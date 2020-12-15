package com.mindforme.app.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.elasticsearch.index.query.QueryBuilders.queryStringQuery;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.mindforme.app.MindformeApp;
import com.mindforme.app.domain.CountryData;
import com.mindforme.app.repository.CountryDataRepository;
import com.mindforme.app.repository.search.CountryDataSearchRepository;
import com.mindforme.app.service.CountryDataService;
import com.mindforme.app.service.dto.CountryDataDTO;
import com.mindforme.app.service.mapper.CountryDataMapper;
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
 * Integration tests for the {@link CountryDataResource} REST controller.
 */
@SpringBootTest(classes = MindformeApp.class)
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
public class CountryDataResourceIT {
    private static final String DEFAULT_CONTENT = "AAAAAAAAAA";
    private static final String UPDATED_CONTENT = "BBBBBBBBBB";

    @Autowired
    private CountryDataRepository countryDataRepository;

    @Autowired
    private CountryDataMapper countryDataMapper;

    @Autowired
    private CountryDataService countryDataService;

    /**
     * This repository is mocked in the com.mindforme.app.repository.search test package.
     *
     * @see com.mindforme.app.repository.search.CountryDataSearchRepositoryMockConfiguration
     */
    @Autowired
    private CountryDataSearchRepository mockCountryDataSearchRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restCountryDataMockMvc;

    private CountryData countryData;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CountryData createEntity(EntityManager em) {
        CountryData countryData = new CountryData().content(DEFAULT_CONTENT);
        return countryData;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CountryData createUpdatedEntity(EntityManager em) {
        CountryData countryData = new CountryData().content(UPDATED_CONTENT);
        return countryData;
    }

    @BeforeEach
    public void initTest() {
        countryData = createEntity(em);
    }

    @Test
    @Transactional
    public void createCountryData() throws Exception {
        int databaseSizeBeforeCreate = countryDataRepository.findAll().size();
        // Create the CountryData
        CountryDataDTO countryDataDTO = countryDataMapper.toDto(countryData);
        restCountryDataMockMvc
            .perform(
                post("/api/country-data").contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(countryDataDTO))
            )
            .andExpect(status().isCreated());

        // Validate the CountryData in the database
        List<CountryData> countryDataList = countryDataRepository.findAll();
        assertThat(countryDataList).hasSize(databaseSizeBeforeCreate + 1);
        CountryData testCountryData = countryDataList.get(countryDataList.size() - 1);
        assertThat(testCountryData.getContent()).isEqualTo(DEFAULT_CONTENT);

        // Validate the CountryData in Elasticsearch
        verify(mockCountryDataSearchRepository, times(1)).save(testCountryData);
    }

    @Test
    @Transactional
    public void createCountryDataWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = countryDataRepository.findAll().size();

        // Create the CountryData with an existing ID
        countryData.setId(1L);
        CountryDataDTO countryDataDTO = countryDataMapper.toDto(countryData);

        // An entity with an existing ID cannot be created, so this API call must fail
        restCountryDataMockMvc
            .perform(
                post("/api/country-data").contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(countryDataDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the CountryData in the database
        List<CountryData> countryDataList = countryDataRepository.findAll();
        assertThat(countryDataList).hasSize(databaseSizeBeforeCreate);

        // Validate the CountryData in Elasticsearch
        verify(mockCountryDataSearchRepository, times(0)).save(countryData);
    }

    @Test
    @Transactional
    public void getAllCountryData() throws Exception {
        // Initialize the database
        countryDataRepository.saveAndFlush(countryData);

        // Get all the countryDataList
        restCountryDataMockMvc
            .perform(get("/api/country-data?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(countryData.getId().intValue())))
            .andExpect(jsonPath("$.[*].content").value(hasItem(DEFAULT_CONTENT)));
    }

    @Test
    @Transactional
    public void getCountryData() throws Exception {
        // Initialize the database
        countryDataRepository.saveAndFlush(countryData);

        // Get the countryData
        restCountryDataMockMvc
            .perform(get("/api/country-data/{id}", countryData.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(countryData.getId().intValue()))
            .andExpect(jsonPath("$.content").value(DEFAULT_CONTENT));
    }

    @Test
    @Transactional
    public void getNonExistingCountryData() throws Exception {
        // Get the countryData
        restCountryDataMockMvc.perform(get("/api/country-data/{id}", Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCountryData() throws Exception {
        // Initialize the database
        countryDataRepository.saveAndFlush(countryData);

        int databaseSizeBeforeUpdate = countryDataRepository.findAll().size();

        // Update the countryData
        CountryData updatedCountryData = countryDataRepository.findById(countryData.getId()).get();
        // Disconnect from session so that the updates on updatedCountryData are not directly saved in db
        em.detach(updatedCountryData);
        updatedCountryData.content(UPDATED_CONTENT);
        CountryDataDTO countryDataDTO = countryDataMapper.toDto(updatedCountryData);

        restCountryDataMockMvc
            .perform(
                put("/api/country-data").contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(countryDataDTO))
            )
            .andExpect(status().isOk());

        // Validate the CountryData in the database
        List<CountryData> countryDataList = countryDataRepository.findAll();
        assertThat(countryDataList).hasSize(databaseSizeBeforeUpdate);
        CountryData testCountryData = countryDataList.get(countryDataList.size() - 1);
        assertThat(testCountryData.getContent()).isEqualTo(UPDATED_CONTENT);

        // Validate the CountryData in Elasticsearch
        verify(mockCountryDataSearchRepository, times(1)).save(testCountryData);
    }

    @Test
    @Transactional
    public void updateNonExistingCountryData() throws Exception {
        int databaseSizeBeforeUpdate = countryDataRepository.findAll().size();

        // Create the CountryData
        CountryDataDTO countryDataDTO = countryDataMapper.toDto(countryData);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCountryDataMockMvc
            .perform(
                put("/api/country-data").contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(countryDataDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the CountryData in the database
        List<CountryData> countryDataList = countryDataRepository.findAll();
        assertThat(countryDataList).hasSize(databaseSizeBeforeUpdate);

        // Validate the CountryData in Elasticsearch
        verify(mockCountryDataSearchRepository, times(0)).save(countryData);
    }

    @Test
    @Transactional
    public void deleteCountryData() throws Exception {
        // Initialize the database
        countryDataRepository.saveAndFlush(countryData);

        int databaseSizeBeforeDelete = countryDataRepository.findAll().size();

        // Delete the countryData
        restCountryDataMockMvc
            .perform(delete("/api/country-data/{id}", countryData.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<CountryData> countryDataList = countryDataRepository.findAll();
        assertThat(countryDataList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the CountryData in Elasticsearch
        verify(mockCountryDataSearchRepository, times(1)).deleteById(countryData.getId());
    }

    @Test
    @Transactional
    public void searchCountryData() throws Exception {
        // Configure the mock search repository
        // Initialize the database
        countryDataRepository.saveAndFlush(countryData);
        when(mockCountryDataSearchRepository.search(queryStringQuery("id:" + countryData.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(countryData), PageRequest.of(0, 1), 1));

        // Search the countryData
        restCountryDataMockMvc
            .perform(get("/api/_search/country-data?query=id:" + countryData.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(countryData.getId().intValue())))
            .andExpect(jsonPath("$.[*].content").value(hasItem(DEFAULT_CONTENT)));
    }
}
