package com.mindforme.app.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.elasticsearch.index.query.QueryBuilders.queryStringQuery;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.mindforme.app.MindformeApp;
import com.mindforme.app.domain.MedicalCondition;
import com.mindforme.app.domain.enumeration.Status;
import com.mindforme.app.repository.MedicalConditionRepository;
import com.mindforme.app.repository.search.MedicalConditionSearchRepository;
import com.mindforme.app.service.MedicalConditionService;
import com.mindforme.app.service.dto.MedicalConditionDTO;
import com.mindforme.app.service.mapper.MedicalConditionMapper;
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
 * Integration tests for the {@link MedicalConditionResource} REST controller.
 */
@SpringBootTest(classes = MindformeApp.class)
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
public class MedicalConditionResourceIT {
    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final Status DEFAULT_STATUS = Status.Active;
    private static final Status UPDATED_STATUS = Status.Inactive;

    @Autowired
    private MedicalConditionRepository medicalConditionRepository;

    @Autowired
    private MedicalConditionMapper medicalConditionMapper;

    @Autowired
    private MedicalConditionService medicalConditionService;

    /**
     * This repository is mocked in the com.mindforme.app.repository.search test package.
     *
     * @see com.mindforme.app.repository.search.MedicalConditionSearchRepositoryMockConfiguration
     */
    @Autowired
    private MedicalConditionSearchRepository mockMedicalConditionSearchRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restMedicalConditionMockMvc;

    private MedicalCondition medicalCondition;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static MedicalCondition createEntity(EntityManager em) {
        MedicalCondition medicalCondition = new MedicalCondition().name(DEFAULT_NAME).status(DEFAULT_STATUS);
        return medicalCondition;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static MedicalCondition createUpdatedEntity(EntityManager em) {
        MedicalCondition medicalCondition = new MedicalCondition().name(UPDATED_NAME).status(UPDATED_STATUS);
        return medicalCondition;
    }

    @BeforeEach
    public void initTest() {
        medicalCondition = createEntity(em);
    }

    @Test
    @Transactional
    public void createMedicalCondition() throws Exception {
        int databaseSizeBeforeCreate = medicalConditionRepository.findAll().size();
        // Create the MedicalCondition
        MedicalConditionDTO medicalConditionDTO = medicalConditionMapper.toDto(medicalCondition);
        restMedicalConditionMockMvc
            .perform(
                post("/api/medical-conditions")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(medicalConditionDTO))
            )
            .andExpect(status().isCreated());

        // Validate the MedicalCondition in the database
        List<MedicalCondition> medicalConditionList = medicalConditionRepository.findAll();
        assertThat(medicalConditionList).hasSize(databaseSizeBeforeCreate + 1);
        MedicalCondition testMedicalCondition = medicalConditionList.get(medicalConditionList.size() - 1);
        assertThat(testMedicalCondition.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testMedicalCondition.getStatus()).isEqualTo(DEFAULT_STATUS);

        // Validate the MedicalCondition in Elasticsearch
        verify(mockMedicalConditionSearchRepository, times(1)).save(testMedicalCondition);
    }

    @Test
    @Transactional
    public void createMedicalConditionWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = medicalConditionRepository.findAll().size();

        // Create the MedicalCondition with an existing ID
        medicalCondition.setId(1L);
        MedicalConditionDTO medicalConditionDTO = medicalConditionMapper.toDto(medicalCondition);

        // An entity with an existing ID cannot be created, so this API call must fail
        restMedicalConditionMockMvc
            .perform(
                post("/api/medical-conditions")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(medicalConditionDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the MedicalCondition in the database
        List<MedicalCondition> medicalConditionList = medicalConditionRepository.findAll();
        assertThat(medicalConditionList).hasSize(databaseSizeBeforeCreate);

        // Validate the MedicalCondition in Elasticsearch
        verify(mockMedicalConditionSearchRepository, times(0)).save(medicalCondition);
    }

    @Test
    @Transactional
    public void getAllMedicalConditions() throws Exception {
        // Initialize the database
        medicalConditionRepository.saveAndFlush(medicalCondition);

        // Get all the medicalConditionList
        restMedicalConditionMockMvc
            .perform(get("/api/medical-conditions?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(medicalCondition.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.toString())));
    }

    @Test
    @Transactional
    public void getMedicalCondition() throws Exception {
        // Initialize the database
        medicalConditionRepository.saveAndFlush(medicalCondition);

        // Get the medicalCondition
        restMedicalConditionMockMvc
            .perform(get("/api/medical-conditions/{id}", medicalCondition.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(medicalCondition.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingMedicalCondition() throws Exception {
        // Get the medicalCondition
        restMedicalConditionMockMvc.perform(get("/api/medical-conditions/{id}", Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateMedicalCondition() throws Exception {
        // Initialize the database
        medicalConditionRepository.saveAndFlush(medicalCondition);

        int databaseSizeBeforeUpdate = medicalConditionRepository.findAll().size();

        // Update the medicalCondition
        MedicalCondition updatedMedicalCondition = medicalConditionRepository.findById(medicalCondition.getId()).get();
        // Disconnect from session so that the updates on updatedMedicalCondition are not directly saved in db
        em.detach(updatedMedicalCondition);
        updatedMedicalCondition.name(UPDATED_NAME).status(UPDATED_STATUS);
        MedicalConditionDTO medicalConditionDTO = medicalConditionMapper.toDto(updatedMedicalCondition);

        restMedicalConditionMockMvc
            .perform(
                put("/api/medical-conditions")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(medicalConditionDTO))
            )
            .andExpect(status().isOk());

        // Validate the MedicalCondition in the database
        List<MedicalCondition> medicalConditionList = medicalConditionRepository.findAll();
        assertThat(medicalConditionList).hasSize(databaseSizeBeforeUpdate);
        MedicalCondition testMedicalCondition = medicalConditionList.get(medicalConditionList.size() - 1);
        assertThat(testMedicalCondition.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testMedicalCondition.getStatus()).isEqualTo(UPDATED_STATUS);

        // Validate the MedicalCondition in Elasticsearch
        verify(mockMedicalConditionSearchRepository, times(1)).save(testMedicalCondition);
    }

    @Test
    @Transactional
    public void updateNonExistingMedicalCondition() throws Exception {
        int databaseSizeBeforeUpdate = medicalConditionRepository.findAll().size();

        // Create the MedicalCondition
        MedicalConditionDTO medicalConditionDTO = medicalConditionMapper.toDto(medicalCondition);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restMedicalConditionMockMvc
            .perform(
                put("/api/medical-conditions")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(medicalConditionDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the MedicalCondition in the database
        List<MedicalCondition> medicalConditionList = medicalConditionRepository.findAll();
        assertThat(medicalConditionList).hasSize(databaseSizeBeforeUpdate);

        // Validate the MedicalCondition in Elasticsearch
        verify(mockMedicalConditionSearchRepository, times(0)).save(medicalCondition);
    }

    @Test
    @Transactional
    public void deleteMedicalCondition() throws Exception {
        // Initialize the database
        medicalConditionRepository.saveAndFlush(medicalCondition);

        int databaseSizeBeforeDelete = medicalConditionRepository.findAll().size();

        // Delete the medicalCondition
        restMedicalConditionMockMvc
            .perform(delete("/api/medical-conditions/{id}", medicalCondition.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<MedicalCondition> medicalConditionList = medicalConditionRepository.findAll();
        assertThat(medicalConditionList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the MedicalCondition in Elasticsearch
        verify(mockMedicalConditionSearchRepository, times(1)).deleteById(medicalCondition.getId());
    }

    @Test
    @Transactional
    public void searchMedicalCondition() throws Exception {
        // Configure the mock search repository
        // Initialize the database
        medicalConditionRepository.saveAndFlush(medicalCondition);
        when(mockMedicalConditionSearchRepository.search(queryStringQuery("id:" + medicalCondition.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(medicalCondition), PageRequest.of(0, 1), 1));

        // Search the medicalCondition
        restMedicalConditionMockMvc
            .perform(get("/api/_search/medical-conditions?query=id:" + medicalCondition.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(medicalCondition.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.toString())));
    }
}
