package com.mindforme.app.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.elasticsearch.index.query.QueryBuilders.queryStringQuery;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.mindforme.app.MindformeApp;
import com.mindforme.app.domain.FeedingData;
import com.mindforme.app.repository.FeedingDataRepository;
import com.mindforme.app.repository.search.FeedingDataSearchRepository;
import com.mindforme.app.service.FeedingDataService;
import com.mindforme.app.service.dto.FeedingDataDTO;
import com.mindforme.app.service.mapper.FeedingDataMapper;
import java.util.Collections;
import java.util.List;
import javax.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link FeedingDataResource} REST controller.
 */
@SpringBootTest(classes = MindformeApp.class)
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
public class FeedingDataResourceIT {
    private static final String DEFAULT_CONTENT = "AAAAAAAAAA";
    private static final String UPDATED_CONTENT = "BBBBBBBBBB";

    @Autowired
    private FeedingDataRepository feedingDataRepository;

    @Autowired
    private FeedingDataMapper feedingDataMapper;

    @Autowired
    private FeedingDataService feedingDataService;

    /**
     * This repository is mocked in the com.mindforme.app.repository.search test package.
     *
     * @see com.mindforme.app.repository.search.FeedingDataSearchRepositoryMockConfiguration
     */
    @Autowired
    private FeedingDataSearchRepository mockFeedingDataSearchRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restFeedingDataMockMvc;

    private FeedingData feedingData;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static FeedingData createEntity(EntityManager em) {
        FeedingData feedingData = new FeedingData().content(DEFAULT_CONTENT);
        return feedingData;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static FeedingData createUpdatedEntity(EntityManager em) {
        FeedingData feedingData = new FeedingData().content(UPDATED_CONTENT);
        return feedingData;
    }

    @BeforeEach
    public void initTest() {
        feedingData = createEntity(em);
    }

    @Test
    @Transactional
    public void createFeedingData() throws Exception {
        int databaseSizeBeforeCreate = feedingDataRepository.findAll().size();
        // Create the FeedingData
        FeedingDataDTO feedingDataDTO = feedingDataMapper.toDto(feedingData);
        restFeedingDataMockMvc
            .perform(
                post("/api/feeding-data").contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(feedingDataDTO))
            )
            .andExpect(status().isCreated());

        // Validate the FeedingData in the database
        List<FeedingData> feedingDataList = feedingDataRepository.findAll();
        assertThat(feedingDataList).hasSize(databaseSizeBeforeCreate + 1);
        FeedingData testFeedingData = feedingDataList.get(feedingDataList.size() - 1);
        assertThat(testFeedingData.getContent()).isEqualTo(DEFAULT_CONTENT);

        // Validate the FeedingData in Elasticsearch
        verify(mockFeedingDataSearchRepository, times(1)).save(testFeedingData);
    }

    @Test
    @Transactional
    public void createFeedingDataWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = feedingDataRepository.findAll().size();

        // Create the FeedingData with an existing ID
        feedingData.setId(1L);
        FeedingDataDTO feedingDataDTO = feedingDataMapper.toDto(feedingData);

        // An entity with an existing ID cannot be created, so this API call must fail
        restFeedingDataMockMvc
            .perform(
                post("/api/feeding-data").contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(feedingDataDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the FeedingData in the database
        List<FeedingData> feedingDataList = feedingDataRepository.findAll();
        assertThat(feedingDataList).hasSize(databaseSizeBeforeCreate);

        // Validate the FeedingData in Elasticsearch
        verify(mockFeedingDataSearchRepository, times(0)).save(feedingData);
    }

    @Test
    @Transactional
    public void getAllFeedingData() throws Exception {
        // Initialize the database
        feedingDataRepository.saveAndFlush(feedingData);

        // Get all the feedingDataList
        restFeedingDataMockMvc
            .perform(get("/api/feeding-data?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(feedingData.getId().intValue())))
            .andExpect(jsonPath("$.[*].content").value(hasItem(DEFAULT_CONTENT)));
    }

    @Test
    @Transactional
    public void getFeedingData() throws Exception {
        // Initialize the database
        feedingDataRepository.saveAndFlush(feedingData);

        // Get the feedingData
        restFeedingDataMockMvc
            .perform(get("/api/feeding-data/{id}", feedingData.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(feedingData.getId().intValue()))
            .andExpect(jsonPath("$.content").value(DEFAULT_CONTENT));
    }

    @Test
    @Transactional
    public void getNonExistingFeedingData() throws Exception {
        // Get the feedingData
        restFeedingDataMockMvc.perform(get("/api/feeding-data/{id}", Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateFeedingData() throws Exception {
        // Initialize the database
        feedingDataRepository.saveAndFlush(feedingData);

        int databaseSizeBeforeUpdate = feedingDataRepository.findAll().size();

        // Update the feedingData
        FeedingData updatedFeedingData = feedingDataRepository.findById(feedingData.getId()).get();
        // Disconnect from session so that the updates on updatedFeedingData are not directly saved in db
        em.detach(updatedFeedingData);
        updatedFeedingData.content(UPDATED_CONTENT);
        FeedingDataDTO feedingDataDTO = feedingDataMapper.toDto(updatedFeedingData);

        restFeedingDataMockMvc
            .perform(
                put("/api/feeding-data").contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(feedingDataDTO))
            )
            .andExpect(status().isOk());

        // Validate the FeedingData in the database
        List<FeedingData> feedingDataList = feedingDataRepository.findAll();
        assertThat(feedingDataList).hasSize(databaseSizeBeforeUpdate);
        FeedingData testFeedingData = feedingDataList.get(feedingDataList.size() - 1);
        assertThat(testFeedingData.getContent()).isEqualTo(UPDATED_CONTENT);

        // Validate the FeedingData in Elasticsearch
        verify(mockFeedingDataSearchRepository, times(1)).save(testFeedingData);
    }

    @Test
    @Transactional
    public void updateNonExistingFeedingData() throws Exception {
        int databaseSizeBeforeUpdate = feedingDataRepository.findAll().size();

        // Create the FeedingData
        FeedingDataDTO feedingDataDTO = feedingDataMapper.toDto(feedingData);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restFeedingDataMockMvc
            .perform(
                put("/api/feeding-data").contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(feedingDataDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the FeedingData in the database
        List<FeedingData> feedingDataList = feedingDataRepository.findAll();
        assertThat(feedingDataList).hasSize(databaseSizeBeforeUpdate);

        // Validate the FeedingData in Elasticsearch
        verify(mockFeedingDataSearchRepository, times(0)).save(feedingData);
    }

    @Test
    @Transactional
    public void deleteFeedingData() throws Exception {
        // Initialize the database
        feedingDataRepository.saveAndFlush(feedingData);

        int databaseSizeBeforeDelete = feedingDataRepository.findAll().size();

        // Delete the feedingData
        restFeedingDataMockMvc
            .perform(delete("/api/feeding-data/{id}", feedingData.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<FeedingData> feedingDataList = feedingDataRepository.findAll();
        assertThat(feedingDataList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the FeedingData in Elasticsearch
        verify(mockFeedingDataSearchRepository, times(1)).deleteById(feedingData.getId());
    }

    @Test
    @Transactional
    public void searchFeedingData() throws Exception {
        // Configure the mock search repository
        // Initialize the database
        feedingDataRepository.saveAndFlush(feedingData);
        when(mockFeedingDataSearchRepository.search(queryStringQuery("id:" + feedingData.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(feedingData), PageRequest.of(0, 1), 1));

        // Search the feedingData
        restFeedingDataMockMvc
            .perform(get("/api/_search/feeding-data?query=id:" + feedingData.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(feedingData.getId().intValue())))
            .andExpect(jsonPath("$.[*].content").value(hasItem(DEFAULT_CONTENT)));
    }
}
