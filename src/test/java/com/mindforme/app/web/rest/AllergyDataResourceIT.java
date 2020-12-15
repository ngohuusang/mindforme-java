package com.mindforme.app.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.elasticsearch.index.query.QueryBuilders.queryStringQuery;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.mindforme.app.MindformeApp;
import com.mindforme.app.domain.AllergyData;
import com.mindforme.app.repository.AllergyDataRepository;
import com.mindforme.app.repository.search.AllergyDataSearchRepository;
import com.mindforme.app.service.AllergyDataService;
import com.mindforme.app.service.dto.AllergyDataDTO;
import com.mindforme.app.service.mapper.AllergyDataMapper;
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
 * Integration tests for the {@link AllergyDataResource} REST controller.
 */
@SpringBootTest(classes = MindformeApp.class)
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
public class AllergyDataResourceIT {
    private static final String DEFAULT_CONTENT = "AAAAAAAAAA";
    private static final String UPDATED_CONTENT = "BBBBBBBBBB";

    @Autowired
    private AllergyDataRepository allergyDataRepository;

    @Autowired
    private AllergyDataMapper allergyDataMapper;

    @Autowired
    private AllergyDataService allergyDataService;

    /**
     * This repository is mocked in the com.mindforme.app.repository.search test package.
     *
     * @see com.mindforme.app.repository.search.AllergyDataSearchRepositoryMockConfiguration
     */
    @Autowired
    private AllergyDataSearchRepository mockAllergyDataSearchRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restAllergyDataMockMvc;

    private AllergyData allergyData;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static AllergyData createEntity(EntityManager em) {
        AllergyData allergyData = new AllergyData().content(DEFAULT_CONTENT);
        return allergyData;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static AllergyData createUpdatedEntity(EntityManager em) {
        AllergyData allergyData = new AllergyData().content(UPDATED_CONTENT);
        return allergyData;
    }

    @BeforeEach
    public void initTest() {
        allergyData = createEntity(em);
    }

    @Test
    @Transactional
    public void createAllergyData() throws Exception {
        int databaseSizeBeforeCreate = allergyDataRepository.findAll().size();
        // Create the AllergyData
        AllergyDataDTO allergyDataDTO = allergyDataMapper.toDto(allergyData);
        restAllergyDataMockMvc
            .perform(
                post("/api/allergy-data").contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(allergyDataDTO))
            )
            .andExpect(status().isCreated());

        // Validate the AllergyData in the database
        List<AllergyData> allergyDataList = allergyDataRepository.findAll();
        assertThat(allergyDataList).hasSize(databaseSizeBeforeCreate + 1);
        AllergyData testAllergyData = allergyDataList.get(allergyDataList.size() - 1);
        assertThat(testAllergyData.getContent()).isEqualTo(DEFAULT_CONTENT);

        // Validate the AllergyData in Elasticsearch
        verify(mockAllergyDataSearchRepository, times(1)).save(testAllergyData);
    }

    @Test
    @Transactional
    public void createAllergyDataWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = allergyDataRepository.findAll().size();

        // Create the AllergyData with an existing ID
        allergyData.setId(1L);
        AllergyDataDTO allergyDataDTO = allergyDataMapper.toDto(allergyData);

        // An entity with an existing ID cannot be created, so this API call must fail
        restAllergyDataMockMvc
            .perform(
                post("/api/allergy-data").contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(allergyDataDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the AllergyData in the database
        List<AllergyData> allergyDataList = allergyDataRepository.findAll();
        assertThat(allergyDataList).hasSize(databaseSizeBeforeCreate);

        // Validate the AllergyData in Elasticsearch
        verify(mockAllergyDataSearchRepository, times(0)).save(allergyData);
    }

    @Test
    @Transactional
    public void getAllAllergyData() throws Exception {
        // Initialize the database
        allergyDataRepository.saveAndFlush(allergyData);

        // Get all the allergyDataList
        restAllergyDataMockMvc
            .perform(get("/api/allergy-data?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(allergyData.getId().intValue())))
            .andExpect(jsonPath("$.[*].content").value(hasItem(DEFAULT_CONTENT)));
    }

    @Test
    @Transactional
    public void getAllergyData() throws Exception {
        // Initialize the database
        allergyDataRepository.saveAndFlush(allergyData);

        // Get the allergyData
        restAllergyDataMockMvc
            .perform(get("/api/allergy-data/{id}", allergyData.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(allergyData.getId().intValue()))
            .andExpect(jsonPath("$.content").value(DEFAULT_CONTENT));
    }

    @Test
    @Transactional
    public void getNonExistingAllergyData() throws Exception {
        // Get the allergyData
        restAllergyDataMockMvc.perform(get("/api/allergy-data/{id}", Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateAllergyData() throws Exception {
        // Initialize the database
        allergyDataRepository.saveAndFlush(allergyData);

        int databaseSizeBeforeUpdate = allergyDataRepository.findAll().size();

        // Update the allergyData
        AllergyData updatedAllergyData = allergyDataRepository.findById(allergyData.getId()).get();
        // Disconnect from session so that the updates on updatedAllergyData are not directly saved in db
        em.detach(updatedAllergyData);
        updatedAllergyData.content(UPDATED_CONTENT);
        AllergyDataDTO allergyDataDTO = allergyDataMapper.toDto(updatedAllergyData);

        restAllergyDataMockMvc
            .perform(
                put("/api/allergy-data").contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(allergyDataDTO))
            )
            .andExpect(status().isOk());

        // Validate the AllergyData in the database
        List<AllergyData> allergyDataList = allergyDataRepository.findAll();
        assertThat(allergyDataList).hasSize(databaseSizeBeforeUpdate);
        AllergyData testAllergyData = allergyDataList.get(allergyDataList.size() - 1);
        assertThat(testAllergyData.getContent()).isEqualTo(UPDATED_CONTENT);

        // Validate the AllergyData in Elasticsearch
        verify(mockAllergyDataSearchRepository, times(1)).save(testAllergyData);
    }

    @Test
    @Transactional
    public void updateNonExistingAllergyData() throws Exception {
        int databaseSizeBeforeUpdate = allergyDataRepository.findAll().size();

        // Create the AllergyData
        AllergyDataDTO allergyDataDTO = allergyDataMapper.toDto(allergyData);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAllergyDataMockMvc
            .perform(
                put("/api/allergy-data").contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(allergyDataDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the AllergyData in the database
        List<AllergyData> allergyDataList = allergyDataRepository.findAll();
        assertThat(allergyDataList).hasSize(databaseSizeBeforeUpdate);

        // Validate the AllergyData in Elasticsearch
        verify(mockAllergyDataSearchRepository, times(0)).save(allergyData);
    }

    @Test
    @Transactional
    public void deleteAllergyData() throws Exception {
        // Initialize the database
        allergyDataRepository.saveAndFlush(allergyData);

        int databaseSizeBeforeDelete = allergyDataRepository.findAll().size();

        // Delete the allergyData
        restAllergyDataMockMvc
            .perform(delete("/api/allergy-data/{id}", allergyData.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<AllergyData> allergyDataList = allergyDataRepository.findAll();
        assertThat(allergyDataList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the AllergyData in Elasticsearch
        verify(mockAllergyDataSearchRepository, times(1)).deleteById(allergyData.getId());
    }

    @Test
    @Transactional
    public void searchAllergyData() throws Exception {
        // Configure the mock search repository
        // Initialize the database
        allergyDataRepository.saveAndFlush(allergyData);
        when(mockAllergyDataSearchRepository.search(queryStringQuery("id:" + allergyData.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(allergyData), PageRequest.of(0, 1), 1));

        // Search the allergyData
        restAllergyDataMockMvc
            .perform(get("/api/_search/allergy-data?query=id:" + allergyData.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(allergyData.getId().intValue())))
            .andExpect(jsonPath("$.[*].content").value(hasItem(DEFAULT_CONTENT)));
    }
}
