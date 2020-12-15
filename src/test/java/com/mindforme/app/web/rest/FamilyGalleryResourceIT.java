package com.mindforme.app.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.elasticsearch.index.query.QueryBuilders.queryStringQuery;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.mindforme.app.MindformeApp;
import com.mindforme.app.domain.FamilyGallery;
import com.mindforme.app.domain.enumeration.Privacy;
import com.mindforme.app.repository.FamilyGalleryRepository;
import com.mindforme.app.repository.search.FamilyGallerySearchRepository;
import com.mindforme.app.service.FamilyGalleryService;
import com.mindforme.app.service.dto.FamilyGalleryDTO;
import com.mindforme.app.service.mapper.FamilyGalleryMapper;
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
 * Integration tests for the {@link FamilyGalleryResource} REST controller.
 */
@SpringBootTest(classes = MindformeApp.class)
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
public class FamilyGalleryResourceIT {
    private static final String DEFAULT_IMAGE_URL = "AAAAAAAAAA";
    private static final String UPDATED_IMAGE_URL = "BBBBBBBBBB";

    private static final Integer DEFAULT_SORT_ORDER = 1;
    private static final Integer UPDATED_SORT_ORDER = 2;

    private static final Boolean DEFAULT_IS_DEFAULT = false;
    private static final Boolean UPDATED_IS_DEFAULT = true;

    private static final Privacy DEFAULT_PRIVACY = Privacy.PUBLIC;
    private static final Privacy UPDATED_PRIVACY = Privacy.FRIENDS;

    @Autowired
    private FamilyGalleryRepository familyGalleryRepository;

    @Autowired
    private FamilyGalleryMapper familyGalleryMapper;

    @Autowired
    private FamilyGalleryService familyGalleryService;

    /**
     * This repository is mocked in the com.mindforme.app.repository.search test package.
     *
     * @see com.mindforme.app.repository.search.FamilyGallerySearchRepositoryMockConfiguration
     */
    @Autowired
    private FamilyGallerySearchRepository mockFamilyGallerySearchRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restFamilyGalleryMockMvc;

    private FamilyGallery familyGallery;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static FamilyGallery createEntity(EntityManager em) {
        FamilyGallery familyGallery = new FamilyGallery()
            .imageUrl(DEFAULT_IMAGE_URL)
            .sortOrder(DEFAULT_SORT_ORDER)
            .isDefault(DEFAULT_IS_DEFAULT)
            .privacy(DEFAULT_PRIVACY);
        return familyGallery;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static FamilyGallery createUpdatedEntity(EntityManager em) {
        FamilyGallery familyGallery = new FamilyGallery()
            .imageUrl(UPDATED_IMAGE_URL)
            .sortOrder(UPDATED_SORT_ORDER)
            .isDefault(UPDATED_IS_DEFAULT)
            .privacy(UPDATED_PRIVACY);
        return familyGallery;
    }

    @BeforeEach
    public void initTest() {
        familyGallery = createEntity(em);
    }

    @Test
    @Transactional
    public void createFamilyGallery() throws Exception {
        int databaseSizeBeforeCreate = familyGalleryRepository.findAll().size();
        // Create the FamilyGallery
        FamilyGalleryDTO familyGalleryDTO = familyGalleryMapper.toDto(familyGallery);
        restFamilyGalleryMockMvc
            .perform(
                post("/api/family-galleries")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(familyGalleryDTO))
            )
            .andExpect(status().isCreated());

        // Validate the FamilyGallery in the database
        List<FamilyGallery> familyGalleryList = familyGalleryRepository.findAll();
        assertThat(familyGalleryList).hasSize(databaseSizeBeforeCreate + 1);
        FamilyGallery testFamilyGallery = familyGalleryList.get(familyGalleryList.size() - 1);
        assertThat(testFamilyGallery.getImageUrl()).isEqualTo(DEFAULT_IMAGE_URL);
        assertThat(testFamilyGallery.getSortOrder()).isEqualTo(DEFAULT_SORT_ORDER);
        assertThat(testFamilyGallery.isIsDefault()).isEqualTo(DEFAULT_IS_DEFAULT);
        assertThat(testFamilyGallery.getPrivacy()).isEqualTo(DEFAULT_PRIVACY);

        // Validate the FamilyGallery in Elasticsearch
        verify(mockFamilyGallerySearchRepository, times(1)).save(testFamilyGallery);
    }

    @Test
    @Transactional
    public void createFamilyGalleryWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = familyGalleryRepository.findAll().size();

        // Create the FamilyGallery with an existing ID
        familyGallery.setId(1L);
        FamilyGalleryDTO familyGalleryDTO = familyGalleryMapper.toDto(familyGallery);

        // An entity with an existing ID cannot be created, so this API call must fail
        restFamilyGalleryMockMvc
            .perform(
                post("/api/family-galleries")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(familyGalleryDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the FamilyGallery in the database
        List<FamilyGallery> familyGalleryList = familyGalleryRepository.findAll();
        assertThat(familyGalleryList).hasSize(databaseSizeBeforeCreate);

        // Validate the FamilyGallery in Elasticsearch
        verify(mockFamilyGallerySearchRepository, times(0)).save(familyGallery);
    }

    @Test
    @Transactional
    public void getAllFamilyGalleries() throws Exception {
        // Initialize the database
        familyGalleryRepository.saveAndFlush(familyGallery);

        // Get all the familyGalleryList
        restFamilyGalleryMockMvc
            .perform(get("/api/family-galleries?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(familyGallery.getId().intValue())))
            .andExpect(jsonPath("$.[*].imageUrl").value(hasItem(DEFAULT_IMAGE_URL)))
            .andExpect(jsonPath("$.[*].sortOrder").value(hasItem(DEFAULT_SORT_ORDER)))
            .andExpect(jsonPath("$.[*].isDefault").value(hasItem(DEFAULT_IS_DEFAULT.booleanValue())))
            .andExpect(jsonPath("$.[*].privacy").value(hasItem(DEFAULT_PRIVACY.toString())));
    }

    @Test
    @Transactional
    public void getFamilyGallery() throws Exception {
        // Initialize the database
        familyGalleryRepository.saveAndFlush(familyGallery);

        // Get the familyGallery
        restFamilyGalleryMockMvc
            .perform(get("/api/family-galleries/{id}", familyGallery.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(familyGallery.getId().intValue()))
            .andExpect(jsonPath("$.imageUrl").value(DEFAULT_IMAGE_URL))
            .andExpect(jsonPath("$.sortOrder").value(DEFAULT_SORT_ORDER))
            .andExpect(jsonPath("$.isDefault").value(DEFAULT_IS_DEFAULT.booleanValue()))
            .andExpect(jsonPath("$.privacy").value(DEFAULT_PRIVACY.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingFamilyGallery() throws Exception {
        // Get the familyGallery
        restFamilyGalleryMockMvc.perform(get("/api/family-galleries/{id}", Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateFamilyGallery() throws Exception {
        // Initialize the database
        familyGalleryRepository.saveAndFlush(familyGallery);

        int databaseSizeBeforeUpdate = familyGalleryRepository.findAll().size();

        // Update the familyGallery
        FamilyGallery updatedFamilyGallery = familyGalleryRepository.findById(familyGallery.getId()).get();
        // Disconnect from session so that the updates on updatedFamilyGallery are not directly saved in db
        em.detach(updatedFamilyGallery);
        updatedFamilyGallery
            .imageUrl(UPDATED_IMAGE_URL)
            .sortOrder(UPDATED_SORT_ORDER)
            .isDefault(UPDATED_IS_DEFAULT)
            .privacy(UPDATED_PRIVACY);
        FamilyGalleryDTO familyGalleryDTO = familyGalleryMapper.toDto(updatedFamilyGallery);

        restFamilyGalleryMockMvc
            .perform(
                put("/api/family-galleries")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(familyGalleryDTO))
            )
            .andExpect(status().isOk());

        // Validate the FamilyGallery in the database
        List<FamilyGallery> familyGalleryList = familyGalleryRepository.findAll();
        assertThat(familyGalleryList).hasSize(databaseSizeBeforeUpdate);
        FamilyGallery testFamilyGallery = familyGalleryList.get(familyGalleryList.size() - 1);
        assertThat(testFamilyGallery.getImageUrl()).isEqualTo(UPDATED_IMAGE_URL);
        assertThat(testFamilyGallery.getSortOrder()).isEqualTo(UPDATED_SORT_ORDER);
        assertThat(testFamilyGallery.isIsDefault()).isEqualTo(UPDATED_IS_DEFAULT);
        assertThat(testFamilyGallery.getPrivacy()).isEqualTo(UPDATED_PRIVACY);

        // Validate the FamilyGallery in Elasticsearch
        verify(mockFamilyGallerySearchRepository, times(1)).save(testFamilyGallery);
    }

    @Test
    @Transactional
    public void updateNonExistingFamilyGallery() throws Exception {
        int databaseSizeBeforeUpdate = familyGalleryRepository.findAll().size();

        // Create the FamilyGallery
        FamilyGalleryDTO familyGalleryDTO = familyGalleryMapper.toDto(familyGallery);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restFamilyGalleryMockMvc
            .perform(
                put("/api/family-galleries")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(familyGalleryDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the FamilyGallery in the database
        List<FamilyGallery> familyGalleryList = familyGalleryRepository.findAll();
        assertThat(familyGalleryList).hasSize(databaseSizeBeforeUpdate);

        // Validate the FamilyGallery in Elasticsearch
        verify(mockFamilyGallerySearchRepository, times(0)).save(familyGallery);
    }

    @Test
    @Transactional
    public void deleteFamilyGallery() throws Exception {
        // Initialize the database
        familyGalleryRepository.saveAndFlush(familyGallery);

        int databaseSizeBeforeDelete = familyGalleryRepository.findAll().size();

        // Delete the familyGallery
        restFamilyGalleryMockMvc
            .perform(delete("/api/family-galleries/{id}", familyGallery.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<FamilyGallery> familyGalleryList = familyGalleryRepository.findAll();
        assertThat(familyGalleryList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the FamilyGallery in Elasticsearch
        verify(mockFamilyGallerySearchRepository, times(1)).deleteById(familyGallery.getId());
    }

    @Test
    @Transactional
    public void searchFamilyGallery() throws Exception {
        // Configure the mock search repository
        // Initialize the database
        familyGalleryRepository.saveAndFlush(familyGallery);
        when(mockFamilyGallerySearchRepository.search(queryStringQuery("id:" + familyGallery.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(familyGallery), PageRequest.of(0, 1), 1));

        // Search the familyGallery
        restFamilyGalleryMockMvc
            .perform(get("/api/_search/family-galleries?query=id:" + familyGallery.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(familyGallery.getId().intValue())))
            .andExpect(jsonPath("$.[*].imageUrl").value(hasItem(DEFAULT_IMAGE_URL)))
            .andExpect(jsonPath("$.[*].sortOrder").value(hasItem(DEFAULT_SORT_ORDER)))
            .andExpect(jsonPath("$.[*].isDefault").value(hasItem(DEFAULT_IS_DEFAULT.booleanValue())))
            .andExpect(jsonPath("$.[*].privacy").value(hasItem(DEFAULT_PRIVACY.toString())));
    }
}
