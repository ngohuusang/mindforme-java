package com.mindforme.app.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.elasticsearch.index.query.QueryBuilders.queryStringQuery;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.mindforme.app.MindformeApp;
import com.mindforme.app.domain.PetBreed;
import com.mindforme.app.domain.enumeration.Status;
import com.mindforme.app.repository.PetBreedRepository;
import com.mindforme.app.repository.search.PetBreedSearchRepository;
import com.mindforme.app.service.PetBreedService;
import com.mindforme.app.service.dto.PetBreedDTO;
import com.mindforme.app.service.mapper.PetBreedMapper;
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
 * Integration tests for the {@link PetBreedResource} REST controller.
 */
@SpringBootTest(classes = MindformeApp.class)
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
public class PetBreedResourceIT {
    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final Status DEFAULT_STATUS = Status.Active;
    private static final Status UPDATED_STATUS = Status.Inactive;

    @Autowired
    private PetBreedRepository petBreedRepository;

    @Autowired
    private PetBreedMapper petBreedMapper;

    @Autowired
    private PetBreedService petBreedService;

    /**
     * This repository is mocked in the com.mindforme.app.repository.search test package.
     *
     * @see com.mindforme.app.repository.search.PetBreedSearchRepositoryMockConfiguration
     */
    @Autowired
    private PetBreedSearchRepository mockPetBreedSearchRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restPetBreedMockMvc;

    private PetBreed petBreed;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PetBreed createEntity(EntityManager em) {
        PetBreed petBreed = new PetBreed().name(DEFAULT_NAME).status(DEFAULT_STATUS);
        return petBreed;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PetBreed createUpdatedEntity(EntityManager em) {
        PetBreed petBreed = new PetBreed().name(UPDATED_NAME).status(UPDATED_STATUS);
        return petBreed;
    }

    @BeforeEach
    public void initTest() {
        petBreed = createEntity(em);
    }

    @Test
    @Transactional
    public void createPetBreed() throws Exception {
        int databaseSizeBeforeCreate = petBreedRepository.findAll().size();
        // Create the PetBreed
        PetBreedDTO petBreedDTO = petBreedMapper.toDto(petBreed);
        restPetBreedMockMvc
            .perform(
                post("/api/pet-breeds").contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(petBreedDTO))
            )
            .andExpect(status().isCreated());

        // Validate the PetBreed in the database
        List<PetBreed> petBreedList = petBreedRepository.findAll();
        assertThat(petBreedList).hasSize(databaseSizeBeforeCreate + 1);
        PetBreed testPetBreed = petBreedList.get(petBreedList.size() - 1);
        assertThat(testPetBreed.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testPetBreed.getStatus()).isEqualTo(DEFAULT_STATUS);

        // Validate the PetBreed in Elasticsearch
        verify(mockPetBreedSearchRepository, times(1)).save(testPetBreed);
    }

    @Test
    @Transactional
    public void createPetBreedWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = petBreedRepository.findAll().size();

        // Create the PetBreed with an existing ID
        petBreed.setId(1L);
        PetBreedDTO petBreedDTO = petBreedMapper.toDto(petBreed);

        // An entity with an existing ID cannot be created, so this API call must fail
        restPetBreedMockMvc
            .perform(
                post("/api/pet-breeds").contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(petBreedDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the PetBreed in the database
        List<PetBreed> petBreedList = petBreedRepository.findAll();
        assertThat(petBreedList).hasSize(databaseSizeBeforeCreate);

        // Validate the PetBreed in Elasticsearch
        verify(mockPetBreedSearchRepository, times(0)).save(petBreed);
    }

    @Test
    @Transactional
    public void getAllPetBreeds() throws Exception {
        // Initialize the database
        petBreedRepository.saveAndFlush(petBreed);

        // Get all the petBreedList
        restPetBreedMockMvc
            .perform(get("/api/pet-breeds?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(petBreed.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.toString())));
    }

    @Test
    @Transactional
    public void getPetBreed() throws Exception {
        // Initialize the database
        petBreedRepository.saveAndFlush(petBreed);

        // Get the petBreed
        restPetBreedMockMvc
            .perform(get("/api/pet-breeds/{id}", petBreed.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(petBreed.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingPetBreed() throws Exception {
        // Get the petBreed
        restPetBreedMockMvc.perform(get("/api/pet-breeds/{id}", Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePetBreed() throws Exception {
        // Initialize the database
        petBreedRepository.saveAndFlush(petBreed);

        int databaseSizeBeforeUpdate = petBreedRepository.findAll().size();

        // Update the petBreed
        PetBreed updatedPetBreed = petBreedRepository.findById(petBreed.getId()).get();
        // Disconnect from session so that the updates on updatedPetBreed are not directly saved in db
        em.detach(updatedPetBreed);
        updatedPetBreed.name(UPDATED_NAME).status(UPDATED_STATUS);
        PetBreedDTO petBreedDTO = petBreedMapper.toDto(updatedPetBreed);

        restPetBreedMockMvc
            .perform(put("/api/pet-breeds").contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(petBreedDTO)))
            .andExpect(status().isOk());

        // Validate the PetBreed in the database
        List<PetBreed> petBreedList = petBreedRepository.findAll();
        assertThat(petBreedList).hasSize(databaseSizeBeforeUpdate);
        PetBreed testPetBreed = petBreedList.get(petBreedList.size() - 1);
        assertThat(testPetBreed.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testPetBreed.getStatus()).isEqualTo(UPDATED_STATUS);

        // Validate the PetBreed in Elasticsearch
        verify(mockPetBreedSearchRepository, times(1)).save(testPetBreed);
    }

    @Test
    @Transactional
    public void updateNonExistingPetBreed() throws Exception {
        int databaseSizeBeforeUpdate = petBreedRepository.findAll().size();

        // Create the PetBreed
        PetBreedDTO petBreedDTO = petBreedMapper.toDto(petBreed);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPetBreedMockMvc
            .perform(put("/api/pet-breeds").contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(petBreedDTO)))
            .andExpect(status().isBadRequest());

        // Validate the PetBreed in the database
        List<PetBreed> petBreedList = petBreedRepository.findAll();
        assertThat(petBreedList).hasSize(databaseSizeBeforeUpdate);

        // Validate the PetBreed in Elasticsearch
        verify(mockPetBreedSearchRepository, times(0)).save(petBreed);
    }

    @Test
    @Transactional
    public void deletePetBreed() throws Exception {
        // Initialize the database
        petBreedRepository.saveAndFlush(petBreed);

        int databaseSizeBeforeDelete = petBreedRepository.findAll().size();

        // Delete the petBreed
        restPetBreedMockMvc
            .perform(delete("/api/pet-breeds/{id}", petBreed.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<PetBreed> petBreedList = petBreedRepository.findAll();
        assertThat(petBreedList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the PetBreed in Elasticsearch
        verify(mockPetBreedSearchRepository, times(1)).deleteById(petBreed.getId());
    }

    @Test
    @Transactional
    public void searchPetBreed() throws Exception {
        // Configure the mock search repository
        // Initialize the database
        petBreedRepository.saveAndFlush(petBreed);
        when(mockPetBreedSearchRepository.search(queryStringQuery("id:" + petBreed.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(petBreed), PageRequest.of(0, 1), 1));

        // Search the petBreed
        restPetBreedMockMvc
            .perform(get("/api/_search/pet-breeds?query=id:" + petBreed.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(petBreed.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.toString())));
    }
}
