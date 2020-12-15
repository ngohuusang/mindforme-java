package com.mindforme.app.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.elasticsearch.index.query.QueryBuilders.queryStringQuery;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.mindforme.app.MindformeApp;
import com.mindforme.app.domain.CityData;
import com.mindforme.app.repository.CityDataRepository;
import com.mindforme.app.repository.search.CityDataSearchRepository;
import com.mindforme.app.service.CityDataService;
import com.mindforme.app.service.dto.CityDataDTO;
import com.mindforme.app.service.mapper.CityDataMapper;
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
 * Integration tests for the {@link CityDataResource} REST controller.
 */
@SpringBootTest(classes = MindformeApp.class)
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
public class CityDataResourceIT {
    private static final String DEFAULT_CONTENT = "AAAAAAAAAA";
    private static final String UPDATED_CONTENT = "BBBBBBBBBB";

    @Autowired
    private CityDataRepository cityDataRepository;

    @Autowired
    private CityDataMapper cityDataMapper;

    @Autowired
    private CityDataService cityDataService;

    /**
     * This repository is mocked in the com.mindforme.app.repository.search test package.
     *
     * @see com.mindforme.app.repository.search.CityDataSearchRepositoryMockConfiguration
     */
    @Autowired
    private CityDataSearchRepository mockCityDataSearchRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restCityDataMockMvc;

    private CityData cityData;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CityData createEntity(EntityManager em) {
        CityData cityData = new CityData().content(DEFAULT_CONTENT);
        return cityData;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CityData createUpdatedEntity(EntityManager em) {
        CityData cityData = new CityData().content(UPDATED_CONTENT);
        return cityData;
    }

    @BeforeEach
    public void initTest() {
        cityData = createEntity(em);
    }

    @Test
    @Transactional
    public void createCityData() throws Exception {
        int databaseSizeBeforeCreate = cityDataRepository.findAll().size();
        // Create the CityData
        CityDataDTO cityDataDTO = cityDataMapper.toDto(cityData);
        restCityDataMockMvc
            .perform(post("/api/city-data").contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(cityDataDTO)))
            .andExpect(status().isCreated());

        // Validate the CityData in the database
        List<CityData> cityDataList = cityDataRepository.findAll();
        assertThat(cityDataList).hasSize(databaseSizeBeforeCreate + 1);
        CityData testCityData = cityDataList.get(cityDataList.size() - 1);
        assertThat(testCityData.getContent()).isEqualTo(DEFAULT_CONTENT);

        // Validate the CityData in Elasticsearch
        verify(mockCityDataSearchRepository, times(1)).save(testCityData);
    }

    @Test
    @Transactional
    public void createCityDataWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = cityDataRepository.findAll().size();

        // Create the CityData with an existing ID
        cityData.setId(1L);
        CityDataDTO cityDataDTO = cityDataMapper.toDto(cityData);

        // An entity with an existing ID cannot be created, so this API call must fail
        restCityDataMockMvc
            .perform(post("/api/city-data").contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(cityDataDTO)))
            .andExpect(status().isBadRequest());

        // Validate the CityData in the database
        List<CityData> cityDataList = cityDataRepository.findAll();
        assertThat(cityDataList).hasSize(databaseSizeBeforeCreate);

        // Validate the CityData in Elasticsearch
        verify(mockCityDataSearchRepository, times(0)).save(cityData);
    }

    @Test
    @Transactional
    public void getAllCityData() throws Exception {
        // Initialize the database
        cityDataRepository.saveAndFlush(cityData);

        // Get all the cityDataList
        restCityDataMockMvc
            .perform(get("/api/city-data?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(cityData.getId().intValue())))
            .andExpect(jsonPath("$.[*].content").value(hasItem(DEFAULT_CONTENT)));
    }

    @Test
    @Transactional
    public void getCityData() throws Exception {
        // Initialize the database
        cityDataRepository.saveAndFlush(cityData);

        // Get the cityData
        restCityDataMockMvc
            .perform(get("/api/city-data/{id}", cityData.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(cityData.getId().intValue()))
            .andExpect(jsonPath("$.content").value(DEFAULT_CONTENT));
    }

    @Test
    @Transactional
    public void getNonExistingCityData() throws Exception {
        // Get the cityData
        restCityDataMockMvc.perform(get("/api/city-data/{id}", Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCityData() throws Exception {
        // Initialize the database
        cityDataRepository.saveAndFlush(cityData);

        int databaseSizeBeforeUpdate = cityDataRepository.findAll().size();

        // Update the cityData
        CityData updatedCityData = cityDataRepository.findById(cityData.getId()).get();
        // Disconnect from session so that the updates on updatedCityData are not directly saved in db
        em.detach(updatedCityData);
        updatedCityData.content(UPDATED_CONTENT);
        CityDataDTO cityDataDTO = cityDataMapper.toDto(updatedCityData);

        restCityDataMockMvc
            .perform(put("/api/city-data").contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(cityDataDTO)))
            .andExpect(status().isOk());

        // Validate the CityData in the database
        List<CityData> cityDataList = cityDataRepository.findAll();
        assertThat(cityDataList).hasSize(databaseSizeBeforeUpdate);
        CityData testCityData = cityDataList.get(cityDataList.size() - 1);
        assertThat(testCityData.getContent()).isEqualTo(UPDATED_CONTENT);

        // Validate the CityData in Elasticsearch
        verify(mockCityDataSearchRepository, times(1)).save(testCityData);
    }

    @Test
    @Transactional
    public void updateNonExistingCityData() throws Exception {
        int databaseSizeBeforeUpdate = cityDataRepository.findAll().size();

        // Create the CityData
        CityDataDTO cityDataDTO = cityDataMapper.toDto(cityData);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCityDataMockMvc
            .perform(put("/api/city-data").contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(cityDataDTO)))
            .andExpect(status().isBadRequest());

        // Validate the CityData in the database
        List<CityData> cityDataList = cityDataRepository.findAll();
        assertThat(cityDataList).hasSize(databaseSizeBeforeUpdate);

        // Validate the CityData in Elasticsearch
        verify(mockCityDataSearchRepository, times(0)).save(cityData);
    }

    @Test
    @Transactional
    public void deleteCityData() throws Exception {
        // Initialize the database
        cityDataRepository.saveAndFlush(cityData);

        int databaseSizeBeforeDelete = cityDataRepository.findAll().size();

        // Delete the cityData
        restCityDataMockMvc
            .perform(delete("/api/city-data/{id}", cityData.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<CityData> cityDataList = cityDataRepository.findAll();
        assertThat(cityDataList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the CityData in Elasticsearch
        verify(mockCityDataSearchRepository, times(1)).deleteById(cityData.getId());
    }

    @Test
    @Transactional
    public void searchCityData() throws Exception {
        // Configure the mock search repository
        // Initialize the database
        cityDataRepository.saveAndFlush(cityData);
        when(mockCityDataSearchRepository.search(queryStringQuery("id:" + cityData.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(cityData), PageRequest.of(0, 1), 1));

        // Search the cityData
        restCityDataMockMvc
            .perform(get("/api/_search/city-data?query=id:" + cityData.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(cityData.getId().intValue())))
            .andExpect(jsonPath("$.[*].content").value(hasItem(DEFAULT_CONTENT)));
    }
}
