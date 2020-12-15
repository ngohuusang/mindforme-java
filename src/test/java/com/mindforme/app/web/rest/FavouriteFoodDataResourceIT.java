package com.mindforme.app.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.elasticsearch.index.query.QueryBuilders.queryStringQuery;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.mindforme.app.MindformeApp;
import com.mindforme.app.domain.FavouriteFoodData;
import com.mindforme.app.repository.FavouriteFoodDataRepository;
import com.mindforme.app.repository.search.FavouriteFoodDataSearchRepository;
import com.mindforme.app.service.FavouriteFoodDataService;
import com.mindforme.app.service.dto.FavouriteFoodDataDTO;
import com.mindforme.app.service.mapper.FavouriteFoodDataMapper;
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
 * Integration tests for the {@link FavouriteFoodDataResource} REST controller.
 */
@SpringBootTest(classes = MindformeApp.class)
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
public class FavouriteFoodDataResourceIT {
    private static final String DEFAULT_CONTENT = "AAAAAAAAAA";
    private static final String UPDATED_CONTENT = "BBBBBBBBBB";

    @Autowired
    private FavouriteFoodDataRepository favouriteFoodDataRepository;

    @Autowired
    private FavouriteFoodDataMapper favouriteFoodDataMapper;

    @Autowired
    private FavouriteFoodDataService favouriteFoodDataService;

    /**
     * This repository is mocked in the com.mindforme.app.repository.search test package.
     *
     * @see com.mindforme.app.repository.search.FavouriteFoodDataSearchRepositoryMockConfiguration
     */
    @Autowired
    private FavouriteFoodDataSearchRepository mockFavouriteFoodDataSearchRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restFavouriteFoodDataMockMvc;

    private FavouriteFoodData favouriteFoodData;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static FavouriteFoodData createEntity(EntityManager em) {
        FavouriteFoodData favouriteFoodData = new FavouriteFoodData().content(DEFAULT_CONTENT);
        return favouriteFoodData;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static FavouriteFoodData createUpdatedEntity(EntityManager em) {
        FavouriteFoodData favouriteFoodData = new FavouriteFoodData().content(UPDATED_CONTENT);
        return favouriteFoodData;
    }

    @BeforeEach
    public void initTest() {
        favouriteFoodData = createEntity(em);
    }

    @Test
    @Transactional
    public void createFavouriteFoodData() throws Exception {
        int databaseSizeBeforeCreate = favouriteFoodDataRepository.findAll().size();
        // Create the FavouriteFoodData
        FavouriteFoodDataDTO favouriteFoodDataDTO = favouriteFoodDataMapper.toDto(favouriteFoodData);
        restFavouriteFoodDataMockMvc
            .perform(
                post("/api/favourite-food-data")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(favouriteFoodDataDTO))
            )
            .andExpect(status().isCreated());

        // Validate the FavouriteFoodData in the database
        List<FavouriteFoodData> favouriteFoodDataList = favouriteFoodDataRepository.findAll();
        assertThat(favouriteFoodDataList).hasSize(databaseSizeBeforeCreate + 1);
        FavouriteFoodData testFavouriteFoodData = favouriteFoodDataList.get(favouriteFoodDataList.size() - 1);
        assertThat(testFavouriteFoodData.getContent()).isEqualTo(DEFAULT_CONTENT);

        // Validate the FavouriteFoodData in Elasticsearch
        verify(mockFavouriteFoodDataSearchRepository, times(1)).save(testFavouriteFoodData);
    }

    @Test
    @Transactional
    public void createFavouriteFoodDataWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = favouriteFoodDataRepository.findAll().size();

        // Create the FavouriteFoodData with an existing ID
        favouriteFoodData.setId(1L);
        FavouriteFoodDataDTO favouriteFoodDataDTO = favouriteFoodDataMapper.toDto(favouriteFoodData);

        // An entity with an existing ID cannot be created, so this API call must fail
        restFavouriteFoodDataMockMvc
            .perform(
                post("/api/favourite-food-data")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(favouriteFoodDataDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the FavouriteFoodData in the database
        List<FavouriteFoodData> favouriteFoodDataList = favouriteFoodDataRepository.findAll();
        assertThat(favouriteFoodDataList).hasSize(databaseSizeBeforeCreate);

        // Validate the FavouriteFoodData in Elasticsearch
        verify(mockFavouriteFoodDataSearchRepository, times(0)).save(favouriteFoodData);
    }

    @Test
    @Transactional
    public void getAllFavouriteFoodData() throws Exception {
        // Initialize the database
        favouriteFoodDataRepository.saveAndFlush(favouriteFoodData);

        // Get all the favouriteFoodDataList
        restFavouriteFoodDataMockMvc
            .perform(get("/api/favourite-food-data?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(favouriteFoodData.getId().intValue())))
            .andExpect(jsonPath("$.[*].content").value(hasItem(DEFAULT_CONTENT)));
    }

    @Test
    @Transactional
    public void getFavouriteFoodData() throws Exception {
        // Initialize the database
        favouriteFoodDataRepository.saveAndFlush(favouriteFoodData);

        // Get the favouriteFoodData
        restFavouriteFoodDataMockMvc
            .perform(get("/api/favourite-food-data/{id}", favouriteFoodData.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(favouriteFoodData.getId().intValue()))
            .andExpect(jsonPath("$.content").value(DEFAULT_CONTENT));
    }

    @Test
    @Transactional
    public void getNonExistingFavouriteFoodData() throws Exception {
        // Get the favouriteFoodData
        restFavouriteFoodDataMockMvc.perform(get("/api/favourite-food-data/{id}", Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateFavouriteFoodData() throws Exception {
        // Initialize the database
        favouriteFoodDataRepository.saveAndFlush(favouriteFoodData);

        int databaseSizeBeforeUpdate = favouriteFoodDataRepository.findAll().size();

        // Update the favouriteFoodData
        FavouriteFoodData updatedFavouriteFoodData = favouriteFoodDataRepository.findById(favouriteFoodData.getId()).get();
        // Disconnect from session so that the updates on updatedFavouriteFoodData are not directly saved in db
        em.detach(updatedFavouriteFoodData);
        updatedFavouriteFoodData.content(UPDATED_CONTENT);
        FavouriteFoodDataDTO favouriteFoodDataDTO = favouriteFoodDataMapper.toDto(updatedFavouriteFoodData);

        restFavouriteFoodDataMockMvc
            .perform(
                put("/api/favourite-food-data")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(favouriteFoodDataDTO))
            )
            .andExpect(status().isOk());

        // Validate the FavouriteFoodData in the database
        List<FavouriteFoodData> favouriteFoodDataList = favouriteFoodDataRepository.findAll();
        assertThat(favouriteFoodDataList).hasSize(databaseSizeBeforeUpdate);
        FavouriteFoodData testFavouriteFoodData = favouriteFoodDataList.get(favouriteFoodDataList.size() - 1);
        assertThat(testFavouriteFoodData.getContent()).isEqualTo(UPDATED_CONTENT);

        // Validate the FavouriteFoodData in Elasticsearch
        verify(mockFavouriteFoodDataSearchRepository, times(1)).save(testFavouriteFoodData);
    }

    @Test
    @Transactional
    public void updateNonExistingFavouriteFoodData() throws Exception {
        int databaseSizeBeforeUpdate = favouriteFoodDataRepository.findAll().size();

        // Create the FavouriteFoodData
        FavouriteFoodDataDTO favouriteFoodDataDTO = favouriteFoodDataMapper.toDto(favouriteFoodData);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restFavouriteFoodDataMockMvc
            .perform(
                put("/api/favourite-food-data")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(favouriteFoodDataDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the FavouriteFoodData in the database
        List<FavouriteFoodData> favouriteFoodDataList = favouriteFoodDataRepository.findAll();
        assertThat(favouriteFoodDataList).hasSize(databaseSizeBeforeUpdate);

        // Validate the FavouriteFoodData in Elasticsearch
        verify(mockFavouriteFoodDataSearchRepository, times(0)).save(favouriteFoodData);
    }

    @Test
    @Transactional
    public void deleteFavouriteFoodData() throws Exception {
        // Initialize the database
        favouriteFoodDataRepository.saveAndFlush(favouriteFoodData);

        int databaseSizeBeforeDelete = favouriteFoodDataRepository.findAll().size();

        // Delete the favouriteFoodData
        restFavouriteFoodDataMockMvc
            .perform(delete("/api/favourite-food-data/{id}", favouriteFoodData.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<FavouriteFoodData> favouriteFoodDataList = favouriteFoodDataRepository.findAll();
        assertThat(favouriteFoodDataList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the FavouriteFoodData in Elasticsearch
        verify(mockFavouriteFoodDataSearchRepository, times(1)).deleteById(favouriteFoodData.getId());
    }

    @Test
    @Transactional
    public void searchFavouriteFoodData() throws Exception {
        // Configure the mock search repository
        // Initialize the database
        favouriteFoodDataRepository.saveAndFlush(favouriteFoodData);
        when(mockFavouriteFoodDataSearchRepository.search(queryStringQuery("id:" + favouriteFoodData.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(favouriteFoodData), PageRequest.of(0, 1), 1));

        // Search the favouriteFoodData
        restFavouriteFoodDataMockMvc
            .perform(get("/api/_search/favourite-food-data?query=id:" + favouriteFoodData.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(favouriteFoodData.getId().intValue())))
            .andExpect(jsonPath("$.[*].content").value(hasItem(DEFAULT_CONTENT)));
    }
}
