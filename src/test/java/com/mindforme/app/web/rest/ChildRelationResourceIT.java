package com.mindforme.app.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.elasticsearch.index.query.QueryBuilders.queryStringQuery;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.mindforme.app.MindformeApp;
import com.mindforme.app.domain.ChildRelation;
import com.mindforme.app.domain.enumeration.Status;
import com.mindforme.app.repository.ChildRelationRepository;
import com.mindforme.app.repository.search.ChildRelationSearchRepository;
import com.mindforme.app.service.ChildRelationService;
import com.mindforme.app.service.dto.ChildRelationDTO;
import com.mindforme.app.service.mapper.ChildRelationMapper;
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
 * Integration tests for the {@link ChildRelationResource} REST controller.
 */
@SpringBootTest(classes = MindformeApp.class)
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
public class ChildRelationResourceIT {
    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final Status DEFAULT_STATUS = Status.Active;
    private static final Status UPDATED_STATUS = Status.Inactive;

    @Autowired
    private ChildRelationRepository childRelationRepository;

    @Autowired
    private ChildRelationMapper childRelationMapper;

    @Autowired
    private ChildRelationService childRelationService;

    /**
     * This repository is mocked in the com.mindforme.app.repository.search test package.
     *
     * @see com.mindforme.app.repository.search.ChildRelationSearchRepositoryMockConfiguration
     */
    @Autowired
    private ChildRelationSearchRepository mockChildRelationSearchRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restChildRelationMockMvc;

    private ChildRelation childRelation;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ChildRelation createEntity(EntityManager em) {
        ChildRelation childRelation = new ChildRelation().name(DEFAULT_NAME).status(DEFAULT_STATUS);
        return childRelation;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ChildRelation createUpdatedEntity(EntityManager em) {
        ChildRelation childRelation = new ChildRelation().name(UPDATED_NAME).status(UPDATED_STATUS);
        return childRelation;
    }

    @BeforeEach
    public void initTest() {
        childRelation = createEntity(em);
    }

    @Test
    @Transactional
    public void createChildRelation() throws Exception {
        int databaseSizeBeforeCreate = childRelationRepository.findAll().size();
        // Create the ChildRelation
        ChildRelationDTO childRelationDTO = childRelationMapper.toDto(childRelation);
        restChildRelationMockMvc
            .perform(
                post("/api/child-relations")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(childRelationDTO))
            )
            .andExpect(status().isCreated());

        // Validate the ChildRelation in the database
        List<ChildRelation> childRelationList = childRelationRepository.findAll();
        assertThat(childRelationList).hasSize(databaseSizeBeforeCreate + 1);
        ChildRelation testChildRelation = childRelationList.get(childRelationList.size() - 1);
        assertThat(testChildRelation.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testChildRelation.getStatus()).isEqualTo(DEFAULT_STATUS);

        // Validate the ChildRelation in Elasticsearch
        verify(mockChildRelationSearchRepository, times(1)).save(testChildRelation);
    }

    @Test
    @Transactional
    public void createChildRelationWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = childRelationRepository.findAll().size();

        // Create the ChildRelation with an existing ID
        childRelation.setId(1L);
        ChildRelationDTO childRelationDTO = childRelationMapper.toDto(childRelation);

        // An entity with an existing ID cannot be created, so this API call must fail
        restChildRelationMockMvc
            .perform(
                post("/api/child-relations")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(childRelationDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ChildRelation in the database
        List<ChildRelation> childRelationList = childRelationRepository.findAll();
        assertThat(childRelationList).hasSize(databaseSizeBeforeCreate);

        // Validate the ChildRelation in Elasticsearch
        verify(mockChildRelationSearchRepository, times(0)).save(childRelation);
    }

    @Test
    @Transactional
    public void getAllChildRelations() throws Exception {
        // Initialize the database
        childRelationRepository.saveAndFlush(childRelation);

        // Get all the childRelationList
        restChildRelationMockMvc
            .perform(get("/api/child-relations?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(childRelation.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.toString())));
    }

    @Test
    @Transactional
    public void getChildRelation() throws Exception {
        // Initialize the database
        childRelationRepository.saveAndFlush(childRelation);

        // Get the childRelation
        restChildRelationMockMvc
            .perform(get("/api/child-relations/{id}", childRelation.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(childRelation.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingChildRelation() throws Exception {
        // Get the childRelation
        restChildRelationMockMvc.perform(get("/api/child-relations/{id}", Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateChildRelation() throws Exception {
        // Initialize the database
        childRelationRepository.saveAndFlush(childRelation);

        int databaseSizeBeforeUpdate = childRelationRepository.findAll().size();

        // Update the childRelation
        ChildRelation updatedChildRelation = childRelationRepository.findById(childRelation.getId()).get();
        // Disconnect from session so that the updates on updatedChildRelation are not directly saved in db
        em.detach(updatedChildRelation);
        updatedChildRelation.name(UPDATED_NAME).status(UPDATED_STATUS);
        ChildRelationDTO childRelationDTO = childRelationMapper.toDto(updatedChildRelation);

        restChildRelationMockMvc
            .perform(
                put("/api/child-relations")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(childRelationDTO))
            )
            .andExpect(status().isOk());

        // Validate the ChildRelation in the database
        List<ChildRelation> childRelationList = childRelationRepository.findAll();
        assertThat(childRelationList).hasSize(databaseSizeBeforeUpdate);
        ChildRelation testChildRelation = childRelationList.get(childRelationList.size() - 1);
        assertThat(testChildRelation.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testChildRelation.getStatus()).isEqualTo(UPDATED_STATUS);

        // Validate the ChildRelation in Elasticsearch
        verify(mockChildRelationSearchRepository, times(1)).save(testChildRelation);
    }

    @Test
    @Transactional
    public void updateNonExistingChildRelation() throws Exception {
        int databaseSizeBeforeUpdate = childRelationRepository.findAll().size();

        // Create the ChildRelation
        ChildRelationDTO childRelationDTO = childRelationMapper.toDto(childRelation);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restChildRelationMockMvc
            .perform(
                put("/api/child-relations")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(childRelationDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ChildRelation in the database
        List<ChildRelation> childRelationList = childRelationRepository.findAll();
        assertThat(childRelationList).hasSize(databaseSizeBeforeUpdate);

        // Validate the ChildRelation in Elasticsearch
        verify(mockChildRelationSearchRepository, times(0)).save(childRelation);
    }

    @Test
    @Transactional
    public void deleteChildRelation() throws Exception {
        // Initialize the database
        childRelationRepository.saveAndFlush(childRelation);

        int databaseSizeBeforeDelete = childRelationRepository.findAll().size();

        // Delete the childRelation
        restChildRelationMockMvc
            .perform(delete("/api/child-relations/{id}", childRelation.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<ChildRelation> childRelationList = childRelationRepository.findAll();
        assertThat(childRelationList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the ChildRelation in Elasticsearch
        verify(mockChildRelationSearchRepository, times(1)).deleteById(childRelation.getId());
    }

    @Test
    @Transactional
    public void searchChildRelation() throws Exception {
        // Configure the mock search repository
        // Initialize the database
        childRelationRepository.saveAndFlush(childRelation);
        when(mockChildRelationSearchRepository.search(queryStringQuery("id:" + childRelation.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(childRelation), PageRequest.of(0, 1), 1));

        // Search the childRelation
        restChildRelationMockMvc
            .perform(get("/api/_search/child-relations?query=id:" + childRelation.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(childRelation.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.toString())));
    }
}
