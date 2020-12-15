package com.mindforme.app.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.elasticsearch.index.query.QueryBuilders.queryStringQuery;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.mindforme.app.MindformeApp;
import com.mindforme.app.domain.InterestData;
import com.mindforme.app.repository.InterestDataRepository;
import com.mindforme.app.repository.search.InterestDataSearchRepository;
import com.mindforme.app.service.InterestDataService;
import com.mindforme.app.service.dto.InterestDataDTO;
import com.mindforme.app.service.mapper.InterestDataMapper;
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
 * Integration tests for the {@link InterestDataResource} REST controller.
 */
@SpringBootTest(classes = MindformeApp.class)
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
public class InterestDataResourceIT {
    private static final String DEFAULT_CONTENT = "AAAAAAAAAA";
    private static final String UPDATED_CONTENT = "BBBBBBBBBB";

    @Autowired
    private InterestDataRepository interestDataRepository;

    @Autowired
    private InterestDataMapper interestDataMapper;

    @Autowired
    private InterestDataService interestDataService;

    /**
     * This repository is mocked in the com.mindforme.app.repository.search test package.
     *
     * @see com.mindforme.app.repository.search.InterestDataSearchRepositoryMockConfiguration
     */
    @Autowired
    private InterestDataSearchRepository mockInterestDataSearchRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restInterestDataMockMvc;

    private InterestData interestData;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static InterestData createEntity(EntityManager em) {
        InterestData interestData = new InterestData().content(DEFAULT_CONTENT);
        return interestData;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static InterestData createUpdatedEntity(EntityManager em) {
        InterestData interestData = new InterestData().content(UPDATED_CONTENT);
        return interestData;
    }

    @BeforeEach
    public void initTest() {
        interestData = createEntity(em);
    }

    @Test
    @Transactional
    public void createInterestData() throws Exception {
        int databaseSizeBeforeCreate = interestDataRepository.findAll().size();
        // Create the InterestData
        InterestDataDTO interestDataDTO = interestDataMapper.toDto(interestData);
        restInterestDataMockMvc
            .perform(
                post("/api/interest-data")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(interestDataDTO))
            )
            .andExpect(status().isCreated());

        // Validate the InterestData in the database
        List<InterestData> interestDataList = interestDataRepository.findAll();
        assertThat(interestDataList).hasSize(databaseSizeBeforeCreate + 1);
        InterestData testInterestData = interestDataList.get(interestDataList.size() - 1);
        assertThat(testInterestData.getContent()).isEqualTo(DEFAULT_CONTENT);

        // Validate the InterestData in Elasticsearch
        verify(mockInterestDataSearchRepository, times(1)).save(testInterestData);
    }

    @Test
    @Transactional
    public void createInterestDataWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = interestDataRepository.findAll().size();

        // Create the InterestData with an existing ID
        interestData.setId(1L);
        InterestDataDTO interestDataDTO = interestDataMapper.toDto(interestData);

        // An entity with an existing ID cannot be created, so this API call must fail
        restInterestDataMockMvc
            .perform(
                post("/api/interest-data")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(interestDataDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the InterestData in the database
        List<InterestData> interestDataList = interestDataRepository.findAll();
        assertThat(interestDataList).hasSize(databaseSizeBeforeCreate);

        // Validate the InterestData in Elasticsearch
        verify(mockInterestDataSearchRepository, times(0)).save(interestData);
    }

    @Test
    @Transactional
    public void getAllInterestData() throws Exception {
        // Initialize the database
        interestDataRepository.saveAndFlush(interestData);

        // Get all the interestDataList
        restInterestDataMockMvc
            .perform(get("/api/interest-data?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(interestData.getId().intValue())))
            .andExpect(jsonPath("$.[*].content").value(hasItem(DEFAULT_CONTENT)));
    }

    @Test
    @Transactional
    public void getInterestData() throws Exception {
        // Initialize the database
        interestDataRepository.saveAndFlush(interestData);

        // Get the interestData
        restInterestDataMockMvc
            .perform(get("/api/interest-data/{id}", interestData.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(interestData.getId().intValue()))
            .andExpect(jsonPath("$.content").value(DEFAULT_CONTENT));
    }

    @Test
    @Transactional
    public void getNonExistingInterestData() throws Exception {
        // Get the interestData
        restInterestDataMockMvc.perform(get("/api/interest-data/{id}", Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateInterestData() throws Exception {
        // Initialize the database
        interestDataRepository.saveAndFlush(interestData);

        int databaseSizeBeforeUpdate = interestDataRepository.findAll().size();

        // Update the interestData
        InterestData updatedInterestData = interestDataRepository.findById(interestData.getId()).get();
        // Disconnect from session so that the updates on updatedInterestData are not directly saved in db
        em.detach(updatedInterestData);
        updatedInterestData.content(UPDATED_CONTENT);
        InterestDataDTO interestDataDTO = interestDataMapper.toDto(updatedInterestData);

        restInterestDataMockMvc
            .perform(
                put("/api/interest-data")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(interestDataDTO))
            )
            .andExpect(status().isOk());

        // Validate the InterestData in the database
        List<InterestData> interestDataList = interestDataRepository.findAll();
        assertThat(interestDataList).hasSize(databaseSizeBeforeUpdate);
        InterestData testInterestData = interestDataList.get(interestDataList.size() - 1);
        assertThat(testInterestData.getContent()).isEqualTo(UPDATED_CONTENT);

        // Validate the InterestData in Elasticsearch
        verify(mockInterestDataSearchRepository, times(1)).save(testInterestData);
    }

    @Test
    @Transactional
    public void updateNonExistingInterestData() throws Exception {
        int databaseSizeBeforeUpdate = interestDataRepository.findAll().size();

        // Create the InterestData
        InterestDataDTO interestDataDTO = interestDataMapper.toDto(interestData);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restInterestDataMockMvc
            .perform(
                put("/api/interest-data")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(interestDataDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the InterestData in the database
        List<InterestData> interestDataList = interestDataRepository.findAll();
        assertThat(interestDataList).hasSize(databaseSizeBeforeUpdate);

        // Validate the InterestData in Elasticsearch
        verify(mockInterestDataSearchRepository, times(0)).save(interestData);
    }

    @Test
    @Transactional
    public void deleteInterestData() throws Exception {
        // Initialize the database
        interestDataRepository.saveAndFlush(interestData);

        int databaseSizeBeforeDelete = interestDataRepository.findAll().size();

        // Delete the interestData
        restInterestDataMockMvc
            .perform(delete("/api/interest-data/{id}", interestData.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<InterestData> interestDataList = interestDataRepository.findAll();
        assertThat(interestDataList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the InterestData in Elasticsearch
        verify(mockInterestDataSearchRepository, times(1)).deleteById(interestData.getId());
    }

    @Test
    @Transactional
    public void searchInterestData() throws Exception {
        // Configure the mock search repository
        // Initialize the database
        interestDataRepository.saveAndFlush(interestData);
        when(mockInterestDataSearchRepository.search(queryStringQuery("id:" + interestData.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(interestData), PageRequest.of(0, 1), 1));

        // Search the interestData
        restInterestDataMockMvc
            .perform(get("/api/_search/interest-data?query=id:" + interestData.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(interestData.getId().intValue())))
            .andExpect(jsonPath("$.[*].content").value(hasItem(DEFAULT_CONTENT)));
    }
}
