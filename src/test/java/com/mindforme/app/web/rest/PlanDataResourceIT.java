package com.mindforme.app.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.elasticsearch.index.query.QueryBuilders.queryStringQuery;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.mindforme.app.MindformeApp;
import com.mindforme.app.domain.PlanData;
import com.mindforme.app.repository.PlanDataRepository;
import com.mindforme.app.repository.search.PlanDataSearchRepository;
import com.mindforme.app.service.PlanDataService;
import com.mindforme.app.service.dto.PlanDataDTO;
import com.mindforme.app.service.mapper.PlanDataMapper;
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
 * Integration tests for the {@link PlanDataResource} REST controller.
 */
@SpringBootTest(classes = MindformeApp.class)
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
public class PlanDataResourceIT {
    private static final String DEFAULT_CONTENT = "AAAAAAAAAA";
    private static final String UPDATED_CONTENT = "BBBBBBBBBB";

    @Autowired
    private PlanDataRepository planDataRepository;

    @Autowired
    private PlanDataMapper planDataMapper;

    @Autowired
    private PlanDataService planDataService;

    /**
     * This repository is mocked in the com.mindforme.app.repository.search test package.
     *
     * @see com.mindforme.app.repository.search.PlanDataSearchRepositoryMockConfiguration
     */
    @Autowired
    private PlanDataSearchRepository mockPlanDataSearchRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restPlanDataMockMvc;

    private PlanData planData;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PlanData createEntity(EntityManager em) {
        PlanData planData = new PlanData().content(DEFAULT_CONTENT);
        return planData;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PlanData createUpdatedEntity(EntityManager em) {
        PlanData planData = new PlanData().content(UPDATED_CONTENT);
        return planData;
    }

    @BeforeEach
    public void initTest() {
        planData = createEntity(em);
    }

    @Test
    @Transactional
    public void createPlanData() throws Exception {
        int databaseSizeBeforeCreate = planDataRepository.findAll().size();
        // Create the PlanData
        PlanDataDTO planDataDTO = planDataMapper.toDto(planData);
        restPlanDataMockMvc
            .perform(post("/api/plan-data").contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(planDataDTO)))
            .andExpect(status().isCreated());

        // Validate the PlanData in the database
        List<PlanData> planDataList = planDataRepository.findAll();
        assertThat(planDataList).hasSize(databaseSizeBeforeCreate + 1);
        PlanData testPlanData = planDataList.get(planDataList.size() - 1);
        assertThat(testPlanData.getContent()).isEqualTo(DEFAULT_CONTENT);

        // Validate the PlanData in Elasticsearch
        verify(mockPlanDataSearchRepository, times(1)).save(testPlanData);
    }

    @Test
    @Transactional
    public void createPlanDataWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = planDataRepository.findAll().size();

        // Create the PlanData with an existing ID
        planData.setId(1L);
        PlanDataDTO planDataDTO = planDataMapper.toDto(planData);

        // An entity with an existing ID cannot be created, so this API call must fail
        restPlanDataMockMvc
            .perform(post("/api/plan-data").contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(planDataDTO)))
            .andExpect(status().isBadRequest());

        // Validate the PlanData in the database
        List<PlanData> planDataList = planDataRepository.findAll();
        assertThat(planDataList).hasSize(databaseSizeBeforeCreate);

        // Validate the PlanData in Elasticsearch
        verify(mockPlanDataSearchRepository, times(0)).save(planData);
    }

    @Test
    @Transactional
    public void getAllPlanData() throws Exception {
        // Initialize the database
        planDataRepository.saveAndFlush(planData);

        // Get all the planDataList
        restPlanDataMockMvc
            .perform(get("/api/plan-data?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(planData.getId().intValue())))
            .andExpect(jsonPath("$.[*].content").value(hasItem(DEFAULT_CONTENT)));
    }

    @Test
    @Transactional
    public void getPlanData() throws Exception {
        // Initialize the database
        planDataRepository.saveAndFlush(planData);

        // Get the planData
        restPlanDataMockMvc
            .perform(get("/api/plan-data/{id}", planData.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(planData.getId().intValue()))
            .andExpect(jsonPath("$.content").value(DEFAULT_CONTENT));
    }

    @Test
    @Transactional
    public void getNonExistingPlanData() throws Exception {
        // Get the planData
        restPlanDataMockMvc.perform(get("/api/plan-data/{id}", Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePlanData() throws Exception {
        // Initialize the database
        planDataRepository.saveAndFlush(planData);

        int databaseSizeBeforeUpdate = planDataRepository.findAll().size();

        // Update the planData
        PlanData updatedPlanData = planDataRepository.findById(planData.getId()).get();
        // Disconnect from session so that the updates on updatedPlanData are not directly saved in db
        em.detach(updatedPlanData);
        updatedPlanData.content(UPDATED_CONTENT);
        PlanDataDTO planDataDTO = planDataMapper.toDto(updatedPlanData);

        restPlanDataMockMvc
            .perform(put("/api/plan-data").contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(planDataDTO)))
            .andExpect(status().isOk());

        // Validate the PlanData in the database
        List<PlanData> planDataList = planDataRepository.findAll();
        assertThat(planDataList).hasSize(databaseSizeBeforeUpdate);
        PlanData testPlanData = planDataList.get(planDataList.size() - 1);
        assertThat(testPlanData.getContent()).isEqualTo(UPDATED_CONTENT);

        // Validate the PlanData in Elasticsearch
        verify(mockPlanDataSearchRepository, times(1)).save(testPlanData);
    }

    @Test
    @Transactional
    public void updateNonExistingPlanData() throws Exception {
        int databaseSizeBeforeUpdate = planDataRepository.findAll().size();

        // Create the PlanData
        PlanDataDTO planDataDTO = planDataMapper.toDto(planData);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPlanDataMockMvc
            .perform(put("/api/plan-data").contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(planDataDTO)))
            .andExpect(status().isBadRequest());

        // Validate the PlanData in the database
        List<PlanData> planDataList = planDataRepository.findAll();
        assertThat(planDataList).hasSize(databaseSizeBeforeUpdate);

        // Validate the PlanData in Elasticsearch
        verify(mockPlanDataSearchRepository, times(0)).save(planData);
    }

    @Test
    @Transactional
    public void deletePlanData() throws Exception {
        // Initialize the database
        planDataRepository.saveAndFlush(planData);

        int databaseSizeBeforeDelete = planDataRepository.findAll().size();

        // Delete the planData
        restPlanDataMockMvc
            .perform(delete("/api/plan-data/{id}", planData.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<PlanData> planDataList = planDataRepository.findAll();
        assertThat(planDataList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the PlanData in Elasticsearch
        verify(mockPlanDataSearchRepository, times(1)).deleteById(planData.getId());
    }

    @Test
    @Transactional
    public void searchPlanData() throws Exception {
        // Configure the mock search repository
        // Initialize the database
        planDataRepository.saveAndFlush(planData);
        when(mockPlanDataSearchRepository.search(queryStringQuery("id:" + planData.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(planData), PageRequest.of(0, 1), 1));

        // Search the planData
        restPlanDataMockMvc
            .perform(get("/api/_search/plan-data?query=id:" + planData.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(planData.getId().intValue())))
            .andExpect(jsonPath("$.[*].content").value(hasItem(DEFAULT_CONTENT)));
    }
}
