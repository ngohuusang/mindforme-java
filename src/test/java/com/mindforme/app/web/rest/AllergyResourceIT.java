package com.mindforme.app.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.elasticsearch.index.query.QueryBuilders.queryStringQuery;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.mindforme.app.MindformeApp;
import com.mindforme.app.domain.Allergy;
import com.mindforme.app.domain.enumeration.Status;
import com.mindforme.app.repository.AllergyRepository;
import com.mindforme.app.repository.search.AllergySearchRepository;
import com.mindforme.app.service.AllergyService;
import com.mindforme.app.service.dto.AllergyDTO;
import com.mindforme.app.service.mapper.AllergyMapper;
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
 * Integration tests for the {@link AllergyResource} REST controller.
 */
@SpringBootTest(classes = MindformeApp.class)
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
public class AllergyResourceIT {
    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final Status DEFAULT_STATUS = Status.Active;
    private static final Status UPDATED_STATUS = Status.Inactive;

    @Autowired
    private AllergyRepository allergyRepository;

    @Autowired
    private AllergyMapper allergyMapper;

    @Autowired
    private AllergyService allergyService;

    /**
     * This repository is mocked in the com.mindforme.app.repository.search test package.
     *
     * @see com.mindforme.app.repository.search.AllergySearchRepositoryMockConfiguration
     */
    @Autowired
    private AllergySearchRepository mockAllergySearchRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restAllergyMockMvc;

    private Allergy allergy;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Allergy createEntity(EntityManager em) {
        Allergy allergy = new Allergy().name(DEFAULT_NAME).status(DEFAULT_STATUS);
        return allergy;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Allergy createUpdatedEntity(EntityManager em) {
        Allergy allergy = new Allergy().name(UPDATED_NAME).status(UPDATED_STATUS);
        return allergy;
    }

    @BeforeEach
    public void initTest() {
        allergy = createEntity(em);
    }

    @Test
    @Transactional
    public void createAllergy() throws Exception {
        int databaseSizeBeforeCreate = allergyRepository.findAll().size();
        // Create the Allergy
        AllergyDTO allergyDTO = allergyMapper.toDto(allergy);
        restAllergyMockMvc
            .perform(post("/api/allergies").contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(allergyDTO)))
            .andExpect(status().isCreated());

        // Validate the Allergy in the database
        List<Allergy> allergyList = allergyRepository.findAll();
        assertThat(allergyList).hasSize(databaseSizeBeforeCreate + 1);
        Allergy testAllergy = allergyList.get(allergyList.size() - 1);
        assertThat(testAllergy.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testAllergy.getStatus()).isEqualTo(DEFAULT_STATUS);

        // Validate the Allergy in Elasticsearch
        verify(mockAllergySearchRepository, times(1)).save(testAllergy);
    }

    @Test
    @Transactional
    public void createAllergyWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = allergyRepository.findAll().size();

        // Create the Allergy with an existing ID
        allergy.setId(1L);
        AllergyDTO allergyDTO = allergyMapper.toDto(allergy);

        // An entity with an existing ID cannot be created, so this API call must fail
        restAllergyMockMvc
            .perform(post("/api/allergies").contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(allergyDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Allergy in the database
        List<Allergy> allergyList = allergyRepository.findAll();
        assertThat(allergyList).hasSize(databaseSizeBeforeCreate);

        // Validate the Allergy in Elasticsearch
        verify(mockAllergySearchRepository, times(0)).save(allergy);
    }

    @Test
    @Transactional
    public void getAllAllergies() throws Exception {
        // Initialize the database
        allergyRepository.saveAndFlush(allergy);

        // Get all the allergyList
        restAllergyMockMvc
            .perform(get("/api/allergies?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(allergy.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.toString())));
    }

    @Test
    @Transactional
    public void getAllergy() throws Exception {
        // Initialize the database
        allergyRepository.saveAndFlush(allergy);

        // Get the allergy
        restAllergyMockMvc
            .perform(get("/api/allergies/{id}", allergy.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(allergy.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingAllergy() throws Exception {
        // Get the allergy
        restAllergyMockMvc.perform(get("/api/allergies/{id}", Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateAllergy() throws Exception {
        // Initialize the database
        allergyRepository.saveAndFlush(allergy);

        int databaseSizeBeforeUpdate = allergyRepository.findAll().size();

        // Update the allergy
        Allergy updatedAllergy = allergyRepository.findById(allergy.getId()).get();
        // Disconnect from session so that the updates on updatedAllergy are not directly saved in db
        em.detach(updatedAllergy);
        updatedAllergy.name(UPDATED_NAME).status(UPDATED_STATUS);
        AllergyDTO allergyDTO = allergyMapper.toDto(updatedAllergy);

        restAllergyMockMvc
            .perform(put("/api/allergies").contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(allergyDTO)))
            .andExpect(status().isOk());

        // Validate the Allergy in the database
        List<Allergy> allergyList = allergyRepository.findAll();
        assertThat(allergyList).hasSize(databaseSizeBeforeUpdate);
        Allergy testAllergy = allergyList.get(allergyList.size() - 1);
        assertThat(testAllergy.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testAllergy.getStatus()).isEqualTo(UPDATED_STATUS);

        // Validate the Allergy in Elasticsearch
        verify(mockAllergySearchRepository, times(1)).save(testAllergy);
    }

    @Test
    @Transactional
    public void updateNonExistingAllergy() throws Exception {
        int databaseSizeBeforeUpdate = allergyRepository.findAll().size();

        // Create the Allergy
        AllergyDTO allergyDTO = allergyMapper.toDto(allergy);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAllergyMockMvc
            .perform(put("/api/allergies").contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(allergyDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Allergy in the database
        List<Allergy> allergyList = allergyRepository.findAll();
        assertThat(allergyList).hasSize(databaseSizeBeforeUpdate);

        // Validate the Allergy in Elasticsearch
        verify(mockAllergySearchRepository, times(0)).save(allergy);
    }

    @Test
    @Transactional
    public void deleteAllergy() throws Exception {
        // Initialize the database
        allergyRepository.saveAndFlush(allergy);

        int databaseSizeBeforeDelete = allergyRepository.findAll().size();

        // Delete the allergy
        restAllergyMockMvc
            .perform(delete("/api/allergies/{id}", allergy.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Allergy> allergyList = allergyRepository.findAll();
        assertThat(allergyList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the Allergy in Elasticsearch
        verify(mockAllergySearchRepository, times(1)).deleteById(allergy.getId());
    }

    @Test
    @Transactional
    public void searchAllergy() throws Exception {
        // Configure the mock search repository
        // Initialize the database
        allergyRepository.saveAndFlush(allergy);
        when(mockAllergySearchRepository.search(queryStringQuery("id:" + allergy.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(allergy), PageRequest.of(0, 1), 1));

        // Search the allergy
        restAllergyMockMvc
            .perform(get("/api/_search/allergies?query=id:" + allergy.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(allergy.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.toString())));
    }
}
