package com.mindforme.app.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.elasticsearch.index.query.QueryBuilders.queryStringQuery;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.mindforme.app.MindformeApp;
import com.mindforme.app.domain.FavouriteFood;
import com.mindforme.app.domain.enumeration.Status;
import com.mindforme.app.repository.FavouriteFoodRepository;
import com.mindforme.app.repository.search.FavouriteFoodSearchRepository;
import com.mindforme.app.service.FavouriteFoodService;
import com.mindforme.app.service.dto.FavouriteFoodDTO;
import com.mindforme.app.service.mapper.FavouriteFoodMapper;
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
 * Integration tests for the {@link FavouriteFoodResource} REST controller.
 */
@SpringBootTest(classes = MindformeApp.class)
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
public class FavouriteFoodResourceIT {
    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final Status DEFAULT_STATUS = Status.Active;
    private static final Status UPDATED_STATUS = Status.Inactive;

    @Autowired
    private FavouriteFoodRepository favouriteFoodRepository;

    @Autowired
    private FavouriteFoodMapper favouriteFoodMapper;

    @Autowired
    private FavouriteFoodService favouriteFoodService;

    /**
     * This repository is mocked in the com.mindforme.app.repository.search test package.
     *
     * @see com.mindforme.app.repository.search.FavouriteFoodSearchRepositoryMockConfiguration
     */
    @Autowired
    private FavouriteFoodSearchRepository mockFavouriteFoodSearchRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restFavouriteFoodMockMvc;

    private FavouriteFood favouriteFood;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static FavouriteFood createEntity(EntityManager em) {
        FavouriteFood favouriteFood = new FavouriteFood().name(DEFAULT_NAME).status(DEFAULT_STATUS);
        return favouriteFood;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static FavouriteFood createUpdatedEntity(EntityManager em) {
        FavouriteFood favouriteFood = new FavouriteFood().name(UPDATED_NAME).status(UPDATED_STATUS);
        return favouriteFood;
    }

    @BeforeEach
    public void initTest() {
        favouriteFood = createEntity(em);
    }

    @Test
    @Transactional
    public void createFavouriteFood() throws Exception {
        int databaseSizeBeforeCreate = favouriteFoodRepository.findAll().size();
        // Create the FavouriteFood
        FavouriteFoodDTO favouriteFoodDTO = favouriteFoodMapper.toDto(favouriteFood);
        restFavouriteFoodMockMvc
            .perform(
                post("/api/favourite-foods")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(favouriteFoodDTO))
            )
            .andExpect(status().isCreated());

        // Validate the FavouriteFood in the database
        List<FavouriteFood> favouriteFoodList = favouriteFoodRepository.findAll();
        assertThat(favouriteFoodList).hasSize(databaseSizeBeforeCreate + 1);
        FavouriteFood testFavouriteFood = favouriteFoodList.get(favouriteFoodList.size() - 1);
        assertThat(testFavouriteFood.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testFavouriteFood.getStatus()).isEqualTo(DEFAULT_STATUS);

        // Validate the FavouriteFood in Elasticsearch
        verify(mockFavouriteFoodSearchRepository, times(1)).save(testFavouriteFood);
    }

    @Test
    @Transactional
    public void createFavouriteFoodWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = favouriteFoodRepository.findAll().size();

        // Create the FavouriteFood with an existing ID
        favouriteFood.setId(1L);
        FavouriteFoodDTO favouriteFoodDTO = favouriteFoodMapper.toDto(favouriteFood);

        // An entity with an existing ID cannot be created, so this API call must fail
        restFavouriteFoodMockMvc
            .perform(
                post("/api/favourite-foods")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(favouriteFoodDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the FavouriteFood in the database
        List<FavouriteFood> favouriteFoodList = favouriteFoodRepository.findAll();
        assertThat(favouriteFoodList).hasSize(databaseSizeBeforeCreate);

        // Validate the FavouriteFood in Elasticsearch
        verify(mockFavouriteFoodSearchRepository, times(0)).save(favouriteFood);
    }

    @Test
    @Transactional
    public void getAllFavouriteFoods() throws Exception {
        // Initialize the database
        favouriteFoodRepository.saveAndFlush(favouriteFood);

        // Get all the favouriteFoodList
        restFavouriteFoodMockMvc
            .perform(get("/api/favourite-foods?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(favouriteFood.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.toString())));
    }

    @Test
    @Transactional
    public void getFavouriteFood() throws Exception {
        // Initialize the database
        favouriteFoodRepository.saveAndFlush(favouriteFood);

        // Get the favouriteFood
        restFavouriteFoodMockMvc
            .perform(get("/api/favourite-foods/{id}", favouriteFood.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(favouriteFood.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingFavouriteFood() throws Exception {
        // Get the favouriteFood
        restFavouriteFoodMockMvc.perform(get("/api/favourite-foods/{id}", Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateFavouriteFood() throws Exception {
        // Initialize the database
        favouriteFoodRepository.saveAndFlush(favouriteFood);

        int databaseSizeBeforeUpdate = favouriteFoodRepository.findAll().size();

        // Update the favouriteFood
        FavouriteFood updatedFavouriteFood = favouriteFoodRepository.findById(favouriteFood.getId()).get();
        // Disconnect from session so that the updates on updatedFavouriteFood are not directly saved in db
        em.detach(updatedFavouriteFood);
        updatedFavouriteFood.name(UPDATED_NAME).status(UPDATED_STATUS);
        FavouriteFoodDTO favouriteFoodDTO = favouriteFoodMapper.toDto(updatedFavouriteFood);

        restFavouriteFoodMockMvc
            .perform(
                put("/api/favourite-foods")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(favouriteFoodDTO))
            )
            .andExpect(status().isOk());

        // Validate the FavouriteFood in the database
        List<FavouriteFood> favouriteFoodList = favouriteFoodRepository.findAll();
        assertThat(favouriteFoodList).hasSize(databaseSizeBeforeUpdate);
        FavouriteFood testFavouriteFood = favouriteFoodList.get(favouriteFoodList.size() - 1);
        assertThat(testFavouriteFood.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testFavouriteFood.getStatus()).isEqualTo(UPDATED_STATUS);

        // Validate the FavouriteFood in Elasticsearch
        verify(mockFavouriteFoodSearchRepository, times(1)).save(testFavouriteFood);
    }

    @Test
    @Transactional
    public void updateNonExistingFavouriteFood() throws Exception {
        int databaseSizeBeforeUpdate = favouriteFoodRepository.findAll().size();

        // Create the FavouriteFood
        FavouriteFoodDTO favouriteFoodDTO = favouriteFoodMapper.toDto(favouriteFood);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restFavouriteFoodMockMvc
            .perform(
                put("/api/favourite-foods")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(favouriteFoodDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the FavouriteFood in the database
        List<FavouriteFood> favouriteFoodList = favouriteFoodRepository.findAll();
        assertThat(favouriteFoodList).hasSize(databaseSizeBeforeUpdate);

        // Validate the FavouriteFood in Elasticsearch
        verify(mockFavouriteFoodSearchRepository, times(0)).save(favouriteFood);
    }

    @Test
    @Transactional
    public void deleteFavouriteFood() throws Exception {
        // Initialize the database
        favouriteFoodRepository.saveAndFlush(favouriteFood);

        int databaseSizeBeforeDelete = favouriteFoodRepository.findAll().size();

        // Delete the favouriteFood
        restFavouriteFoodMockMvc
            .perform(delete("/api/favourite-foods/{id}", favouriteFood.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<FavouriteFood> favouriteFoodList = favouriteFoodRepository.findAll();
        assertThat(favouriteFoodList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the FavouriteFood in Elasticsearch
        verify(mockFavouriteFoodSearchRepository, times(1)).deleteById(favouriteFood.getId());
    }

    @Test
    @Transactional
    public void searchFavouriteFood() throws Exception {
        // Configure the mock search repository
        // Initialize the database
        favouriteFoodRepository.saveAndFlush(favouriteFood);
        when(mockFavouriteFoodSearchRepository.search(queryStringQuery("id:" + favouriteFood.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(favouriteFood), PageRequest.of(0, 1), 1));

        // Search the favouriteFood
        restFavouriteFoodMockMvc
            .perform(get("/api/_search/favourite-foods?query=id:" + favouriteFood.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(favouriteFood.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.toString())));
    }
}
