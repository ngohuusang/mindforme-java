package com.mindforme.app.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.elasticsearch.index.query.QueryBuilders.queryStringQuery;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.mindforme.app.MindformeApp;
import com.mindforme.app.domain.MedicalConditionData;
import com.mindforme.app.repository.MedicalConditionDataRepository;
import com.mindforme.app.repository.search.MedicalConditionDataSearchRepository;
import com.mindforme.app.service.MedicalConditionDataService;
import com.mindforme.app.service.dto.MedicalConditionDataDTO;
import com.mindforme.app.service.mapper.MedicalConditionDataMapper;
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
 * Integration tests for the {@link MedicalConditionDataResource} REST controller.
 */
@SpringBootTest(classes = MindformeApp.class)
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
public class MedicalConditionDataResourceIT {
    private static final String DEFAULT_CONTENT = "AAAAAAAAAA";
    private static final String UPDATED_CONTENT = "BBBBBBBBBB";

    @Autowired
    private MedicalConditionDataRepository medicalConditionDataRepository;

    @Autowired
    private MedicalConditionDataMapper medicalConditionDataMapper;

    @Autowired
    private MedicalConditionDataService medicalConditionDataService;

    /**
     * This repository is mocked in the com.mindforme.app.repository.search test package.
     *
     * @see com.mindforme.app.repository.search.MedicalConditionDataSearchRepositoryMockConfiguration
     */
    @Autowired
    private MedicalConditionDataSearchRepository mockMedicalConditionDataSearchRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restMedicalConditionDataMockMvc;

    private MedicalConditionData medicalConditionData;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static MedicalConditionData createEntity(EntityManager em) {
        MedicalConditionData medicalConditionData = new MedicalConditionData().content(DEFAULT_CONTENT);
        return medicalConditionData;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static MedicalConditionData createUpdatedEntity(EntityManager em) {
        MedicalConditionData medicalConditionData = new MedicalConditionData().content(UPDATED_CONTENT);
        return medicalConditionData;
    }

    @BeforeEach
    public void initTest() {
        medicalConditionData = createEntity(em);
    }

    @Test
    @Transactional
    public void createMedicalConditionData() throws Exception {
        int databaseSizeBeforeCreate = medicalConditionDataRepository.findAll().size();
        // Create the MedicalConditionData
        MedicalConditionDataDTO medicalConditionDataDTO = medicalConditionDataMapper.toDto(medicalConditionData);
        restMedicalConditionDataMockMvc
            .perform(
                post("/api/medical-condition-data")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(medicalConditionDataDTO))
            )
            .andExpect(status().isCreated());

        // Validate the MedicalConditionData in the database
        List<MedicalConditionData> medicalConditionDataList = medicalConditionDataRepository.findAll();
        assertThat(medicalConditionDataList).hasSize(databaseSizeBeforeCreate + 1);
        MedicalConditionData testMedicalConditionData = medicalConditionDataList.get(medicalConditionDataList.size() - 1);
        assertThat(testMedicalConditionData.getContent()).isEqualTo(DEFAULT_CONTENT);

        // Validate the MedicalConditionData in Elasticsearch
        verify(mockMedicalConditionDataSearchRepository, times(1)).save(testMedicalConditionData);
    }

    @Test
    @Transactional
    public void createMedicalConditionDataWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = medicalConditionDataRepository.findAll().size();

        // Create the MedicalConditionData with an existing ID
        medicalConditionData.setId(1L);
        MedicalConditionDataDTO medicalConditionDataDTO = medicalConditionDataMapper.toDto(medicalConditionData);

        // An entity with an existing ID cannot be created, so this API call must fail
        restMedicalConditionDataMockMvc
            .perform(
                post("/api/medical-condition-data")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(medicalConditionDataDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the MedicalConditionData in the database
        List<MedicalConditionData> medicalConditionDataList = medicalConditionDataRepository.findAll();
        assertThat(medicalConditionDataList).hasSize(databaseSizeBeforeCreate);

        // Validate the MedicalConditionData in Elasticsearch
        verify(mockMedicalConditionDataSearchRepository, times(0)).save(medicalConditionData);
    }

    @Test
    @Transactional
    public void getAllMedicalConditionData() throws Exception {
        // Initialize the database
        medicalConditionDataRepository.saveAndFlush(medicalConditionData);

        // Get all the medicalConditionDataList
        restMedicalConditionDataMockMvc
            .perform(get("/api/medical-condition-data?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(medicalConditionData.getId().intValue())))
            .andExpect(jsonPath("$.[*].content").value(hasItem(DEFAULT_CONTENT)));
    }

    @Test
    @Transactional
    public void getMedicalConditionData() throws Exception {
        // Initialize the database
        medicalConditionDataRepository.saveAndFlush(medicalConditionData);

        // Get the medicalConditionData
        restMedicalConditionDataMockMvc
            .perform(get("/api/medical-condition-data/{id}", medicalConditionData.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(medicalConditionData.getId().intValue()))
            .andExpect(jsonPath("$.content").value(DEFAULT_CONTENT));
    }

    @Test
    @Transactional
    public void getNonExistingMedicalConditionData() throws Exception {
        // Get the medicalConditionData
        restMedicalConditionDataMockMvc.perform(get("/api/medical-condition-data/{id}", Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateMedicalConditionData() throws Exception {
        // Initialize the database
        medicalConditionDataRepository.saveAndFlush(medicalConditionData);

        int databaseSizeBeforeUpdate = medicalConditionDataRepository.findAll().size();

        // Update the medicalConditionData
        MedicalConditionData updatedMedicalConditionData = medicalConditionDataRepository.findById(medicalConditionData.getId()).get();
        // Disconnect from session so that the updates on updatedMedicalConditionData are not directly saved in db
        em.detach(updatedMedicalConditionData);
        updatedMedicalConditionData.content(UPDATED_CONTENT);
        MedicalConditionDataDTO medicalConditionDataDTO = medicalConditionDataMapper.toDto(updatedMedicalConditionData);

        restMedicalConditionDataMockMvc
            .perform(
                put("/api/medical-condition-data")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(medicalConditionDataDTO))
            )
            .andExpect(status().isOk());

        // Validate the MedicalConditionData in the database
        List<MedicalConditionData> medicalConditionDataList = medicalConditionDataRepository.findAll();
        assertThat(medicalConditionDataList).hasSize(databaseSizeBeforeUpdate);
        MedicalConditionData testMedicalConditionData = medicalConditionDataList.get(medicalConditionDataList.size() - 1);
        assertThat(testMedicalConditionData.getContent()).isEqualTo(UPDATED_CONTENT);

        // Validate the MedicalConditionData in Elasticsearch
        verify(mockMedicalConditionDataSearchRepository, times(1)).save(testMedicalConditionData);
    }

    @Test
    @Transactional
    public void updateNonExistingMedicalConditionData() throws Exception {
        int databaseSizeBeforeUpdate = medicalConditionDataRepository.findAll().size();

        // Create the MedicalConditionData
        MedicalConditionDataDTO medicalConditionDataDTO = medicalConditionDataMapper.toDto(medicalConditionData);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restMedicalConditionDataMockMvc
            .perform(
                put("/api/medical-condition-data")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(medicalConditionDataDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the MedicalConditionData in the database
        List<MedicalConditionData> medicalConditionDataList = medicalConditionDataRepository.findAll();
        assertThat(medicalConditionDataList).hasSize(databaseSizeBeforeUpdate);

        // Validate the MedicalConditionData in Elasticsearch
        verify(mockMedicalConditionDataSearchRepository, times(0)).save(medicalConditionData);
    }

    @Test
    @Transactional
    public void deleteMedicalConditionData() throws Exception {
        // Initialize the database
        medicalConditionDataRepository.saveAndFlush(medicalConditionData);

        int databaseSizeBeforeDelete = medicalConditionDataRepository.findAll().size();

        // Delete the medicalConditionData
        restMedicalConditionDataMockMvc
            .perform(delete("/api/medical-condition-data/{id}", medicalConditionData.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<MedicalConditionData> medicalConditionDataList = medicalConditionDataRepository.findAll();
        assertThat(medicalConditionDataList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the MedicalConditionData in Elasticsearch
        verify(mockMedicalConditionDataSearchRepository, times(1)).deleteById(medicalConditionData.getId());
    }

    @Test
    @Transactional
    public void searchMedicalConditionData() throws Exception {
        // Configure the mock search repository
        // Initialize the database
        medicalConditionDataRepository.saveAndFlush(medicalConditionData);
        when(mockMedicalConditionDataSearchRepository.search(queryStringQuery("id:" + medicalConditionData.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(medicalConditionData), PageRequest.of(0, 1), 1));

        // Search the medicalConditionData
        restMedicalConditionDataMockMvc
            .perform(get("/api/_search/medical-condition-data?query=id:" + medicalConditionData.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(medicalConditionData.getId().intValue())))
            .andExpect(jsonPath("$.[*].content").value(hasItem(DEFAULT_CONTENT)));
    }
}
