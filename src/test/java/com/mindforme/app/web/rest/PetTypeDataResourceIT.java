package com.mindforme.app.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.elasticsearch.index.query.QueryBuilders.queryStringQuery;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.mindforme.app.MindformeApp;
import com.mindforme.app.domain.PetTypeData;
import com.mindforme.app.repository.PetTypeDataRepository;
import com.mindforme.app.repository.search.PetTypeDataSearchRepository;
import com.mindforme.app.service.PetTypeDataService;
import com.mindforme.app.service.dto.PetTypeDataDTO;
import com.mindforme.app.service.mapper.PetTypeDataMapper;
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
 * Integration tests for the {@link PetTypeDataResource} REST controller.
 */
@SpringBootTest(classes = MindformeApp.class)
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
public class PetTypeDataResourceIT {
    private static final String DEFAULT_CONTENT = "AAAAAAAAAA";
    private static final String UPDATED_CONTENT = "BBBBBBBBBB";

    @Autowired
    private PetTypeDataRepository petTypeDataRepository;

    @Autowired
    private PetTypeDataMapper petTypeDataMapper;

    @Autowired
    private PetTypeDataService petTypeDataService;

    /**
     * This repository is mocked in the com.mindforme.app.repository.search test package.
     *
     * @see com.mindforme.app.repository.search.PetTypeDataSearchRepositoryMockConfiguration
     */
    @Autowired
    private PetTypeDataSearchRepository mockPetTypeDataSearchRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restPetTypeDataMockMvc;

    private PetTypeData petTypeData;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PetTypeData createEntity(EntityManager em) {
        PetTypeData petTypeData = new PetTypeData().content(DEFAULT_CONTENT);
        return petTypeData;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PetTypeData createUpdatedEntity(EntityManager em) {
        PetTypeData petTypeData = new PetTypeData().content(UPDATED_CONTENT);
        return petTypeData;
    }

    @BeforeEach
    public void initTest() {
        petTypeData = createEntity(em);
    }

    @Test
    @Transactional
    public void createPetTypeData() throws Exception {
        int databaseSizeBeforeCreate = petTypeDataRepository.findAll().size();
        // Create the PetTypeData
        PetTypeDataDTO petTypeDataDTO = petTypeDataMapper.toDto(petTypeData);
        restPetTypeDataMockMvc
            .perform(
                post("/api/pet-type-data")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(petTypeDataDTO))
            )
            .andExpect(status().isCreated());

        // Validate the PetTypeData in the database
        List<PetTypeData> petTypeDataList = petTypeDataRepository.findAll();
        assertThat(petTypeDataList).hasSize(databaseSizeBeforeCreate + 1);
        PetTypeData testPetTypeData = petTypeDataList.get(petTypeDataList.size() - 1);
        assertThat(testPetTypeData.getContent()).isEqualTo(DEFAULT_CONTENT);

        // Validate the PetTypeData in Elasticsearch
        verify(mockPetTypeDataSearchRepository, times(1)).save(testPetTypeData);
    }

    @Test
    @Transactional
    public void createPetTypeDataWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = petTypeDataRepository.findAll().size();

        // Create the PetTypeData with an existing ID
        petTypeData.setId(1L);
        PetTypeDataDTO petTypeDataDTO = petTypeDataMapper.toDto(petTypeData);

        // An entity with an existing ID cannot be created, so this API call must fail
        restPetTypeDataMockMvc
            .perform(
                post("/api/pet-type-data")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(petTypeDataDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the PetTypeData in the database
        List<PetTypeData> petTypeDataList = petTypeDataRepository.findAll();
        assertThat(petTypeDataList).hasSize(databaseSizeBeforeCreate);

        // Validate the PetTypeData in Elasticsearch
        verify(mockPetTypeDataSearchRepository, times(0)).save(petTypeData);
    }

    @Test
    @Transactional
    public void getAllPetTypeData() throws Exception {
        // Initialize the database
        petTypeDataRepository.saveAndFlush(petTypeData);

        // Get all the petTypeDataList
        restPetTypeDataMockMvc
            .perform(get("/api/pet-type-data?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(petTypeData.getId().intValue())))
            .andExpect(jsonPath("$.[*].content").value(hasItem(DEFAULT_CONTENT)));
    }

    @Test
    @Transactional
    public void getPetTypeData() throws Exception {
        // Initialize the database
        petTypeDataRepository.saveAndFlush(petTypeData);

        // Get the petTypeData
        restPetTypeDataMockMvc
            .perform(get("/api/pet-type-data/{id}", petTypeData.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(petTypeData.getId().intValue()))
            .andExpect(jsonPath("$.content").value(DEFAULT_CONTENT));
    }

    @Test
    @Transactional
    public void getNonExistingPetTypeData() throws Exception {
        // Get the petTypeData
        restPetTypeDataMockMvc.perform(get("/api/pet-type-data/{id}", Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePetTypeData() throws Exception {
        // Initialize the database
        petTypeDataRepository.saveAndFlush(petTypeData);

        int databaseSizeBeforeUpdate = petTypeDataRepository.findAll().size();

        // Update the petTypeData
        PetTypeData updatedPetTypeData = petTypeDataRepository.findById(petTypeData.getId()).get();
        // Disconnect from session so that the updates on updatedPetTypeData are not directly saved in db
        em.detach(updatedPetTypeData);
        updatedPetTypeData.content(UPDATED_CONTENT);
        PetTypeDataDTO petTypeDataDTO = petTypeDataMapper.toDto(updatedPetTypeData);

        restPetTypeDataMockMvc
            .perform(
                put("/api/pet-type-data").contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(petTypeDataDTO))
            )
            .andExpect(status().isOk());

        // Validate the PetTypeData in the database
        List<PetTypeData> petTypeDataList = petTypeDataRepository.findAll();
        assertThat(petTypeDataList).hasSize(databaseSizeBeforeUpdate);
        PetTypeData testPetTypeData = petTypeDataList.get(petTypeDataList.size() - 1);
        assertThat(testPetTypeData.getContent()).isEqualTo(UPDATED_CONTENT);

        // Validate the PetTypeData in Elasticsearch
        verify(mockPetTypeDataSearchRepository, times(1)).save(testPetTypeData);
    }

    @Test
    @Transactional
    public void updateNonExistingPetTypeData() throws Exception {
        int databaseSizeBeforeUpdate = petTypeDataRepository.findAll().size();

        // Create the PetTypeData
        PetTypeDataDTO petTypeDataDTO = petTypeDataMapper.toDto(petTypeData);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPetTypeDataMockMvc
            .perform(
                put("/api/pet-type-data").contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(petTypeDataDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the PetTypeData in the database
        List<PetTypeData> petTypeDataList = petTypeDataRepository.findAll();
        assertThat(petTypeDataList).hasSize(databaseSizeBeforeUpdate);

        // Validate the PetTypeData in Elasticsearch
        verify(mockPetTypeDataSearchRepository, times(0)).save(petTypeData);
    }

    @Test
    @Transactional
    public void deletePetTypeData() throws Exception {
        // Initialize the database
        petTypeDataRepository.saveAndFlush(petTypeData);

        int databaseSizeBeforeDelete = petTypeDataRepository.findAll().size();

        // Delete the petTypeData
        restPetTypeDataMockMvc
            .perform(delete("/api/pet-type-data/{id}", petTypeData.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<PetTypeData> petTypeDataList = petTypeDataRepository.findAll();
        assertThat(petTypeDataList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the PetTypeData in Elasticsearch
        verify(mockPetTypeDataSearchRepository, times(1)).deleteById(petTypeData.getId());
    }

    @Test
    @Transactional
    public void searchPetTypeData() throws Exception {
        // Configure the mock search repository
        // Initialize the database
        petTypeDataRepository.saveAndFlush(petTypeData);
        when(mockPetTypeDataSearchRepository.search(queryStringQuery("id:" + petTypeData.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(petTypeData), PageRequest.of(0, 1), 1));

        // Search the petTypeData
        restPetTypeDataMockMvc
            .perform(get("/api/_search/pet-type-data?query=id:" + petTypeData.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(petTypeData.getId().intValue())))
            .andExpect(jsonPath("$.[*].content").value(hasItem(DEFAULT_CONTENT)));
    }
}
