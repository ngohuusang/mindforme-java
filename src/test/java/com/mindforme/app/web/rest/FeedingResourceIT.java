package com.mindforme.app.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.elasticsearch.index.query.QueryBuilders.queryStringQuery;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.mindforme.app.MindformeApp;
import com.mindforme.app.domain.Feeding;
import com.mindforme.app.domain.enumeration.Status;
import com.mindforme.app.repository.FeedingRepository;
import com.mindforme.app.repository.search.FeedingSearchRepository;
import com.mindforme.app.service.FeedingService;
import com.mindforme.app.service.dto.FeedingDTO;
import com.mindforme.app.service.mapper.FeedingMapper;
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
 * Integration tests for the {@link FeedingResource} REST controller.
 */
@SpringBootTest(classes = MindformeApp.class)
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
public class FeedingResourceIT {
    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final Status DEFAULT_STATUS = Status.Active;
    private static final Status UPDATED_STATUS = Status.Inactive;

    @Autowired
    private FeedingRepository feedingRepository;

    @Autowired
    private FeedingMapper feedingMapper;

    @Autowired
    private FeedingService feedingService;

    /**
     * This repository is mocked in the com.mindforme.app.repository.search test package.
     *
     * @see com.mindforme.app.repository.search.FeedingSearchRepositoryMockConfiguration
     */
    @Autowired
    private FeedingSearchRepository mockFeedingSearchRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restFeedingMockMvc;

    private Feeding feeding;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Feeding createEntity(EntityManager em) {
        Feeding feeding = new Feeding().name(DEFAULT_NAME).status(DEFAULT_STATUS);
        return feeding;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Feeding createUpdatedEntity(EntityManager em) {
        Feeding feeding = new Feeding().name(UPDATED_NAME).status(UPDATED_STATUS);
        return feeding;
    }

    @BeforeEach
    public void initTest() {
        feeding = createEntity(em);
    }

    @Test
    @Transactional
    public void createFeeding() throws Exception {
        int databaseSizeBeforeCreate = feedingRepository.findAll().size();
        // Create the Feeding
        FeedingDTO feedingDTO = feedingMapper.toDto(feeding);
        restFeedingMockMvc
            .perform(post("/api/feedings").contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(feedingDTO)))
            .andExpect(status().isCreated());

        // Validate the Feeding in the database
        List<Feeding> feedingList = feedingRepository.findAll();
        assertThat(feedingList).hasSize(databaseSizeBeforeCreate + 1);
        Feeding testFeeding = feedingList.get(feedingList.size() - 1);
        assertThat(testFeeding.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testFeeding.getStatus()).isEqualTo(DEFAULT_STATUS);

        // Validate the Feeding in Elasticsearch
        verify(mockFeedingSearchRepository, times(1)).save(testFeeding);
    }

    @Test
    @Transactional
    public void createFeedingWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = feedingRepository.findAll().size();

        // Create the Feeding with an existing ID
        feeding.setId(1L);
        FeedingDTO feedingDTO = feedingMapper.toDto(feeding);

        // An entity with an existing ID cannot be created, so this API call must fail
        restFeedingMockMvc
            .perform(post("/api/feedings").contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(feedingDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Feeding in the database
        List<Feeding> feedingList = feedingRepository.findAll();
        assertThat(feedingList).hasSize(databaseSizeBeforeCreate);

        // Validate the Feeding in Elasticsearch
        verify(mockFeedingSearchRepository, times(0)).save(feeding);
    }

    @Test
    @Transactional
    public void getAllFeedings() throws Exception {
        // Initialize the database
        feedingRepository.saveAndFlush(feeding);

        // Get all the feedingList
        restFeedingMockMvc
            .perform(get("/api/feedings?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(feeding.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.toString())));
    }

    @Test
    @Transactional
    public void getFeeding() throws Exception {
        // Initialize the database
        feedingRepository.saveAndFlush(feeding);

        // Get the feeding
        restFeedingMockMvc
            .perform(get("/api/feedings/{id}", feeding.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(feeding.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingFeeding() throws Exception {
        // Get the feeding
        restFeedingMockMvc.perform(get("/api/feedings/{id}", Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateFeeding() throws Exception {
        // Initialize the database
        feedingRepository.saveAndFlush(feeding);

        int databaseSizeBeforeUpdate = feedingRepository.findAll().size();

        // Update the feeding
        Feeding updatedFeeding = feedingRepository.findById(feeding.getId()).get();
        // Disconnect from session so that the updates on updatedFeeding are not directly saved in db
        em.detach(updatedFeeding);
        updatedFeeding.name(UPDATED_NAME).status(UPDATED_STATUS);
        FeedingDTO feedingDTO = feedingMapper.toDto(updatedFeeding);

        restFeedingMockMvc
            .perform(put("/api/feedings").contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(feedingDTO)))
            .andExpect(status().isOk());

        // Validate the Feeding in the database
        List<Feeding> feedingList = feedingRepository.findAll();
        assertThat(feedingList).hasSize(databaseSizeBeforeUpdate);
        Feeding testFeeding = feedingList.get(feedingList.size() - 1);
        assertThat(testFeeding.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testFeeding.getStatus()).isEqualTo(UPDATED_STATUS);

        // Validate the Feeding in Elasticsearch
        verify(mockFeedingSearchRepository, times(1)).save(testFeeding);
    }

    @Test
    @Transactional
    public void updateNonExistingFeeding() throws Exception {
        int databaseSizeBeforeUpdate = feedingRepository.findAll().size();

        // Create the Feeding
        FeedingDTO feedingDTO = feedingMapper.toDto(feeding);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restFeedingMockMvc
            .perform(put("/api/feedings").contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(feedingDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Feeding in the database
        List<Feeding> feedingList = feedingRepository.findAll();
        assertThat(feedingList).hasSize(databaseSizeBeforeUpdate);

        // Validate the Feeding in Elasticsearch
        verify(mockFeedingSearchRepository, times(0)).save(feeding);
    }

    @Test
    @Transactional
    public void deleteFeeding() throws Exception {
        // Initialize the database
        feedingRepository.saveAndFlush(feeding);

        int databaseSizeBeforeDelete = feedingRepository.findAll().size();

        // Delete the feeding
        restFeedingMockMvc
            .perform(delete("/api/feedings/{id}", feeding.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Feeding> feedingList = feedingRepository.findAll();
        assertThat(feedingList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the Feeding in Elasticsearch
        verify(mockFeedingSearchRepository, times(1)).deleteById(feeding.getId());
    }

    @Test
    @Transactional
    public void searchFeeding() throws Exception {
        // Configure the mock search repository
        // Initialize the database
        feedingRepository.saveAndFlush(feeding);
        when(mockFeedingSearchRepository.search(queryStringQuery("id:" + feeding.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(feeding), PageRequest.of(0, 1), 1));

        // Search the feeding
        restFeedingMockMvc
            .perform(get("/api/_search/feedings?query=id:" + feeding.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(feeding.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.toString())));
    }
}
