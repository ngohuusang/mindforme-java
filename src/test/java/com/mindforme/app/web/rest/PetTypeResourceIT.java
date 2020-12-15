package com.mindforme.app.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.elasticsearch.index.query.QueryBuilders.queryStringQuery;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.mindforme.app.MindformeApp;
import com.mindforme.app.domain.PetType;
import com.mindforme.app.domain.enumeration.Status;
import com.mindforme.app.repository.PetTypeRepository;
import com.mindforme.app.repository.search.PetTypeSearchRepository;
import com.mindforme.app.service.PetTypeService;
import com.mindforme.app.service.dto.PetTypeDTO;
import com.mindforme.app.service.mapper.PetTypeMapper;
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
 * Integration tests for the {@link PetTypeResource} REST controller.
 */
@SpringBootTest(classes = MindformeApp.class)
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
public class PetTypeResourceIT {
    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final Status DEFAULT_STATUS = Status.Active;
    private static final Status UPDATED_STATUS = Status.Inactive;

    @Autowired
    private PetTypeRepository petTypeRepository;

    @Autowired
    private PetTypeMapper petTypeMapper;

    @Autowired
    private PetTypeService petTypeService;

    /**
     * This repository is mocked in the com.mindforme.app.repository.search test package.
     *
     * @see com.mindforme.app.repository.search.PetTypeSearchRepositoryMockConfiguration
     */
    @Autowired
    private PetTypeSearchRepository mockPetTypeSearchRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restPetTypeMockMvc;

    private PetType petType;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PetType createEntity(EntityManager em) {
        PetType petType = new PetType().name(DEFAULT_NAME).status(DEFAULT_STATUS);
        return petType;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PetType createUpdatedEntity(EntityManager em) {
        PetType petType = new PetType().name(UPDATED_NAME).status(UPDATED_STATUS);
        return petType;
    }

    @BeforeEach
    public void initTest() {
        petType = createEntity(em);
    }

    @Test
    @Transactional
    public void createPetType() throws Exception {
        int databaseSizeBeforeCreate = petTypeRepository.findAll().size();
        // Create the PetType
        PetTypeDTO petTypeDTO = petTypeMapper.toDto(petType);
        restPetTypeMockMvc
            .perform(post("/api/pet-types").contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(petTypeDTO)))
            .andExpect(status().isCreated());

        // Validate the PetType in the database
        List<PetType> petTypeList = petTypeRepository.findAll();
        assertThat(petTypeList).hasSize(databaseSizeBeforeCreate + 1);
        PetType testPetType = petTypeList.get(petTypeList.size() - 1);
        assertThat(testPetType.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testPetType.getStatus()).isEqualTo(DEFAULT_STATUS);

        // Validate the PetType in Elasticsearch
        verify(mockPetTypeSearchRepository, times(1)).save(testPetType);
    }

    @Test
    @Transactional
    public void createPetTypeWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = petTypeRepository.findAll().size();

        // Create the PetType with an existing ID
        petType.setId(1L);
        PetTypeDTO petTypeDTO = petTypeMapper.toDto(petType);

        // An entity with an existing ID cannot be created, so this API call must fail
        restPetTypeMockMvc
            .perform(post("/api/pet-types").contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(petTypeDTO)))
            .andExpect(status().isBadRequest());

        // Validate the PetType in the database
        List<PetType> petTypeList = petTypeRepository.findAll();
        assertThat(petTypeList).hasSize(databaseSizeBeforeCreate);

        // Validate the PetType in Elasticsearch
        verify(mockPetTypeSearchRepository, times(0)).save(petType);
    }

    @Test
    @Transactional
    public void getAllPetTypes() throws Exception {
        // Initialize the database
        petTypeRepository.saveAndFlush(petType);

        // Get all the petTypeList
        restPetTypeMockMvc
            .perform(get("/api/pet-types?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(petType.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.toString())));
    }

    @Test
    @Transactional
    public void getPetType() throws Exception {
        // Initialize the database
        petTypeRepository.saveAndFlush(petType);

        // Get the petType
        restPetTypeMockMvc
            .perform(get("/api/pet-types/{id}", petType.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(petType.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingPetType() throws Exception {
        // Get the petType
        restPetTypeMockMvc.perform(get("/api/pet-types/{id}", Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePetType() throws Exception {
        // Initialize the database
        petTypeRepository.saveAndFlush(petType);

        int databaseSizeBeforeUpdate = petTypeRepository.findAll().size();

        // Update the petType
        PetType updatedPetType = petTypeRepository.findById(petType.getId()).get();
        // Disconnect from session so that the updates on updatedPetType are not directly saved in db
        em.detach(updatedPetType);
        updatedPetType.name(UPDATED_NAME).status(UPDATED_STATUS);
        PetTypeDTO petTypeDTO = petTypeMapper.toDto(updatedPetType);

        restPetTypeMockMvc
            .perform(put("/api/pet-types").contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(petTypeDTO)))
            .andExpect(status().isOk());

        // Validate the PetType in the database
        List<PetType> petTypeList = petTypeRepository.findAll();
        assertThat(petTypeList).hasSize(databaseSizeBeforeUpdate);
        PetType testPetType = petTypeList.get(petTypeList.size() - 1);
        assertThat(testPetType.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testPetType.getStatus()).isEqualTo(UPDATED_STATUS);

        // Validate the PetType in Elasticsearch
        verify(mockPetTypeSearchRepository, times(1)).save(testPetType);
    }

    @Test
    @Transactional
    public void updateNonExistingPetType() throws Exception {
        int databaseSizeBeforeUpdate = petTypeRepository.findAll().size();

        // Create the PetType
        PetTypeDTO petTypeDTO = petTypeMapper.toDto(petType);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPetTypeMockMvc
            .perform(put("/api/pet-types").contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(petTypeDTO)))
            .andExpect(status().isBadRequest());

        // Validate the PetType in the database
        List<PetType> petTypeList = petTypeRepository.findAll();
        assertThat(petTypeList).hasSize(databaseSizeBeforeUpdate);

        // Validate the PetType in Elasticsearch
        verify(mockPetTypeSearchRepository, times(0)).save(petType);
    }

    @Test
    @Transactional
    public void deletePetType() throws Exception {
        // Initialize the database
        petTypeRepository.saveAndFlush(petType);

        int databaseSizeBeforeDelete = petTypeRepository.findAll().size();

        // Delete the petType
        restPetTypeMockMvc
            .perform(delete("/api/pet-types/{id}", petType.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<PetType> petTypeList = petTypeRepository.findAll();
        assertThat(petTypeList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the PetType in Elasticsearch
        verify(mockPetTypeSearchRepository, times(1)).deleteById(petType.getId());
    }

    @Test
    @Transactional
    public void searchPetType() throws Exception {
        // Configure the mock search repository
        // Initialize the database
        petTypeRepository.saveAndFlush(petType);
        when(mockPetTypeSearchRepository.search(queryStringQuery("id:" + petType.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(petType), PageRequest.of(0, 1), 1));

        // Search the petType
        restPetTypeMockMvc
            .perform(get("/api/_search/pet-types?query=id:" + petType.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(petType.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.toString())));
    }
}
