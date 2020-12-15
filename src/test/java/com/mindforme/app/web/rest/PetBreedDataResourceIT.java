package com.mindforme.app.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.elasticsearch.index.query.QueryBuilders.queryStringQuery;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.mindforme.app.MindformeApp;
import com.mindforme.app.domain.PetBreedData;
import com.mindforme.app.repository.PetBreedDataRepository;
import com.mindforme.app.repository.search.PetBreedDataSearchRepository;
import com.mindforme.app.service.PetBreedDataService;
import com.mindforme.app.service.dto.PetBreedDataDTO;
import com.mindforme.app.service.mapper.PetBreedDataMapper;
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
 * Integration tests for the {@link PetBreedDataResource} REST controller.
 */
@SpringBootTest(classes = MindformeApp.class)
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
public class PetBreedDataResourceIT {
    private static final String DEFAULT_CONTENT = "AAAAAAAAAA";
    private static final String UPDATED_CONTENT = "BBBBBBBBBB";

    @Autowired
    private PetBreedDataRepository petBreedDataRepository;

    @Autowired
    private PetBreedDataMapper petBreedDataMapper;

    @Autowired
    private PetBreedDataService petBreedDataService;

    /**
     * This repository is mocked in the com.mindforme.app.repository.search test package.
     *
     * @see com.mindforme.app.repository.search.PetBreedDataSearchRepositoryMockConfiguration
     */
    @Autowired
    private PetBreedDataSearchRepository mockPetBreedDataSearchRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restPetBreedDataMockMvc;

    private PetBreedData petBreedData;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PetBreedData createEntity(EntityManager em) {
        PetBreedData petBreedData = new PetBreedData().content(DEFAULT_CONTENT);
        return petBreedData;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PetBreedData createUpdatedEntity(EntityManager em) {
        PetBreedData petBreedData = new PetBreedData().content(UPDATED_CONTENT);
        return petBreedData;
    }

    @BeforeEach
    public void initTest() {
        petBreedData = createEntity(em);
    }

    @Test
    @Transactional
    public void createPetBreedData() throws Exception {
        int databaseSizeBeforeCreate = petBreedDataRepository.findAll().size();
        // Create the PetBreedData
        PetBreedDataDTO petBreedDataDTO = petBreedDataMapper.toDto(petBreedData);
        restPetBreedDataMockMvc
            .perform(
                post("/api/pet-breed-data")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(petBreedDataDTO))
            )
            .andExpect(status().isCreated());

        // Validate the PetBreedData in the database
        List<PetBreedData> petBreedDataList = petBreedDataRepository.findAll();
        assertThat(petBreedDataList).hasSize(databaseSizeBeforeCreate + 1);
        PetBreedData testPetBreedData = petBreedDataList.get(petBreedDataList.size() - 1);
        assertThat(testPetBreedData.getContent()).isEqualTo(DEFAULT_CONTENT);

        // Validate the PetBreedData in Elasticsearch
        verify(mockPetBreedDataSearchRepository, times(1)).save(testPetBreedData);
    }

    @Test
    @Transactional
    public void createPetBreedDataWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = petBreedDataRepository.findAll().size();

        // Create the PetBreedData with an existing ID
        petBreedData.setId(1L);
        PetBreedDataDTO petBreedDataDTO = petBreedDataMapper.toDto(petBreedData);

        // An entity with an existing ID cannot be created, so this API call must fail
        restPetBreedDataMockMvc
            .perform(
                post("/api/pet-breed-data")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(petBreedDataDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the PetBreedData in the database
        List<PetBreedData> petBreedDataList = petBreedDataRepository.findAll();
        assertThat(petBreedDataList).hasSize(databaseSizeBeforeCreate);

        // Validate the PetBreedData in Elasticsearch
        verify(mockPetBreedDataSearchRepository, times(0)).save(petBreedData);
    }

    @Test
    @Transactional
    public void getAllPetBreedData() throws Exception {
        // Initialize the database
        petBreedDataRepository.saveAndFlush(petBreedData);

        // Get all the petBreedDataList
        restPetBreedDataMockMvc
            .perform(get("/api/pet-breed-data?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(petBreedData.getId().intValue())))
            .andExpect(jsonPath("$.[*].content").value(hasItem(DEFAULT_CONTENT)));
    }

    @Test
    @Transactional
    public void getPetBreedData() throws Exception {
        // Initialize the database
        petBreedDataRepository.saveAndFlush(petBreedData);

        // Get the petBreedData
        restPetBreedDataMockMvc
            .perform(get("/api/pet-breed-data/{id}", petBreedData.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(petBreedData.getId().intValue()))
            .andExpect(jsonPath("$.content").value(DEFAULT_CONTENT));
    }

    @Test
    @Transactional
    public void getNonExistingPetBreedData() throws Exception {
        // Get the petBreedData
        restPetBreedDataMockMvc.perform(get("/api/pet-breed-data/{id}", Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePetBreedData() throws Exception {
        // Initialize the database
        petBreedDataRepository.saveAndFlush(petBreedData);

        int databaseSizeBeforeUpdate = petBreedDataRepository.findAll().size();

        // Update the petBreedData
        PetBreedData updatedPetBreedData = petBreedDataRepository.findById(petBreedData.getId()).get();
        // Disconnect from session so that the updates on updatedPetBreedData are not directly saved in db
        em.detach(updatedPetBreedData);
        updatedPetBreedData.content(UPDATED_CONTENT);
        PetBreedDataDTO petBreedDataDTO = petBreedDataMapper.toDto(updatedPetBreedData);

        restPetBreedDataMockMvc
            .perform(
                put("/api/pet-breed-data")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(petBreedDataDTO))
            )
            .andExpect(status().isOk());

        // Validate the PetBreedData in the database
        List<PetBreedData> petBreedDataList = petBreedDataRepository.findAll();
        assertThat(petBreedDataList).hasSize(databaseSizeBeforeUpdate);
        PetBreedData testPetBreedData = petBreedDataList.get(petBreedDataList.size() - 1);
        assertThat(testPetBreedData.getContent()).isEqualTo(UPDATED_CONTENT);

        // Validate the PetBreedData in Elasticsearch
        verify(mockPetBreedDataSearchRepository, times(1)).save(testPetBreedData);
    }

    @Test
    @Transactional
    public void updateNonExistingPetBreedData() throws Exception {
        int databaseSizeBeforeUpdate = petBreedDataRepository.findAll().size();

        // Create the PetBreedData
        PetBreedDataDTO petBreedDataDTO = petBreedDataMapper.toDto(petBreedData);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPetBreedDataMockMvc
            .perform(
                put("/api/pet-breed-data")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(petBreedDataDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the PetBreedData in the database
        List<PetBreedData> petBreedDataList = petBreedDataRepository.findAll();
        assertThat(petBreedDataList).hasSize(databaseSizeBeforeUpdate);

        // Validate the PetBreedData in Elasticsearch
        verify(mockPetBreedDataSearchRepository, times(0)).save(petBreedData);
    }

    @Test
    @Transactional
    public void deletePetBreedData() throws Exception {
        // Initialize the database
        petBreedDataRepository.saveAndFlush(petBreedData);

        int databaseSizeBeforeDelete = petBreedDataRepository.findAll().size();

        // Delete the petBreedData
        restPetBreedDataMockMvc
            .perform(delete("/api/pet-breed-data/{id}", petBreedData.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<PetBreedData> petBreedDataList = petBreedDataRepository.findAll();
        assertThat(petBreedDataList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the PetBreedData in Elasticsearch
        verify(mockPetBreedDataSearchRepository, times(1)).deleteById(petBreedData.getId());
    }

    @Test
    @Transactional
    public void searchPetBreedData() throws Exception {
        // Configure the mock search repository
        // Initialize the database
        petBreedDataRepository.saveAndFlush(petBreedData);
        when(mockPetBreedDataSearchRepository.search(queryStringQuery("id:" + petBreedData.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(petBreedData), PageRequest.of(0, 1), 1));

        // Search the petBreedData
        restPetBreedDataMockMvc
            .perform(get("/api/_search/pet-breed-data?query=id:" + petBreedData.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(petBreedData.getId().intValue())))
            .andExpect(jsonPath("$.[*].content").value(hasItem(DEFAULT_CONTENT)));
    }
}
