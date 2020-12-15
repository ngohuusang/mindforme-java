package com.mindforme.app.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.elasticsearch.index.query.QueryBuilders.queryStringQuery;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.mindforme.app.MindformeApp;
import com.mindforme.app.domain.SupportedRelation;
import com.mindforme.app.domain.enumeration.Status;
import com.mindforme.app.repository.SupportedRelationRepository;
import com.mindforme.app.repository.search.SupportedRelationSearchRepository;
import com.mindforme.app.service.SupportedRelationService;
import com.mindforme.app.service.dto.SupportedRelationDTO;
import com.mindforme.app.service.mapper.SupportedRelationMapper;
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
 * Integration tests for the {@link SupportedRelationResource} REST controller.
 */
@SpringBootTest(classes = MindformeApp.class)
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
public class SupportedRelationResourceIT {
    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final Status DEFAULT_STATUS = Status.Active;
    private static final Status UPDATED_STATUS = Status.Inactive;

    @Autowired
    private SupportedRelationRepository supportedRelationRepository;

    @Autowired
    private SupportedRelationMapper supportedRelationMapper;

    @Autowired
    private SupportedRelationService supportedRelationService;

    /**
     * This repository is mocked in the com.mindforme.app.repository.search test package.
     *
     * @see com.mindforme.app.repository.search.SupportedRelationSearchRepositoryMockConfiguration
     */
    @Autowired
    private SupportedRelationSearchRepository mockSupportedRelationSearchRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restSupportedRelationMockMvc;

    private SupportedRelation supportedRelation;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static SupportedRelation createEntity(EntityManager em) {
        SupportedRelation supportedRelation = new SupportedRelation().name(DEFAULT_NAME).status(DEFAULT_STATUS);
        return supportedRelation;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static SupportedRelation createUpdatedEntity(EntityManager em) {
        SupportedRelation supportedRelation = new SupportedRelation().name(UPDATED_NAME).status(UPDATED_STATUS);
        return supportedRelation;
    }

    @BeforeEach
    public void initTest() {
        supportedRelation = createEntity(em);
    }

    @Test
    @Transactional
    public void createSupportedRelation() throws Exception {
        int databaseSizeBeforeCreate = supportedRelationRepository.findAll().size();
        // Create the SupportedRelation
        SupportedRelationDTO supportedRelationDTO = supportedRelationMapper.toDto(supportedRelation);
        restSupportedRelationMockMvc
            .perform(
                post("/api/supported-relations")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(supportedRelationDTO))
            )
            .andExpect(status().isCreated());

        // Validate the SupportedRelation in the database
        List<SupportedRelation> supportedRelationList = supportedRelationRepository.findAll();
        assertThat(supportedRelationList).hasSize(databaseSizeBeforeCreate + 1);
        SupportedRelation testSupportedRelation = supportedRelationList.get(supportedRelationList.size() - 1);
        assertThat(testSupportedRelation.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testSupportedRelation.getStatus()).isEqualTo(DEFAULT_STATUS);

        // Validate the SupportedRelation in Elasticsearch
        verify(mockSupportedRelationSearchRepository, times(1)).save(testSupportedRelation);
    }

    @Test
    @Transactional
    public void createSupportedRelationWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = supportedRelationRepository.findAll().size();

        // Create the SupportedRelation with an existing ID
        supportedRelation.setId(1L);
        SupportedRelationDTO supportedRelationDTO = supportedRelationMapper.toDto(supportedRelation);

        // An entity with an existing ID cannot be created, so this API call must fail
        restSupportedRelationMockMvc
            .perform(
                post("/api/supported-relations")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(supportedRelationDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the SupportedRelation in the database
        List<SupportedRelation> supportedRelationList = supportedRelationRepository.findAll();
        assertThat(supportedRelationList).hasSize(databaseSizeBeforeCreate);

        // Validate the SupportedRelation in Elasticsearch
        verify(mockSupportedRelationSearchRepository, times(0)).save(supportedRelation);
    }

    @Test
    @Transactional
    public void getAllSupportedRelations() throws Exception {
        // Initialize the database
        supportedRelationRepository.saveAndFlush(supportedRelation);

        // Get all the supportedRelationList
        restSupportedRelationMockMvc
            .perform(get("/api/supported-relations?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(supportedRelation.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.toString())));
    }

    @Test
    @Transactional
    public void getSupportedRelation() throws Exception {
        // Initialize the database
        supportedRelationRepository.saveAndFlush(supportedRelation);

        // Get the supportedRelation
        restSupportedRelationMockMvc
            .perform(get("/api/supported-relations/{id}", supportedRelation.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(supportedRelation.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingSupportedRelation() throws Exception {
        // Get the supportedRelation
        restSupportedRelationMockMvc.perform(get("/api/supported-relations/{id}", Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateSupportedRelation() throws Exception {
        // Initialize the database
        supportedRelationRepository.saveAndFlush(supportedRelation);

        int databaseSizeBeforeUpdate = supportedRelationRepository.findAll().size();

        // Update the supportedRelation
        SupportedRelation updatedSupportedRelation = supportedRelationRepository.findById(supportedRelation.getId()).get();
        // Disconnect from session so that the updates on updatedSupportedRelation are not directly saved in db
        em.detach(updatedSupportedRelation);
        updatedSupportedRelation.name(UPDATED_NAME).status(UPDATED_STATUS);
        SupportedRelationDTO supportedRelationDTO = supportedRelationMapper.toDto(updatedSupportedRelation);

        restSupportedRelationMockMvc
            .perform(
                put("/api/supported-relations")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(supportedRelationDTO))
            )
            .andExpect(status().isOk());

        // Validate the SupportedRelation in the database
        List<SupportedRelation> supportedRelationList = supportedRelationRepository.findAll();
        assertThat(supportedRelationList).hasSize(databaseSizeBeforeUpdate);
        SupportedRelation testSupportedRelation = supportedRelationList.get(supportedRelationList.size() - 1);
        assertThat(testSupportedRelation.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testSupportedRelation.getStatus()).isEqualTo(UPDATED_STATUS);

        // Validate the SupportedRelation in Elasticsearch
        verify(mockSupportedRelationSearchRepository, times(1)).save(testSupportedRelation);
    }

    @Test
    @Transactional
    public void updateNonExistingSupportedRelation() throws Exception {
        int databaseSizeBeforeUpdate = supportedRelationRepository.findAll().size();

        // Create the SupportedRelation
        SupportedRelationDTO supportedRelationDTO = supportedRelationMapper.toDto(supportedRelation);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSupportedRelationMockMvc
            .perform(
                put("/api/supported-relations")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(supportedRelationDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the SupportedRelation in the database
        List<SupportedRelation> supportedRelationList = supportedRelationRepository.findAll();
        assertThat(supportedRelationList).hasSize(databaseSizeBeforeUpdate);

        // Validate the SupportedRelation in Elasticsearch
        verify(mockSupportedRelationSearchRepository, times(0)).save(supportedRelation);
    }

    @Test
    @Transactional
    public void deleteSupportedRelation() throws Exception {
        // Initialize the database
        supportedRelationRepository.saveAndFlush(supportedRelation);

        int databaseSizeBeforeDelete = supportedRelationRepository.findAll().size();

        // Delete the supportedRelation
        restSupportedRelationMockMvc
            .perform(delete("/api/supported-relations/{id}", supportedRelation.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<SupportedRelation> supportedRelationList = supportedRelationRepository.findAll();
        assertThat(supportedRelationList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the SupportedRelation in Elasticsearch
        verify(mockSupportedRelationSearchRepository, times(1)).deleteById(supportedRelation.getId());
    }

    @Test
    @Transactional
    public void searchSupportedRelation() throws Exception {
        // Configure the mock search repository
        // Initialize the database
        supportedRelationRepository.saveAndFlush(supportedRelation);
        when(mockSupportedRelationSearchRepository.search(queryStringQuery("id:" + supportedRelation.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(supportedRelation), PageRequest.of(0, 1), 1));

        // Search the supportedRelation
        restSupportedRelationMockMvc
            .perform(get("/api/_search/supported-relations?query=id:" + supportedRelation.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(supportedRelation.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.toString())));
    }
}
