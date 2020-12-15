package com.mindforme.app.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.elasticsearch.index.query.QueryBuilders.queryStringQuery;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.mindforme.app.MindformeApp;
import com.mindforme.app.domain.Pet;
import com.mindforme.app.domain.enumeration.Status;
import com.mindforme.app.repository.PetRepository;
import com.mindforme.app.repository.search.PetSearchRepository;
import com.mindforme.app.service.PetService;
import com.mindforme.app.service.dto.PetDTO;
import com.mindforme.app.service.mapper.PetMapper;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
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
 * Integration tests for the {@link PetResource} REST controller.
 */
@SpringBootTest(classes = MindformeApp.class)
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
public class PetResourceIT {
    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_IMAGE_URL = "AAAAAAAAAA";
    private static final String UPDATED_IMAGE_URL = "BBBBBBBBBB";

    private static final Instant DEFAULT_BIRTHDAY = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_BIRTHDAY = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Status DEFAULT_STATUS = Status.Active;
    private static final Status UPDATED_STATUS = Status.Inactive;

    @Autowired
    private PetRepository petRepository;

    @Mock
    private PetRepository petRepositoryMock;

    @Autowired
    private PetMapper petMapper;

    @Mock
    private PetService petServiceMock;

    @Autowired
    private PetService petService;

    /**
     * This repository is mocked in the com.mindforme.app.repository.search test package.
     *
     * @see com.mindforme.app.repository.search.PetSearchRepositoryMockConfiguration
     */
    @Autowired
    private PetSearchRepository mockPetSearchRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restPetMockMvc;

    private Pet pet;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Pet createEntity(EntityManager em) {
        Pet pet = new Pet().name(DEFAULT_NAME).imageUrl(DEFAULT_IMAGE_URL).birthday(DEFAULT_BIRTHDAY).status(DEFAULT_STATUS);
        return pet;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Pet createUpdatedEntity(EntityManager em) {
        Pet pet = new Pet().name(UPDATED_NAME).imageUrl(UPDATED_IMAGE_URL).birthday(UPDATED_BIRTHDAY).status(UPDATED_STATUS);
        return pet;
    }

    @BeforeEach
    public void initTest() {
        pet = createEntity(em);
    }

    @Test
    @Transactional
    public void createPet() throws Exception {
        int databaseSizeBeforeCreate = petRepository.findAll().size();
        // Create the Pet
        PetDTO petDTO = petMapper.toDto(pet);
        restPetMockMvc
            .perform(post("/api/pets").contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(petDTO)))
            .andExpect(status().isCreated());

        // Validate the Pet in the database
        List<Pet> petList = petRepository.findAll();
        assertThat(petList).hasSize(databaseSizeBeforeCreate + 1);
        Pet testPet = petList.get(petList.size() - 1);
        assertThat(testPet.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testPet.getImageUrl()).isEqualTo(DEFAULT_IMAGE_URL);
        assertThat(testPet.getBirthday()).isEqualTo(DEFAULT_BIRTHDAY);
        assertThat(testPet.getStatus()).isEqualTo(DEFAULT_STATUS);

        // Validate the Pet in Elasticsearch
        verify(mockPetSearchRepository, times(1)).save(testPet);
    }

    @Test
    @Transactional
    public void createPetWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = petRepository.findAll().size();

        // Create the Pet with an existing ID
        pet.setId(1L);
        PetDTO petDTO = petMapper.toDto(pet);

        // An entity with an existing ID cannot be created, so this API call must fail
        restPetMockMvc
            .perform(post("/api/pets").contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(petDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Pet in the database
        List<Pet> petList = petRepository.findAll();
        assertThat(petList).hasSize(databaseSizeBeforeCreate);

        // Validate the Pet in Elasticsearch
        verify(mockPetSearchRepository, times(0)).save(pet);
    }

    @Test
    @Transactional
    public void getAllPets() throws Exception {
        // Initialize the database
        petRepository.saveAndFlush(pet);

        // Get all the petList
        restPetMockMvc
            .perform(get("/api/pets?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(pet.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].imageUrl").value(hasItem(DEFAULT_IMAGE_URL)))
            .andExpect(jsonPath("$.[*].birthday").value(hasItem(DEFAULT_BIRTHDAY.toString())))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.toString())));
    }

    @SuppressWarnings({ "unchecked" })
    public void getAllPetsWithEagerRelationshipsIsEnabled() throws Exception {
        when(petServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restPetMockMvc.perform(get("/api/pets?eagerload=true")).andExpect(status().isOk());

        verify(petServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({ "unchecked" })
    public void getAllPetsWithEagerRelationshipsIsNotEnabled() throws Exception {
        when(petServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restPetMockMvc.perform(get("/api/pets?eagerload=true")).andExpect(status().isOk());

        verify(petServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @Test
    @Transactional
    public void getPet() throws Exception {
        // Initialize the database
        petRepository.saveAndFlush(pet);

        // Get the pet
        restPetMockMvc
            .perform(get("/api/pets/{id}", pet.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(pet.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.imageUrl").value(DEFAULT_IMAGE_URL))
            .andExpect(jsonPath("$.birthday").value(DEFAULT_BIRTHDAY.toString()))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingPet() throws Exception {
        // Get the pet
        restPetMockMvc.perform(get("/api/pets/{id}", Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePet() throws Exception {
        // Initialize the database
        petRepository.saveAndFlush(pet);

        int databaseSizeBeforeUpdate = petRepository.findAll().size();

        // Update the pet
        Pet updatedPet = petRepository.findById(pet.getId()).get();
        // Disconnect from session so that the updates on updatedPet are not directly saved in db
        em.detach(updatedPet);
        updatedPet.name(UPDATED_NAME).imageUrl(UPDATED_IMAGE_URL).birthday(UPDATED_BIRTHDAY).status(UPDATED_STATUS);
        PetDTO petDTO = petMapper.toDto(updatedPet);

        restPetMockMvc
            .perform(put("/api/pets").contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(petDTO)))
            .andExpect(status().isOk());

        // Validate the Pet in the database
        List<Pet> petList = petRepository.findAll();
        assertThat(petList).hasSize(databaseSizeBeforeUpdate);
        Pet testPet = petList.get(petList.size() - 1);
        assertThat(testPet.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testPet.getImageUrl()).isEqualTo(UPDATED_IMAGE_URL);
        assertThat(testPet.getBirthday()).isEqualTo(UPDATED_BIRTHDAY);
        assertThat(testPet.getStatus()).isEqualTo(UPDATED_STATUS);

        // Validate the Pet in Elasticsearch
        verify(mockPetSearchRepository, times(1)).save(testPet);
    }

    @Test
    @Transactional
    public void updateNonExistingPet() throws Exception {
        int databaseSizeBeforeUpdate = petRepository.findAll().size();

        // Create the Pet
        PetDTO petDTO = petMapper.toDto(pet);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPetMockMvc
            .perform(put("/api/pets").contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(petDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Pet in the database
        List<Pet> petList = petRepository.findAll();
        assertThat(petList).hasSize(databaseSizeBeforeUpdate);

        // Validate the Pet in Elasticsearch
        verify(mockPetSearchRepository, times(0)).save(pet);
    }

    @Test
    @Transactional
    public void deletePet() throws Exception {
        // Initialize the database
        petRepository.saveAndFlush(pet);

        int databaseSizeBeforeDelete = petRepository.findAll().size();

        // Delete the pet
        restPetMockMvc.perform(delete("/api/pets/{id}", pet.getId()).accept(MediaType.APPLICATION_JSON)).andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Pet> petList = petRepository.findAll();
        assertThat(petList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the Pet in Elasticsearch
        verify(mockPetSearchRepository, times(1)).deleteById(pet.getId());
    }

    @Test
    @Transactional
    public void searchPet() throws Exception {
        // Configure the mock search repository
        // Initialize the database
        petRepository.saveAndFlush(pet);
        when(mockPetSearchRepository.search(queryStringQuery("id:" + pet.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(pet), PageRequest.of(0, 1), 1));

        // Search the pet
        restPetMockMvc
            .perform(get("/api/_search/pets?query=id:" + pet.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(pet.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].imageUrl").value(hasItem(DEFAULT_IMAGE_URL)))
            .andExpect(jsonPath("$.[*].birthday").value(hasItem(DEFAULT_BIRTHDAY.toString())))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.toString())));
    }
}
