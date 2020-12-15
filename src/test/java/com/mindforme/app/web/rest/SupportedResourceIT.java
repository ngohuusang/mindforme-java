package com.mindforme.app.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.elasticsearch.index.query.QueryBuilders.queryStringQuery;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.mindforme.app.MindformeApp;
import com.mindforme.app.domain.Supported;
import com.mindforme.app.domain.enumeration.Status;
import com.mindforme.app.repository.SupportedRepository;
import com.mindforme.app.repository.search.SupportedSearchRepository;
import com.mindforme.app.service.SupportedService;
import com.mindforme.app.service.dto.SupportedDTO;
import com.mindforme.app.service.mapper.SupportedMapper;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
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
 * Integration tests for the {@link SupportedResource} REST controller.
 */
@SpringBootTest(classes = MindformeApp.class)
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
public class SupportedResourceIT {
    private static final String DEFAULT_FIRST_NAME = "AAAAAAAAAA";
    private static final String UPDATED_FIRST_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_LAST_NAME = "AAAAAAAAAA";
    private static final String UPDATED_LAST_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_IMAGE_URL = "AAAAAAAAAA";
    private static final String UPDATED_IMAGE_URL = "BBBBBBBBBB";

    private static final Instant DEFAULT_BIRTHDAY = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_BIRTHDAY = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Status DEFAULT_STATUS = Status.Active;
    private static final Status UPDATED_STATUS = Status.Inactive;

    @Autowired
    private SupportedRepository supportedRepository;

    @Autowired
    private SupportedMapper supportedMapper;

    @Autowired
    private SupportedService supportedService;

    /**
     * This repository is mocked in the com.mindforme.app.repository.search test package.
     *
     * @see com.mindforme.app.repository.search.SupportedSearchRepositoryMockConfiguration
     */
    @Autowired
    private SupportedSearchRepository mockSupportedSearchRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restSupportedMockMvc;

    private Supported supported;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Supported createEntity(EntityManager em) {
        Supported supported = new Supported()
            .firstName(DEFAULT_FIRST_NAME)
            .lastName(DEFAULT_LAST_NAME)
            .imageUrl(DEFAULT_IMAGE_URL)
            .birthday(DEFAULT_BIRTHDAY)
            .status(DEFAULT_STATUS);
        return supported;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Supported createUpdatedEntity(EntityManager em) {
        Supported supported = new Supported()
            .firstName(UPDATED_FIRST_NAME)
            .lastName(UPDATED_LAST_NAME)
            .imageUrl(UPDATED_IMAGE_URL)
            .birthday(UPDATED_BIRTHDAY)
            .status(UPDATED_STATUS);
        return supported;
    }

    @BeforeEach
    public void initTest() {
        supported = createEntity(em);
    }

    @Test
    @Transactional
    public void createSupported() throws Exception {
        int databaseSizeBeforeCreate = supportedRepository.findAll().size();
        // Create the Supported
        SupportedDTO supportedDTO = supportedMapper.toDto(supported);
        restSupportedMockMvc
            .perform(
                post("/api/supporteds").contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(supportedDTO))
            )
            .andExpect(status().isCreated());

        // Validate the Supported in the database
        List<Supported> supportedList = supportedRepository.findAll();
        assertThat(supportedList).hasSize(databaseSizeBeforeCreate + 1);
        Supported testSupported = supportedList.get(supportedList.size() - 1);
        assertThat(testSupported.getFirstName()).isEqualTo(DEFAULT_FIRST_NAME);
        assertThat(testSupported.getLastName()).isEqualTo(DEFAULT_LAST_NAME);
        assertThat(testSupported.getImageUrl()).isEqualTo(DEFAULT_IMAGE_URL);
        assertThat(testSupported.getBirthday()).isEqualTo(DEFAULT_BIRTHDAY);
        assertThat(testSupported.getStatus()).isEqualTo(DEFAULT_STATUS);

        // Validate the Supported in Elasticsearch
        verify(mockSupportedSearchRepository, times(1)).save(testSupported);
    }

    @Test
    @Transactional
    public void createSupportedWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = supportedRepository.findAll().size();

        // Create the Supported with an existing ID
        supported.setId(1L);
        SupportedDTO supportedDTO = supportedMapper.toDto(supported);

        // An entity with an existing ID cannot be created, so this API call must fail
        restSupportedMockMvc
            .perform(
                post("/api/supporteds").contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(supportedDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Supported in the database
        List<Supported> supportedList = supportedRepository.findAll();
        assertThat(supportedList).hasSize(databaseSizeBeforeCreate);

        // Validate the Supported in Elasticsearch
        verify(mockSupportedSearchRepository, times(0)).save(supported);
    }

    @Test
    @Transactional
    public void getAllSupporteds() throws Exception {
        // Initialize the database
        supportedRepository.saveAndFlush(supported);

        // Get all the supportedList
        restSupportedMockMvc
            .perform(get("/api/supporteds?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(supported.getId().intValue())))
            .andExpect(jsonPath("$.[*].firstName").value(hasItem(DEFAULT_FIRST_NAME)))
            .andExpect(jsonPath("$.[*].lastName").value(hasItem(DEFAULT_LAST_NAME)))
            .andExpect(jsonPath("$.[*].imageUrl").value(hasItem(DEFAULT_IMAGE_URL)))
            .andExpect(jsonPath("$.[*].birthday").value(hasItem(DEFAULT_BIRTHDAY.toString())))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.toString())));
    }

    @Test
    @Transactional
    public void getSupported() throws Exception {
        // Initialize the database
        supportedRepository.saveAndFlush(supported);

        // Get the supported
        restSupportedMockMvc
            .perform(get("/api/supporteds/{id}", supported.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(supported.getId().intValue()))
            .andExpect(jsonPath("$.firstName").value(DEFAULT_FIRST_NAME))
            .andExpect(jsonPath("$.lastName").value(DEFAULT_LAST_NAME))
            .andExpect(jsonPath("$.imageUrl").value(DEFAULT_IMAGE_URL))
            .andExpect(jsonPath("$.birthday").value(DEFAULT_BIRTHDAY.toString()))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingSupported() throws Exception {
        // Get the supported
        restSupportedMockMvc.perform(get("/api/supporteds/{id}", Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateSupported() throws Exception {
        // Initialize the database
        supportedRepository.saveAndFlush(supported);

        int databaseSizeBeforeUpdate = supportedRepository.findAll().size();

        // Update the supported
        Supported updatedSupported = supportedRepository.findById(supported.getId()).get();
        // Disconnect from session so that the updates on updatedSupported are not directly saved in db
        em.detach(updatedSupported);
        updatedSupported
            .firstName(UPDATED_FIRST_NAME)
            .lastName(UPDATED_LAST_NAME)
            .imageUrl(UPDATED_IMAGE_URL)
            .birthday(UPDATED_BIRTHDAY)
            .status(UPDATED_STATUS);
        SupportedDTO supportedDTO = supportedMapper.toDto(updatedSupported);

        restSupportedMockMvc
            .perform(
                put("/api/supporteds").contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(supportedDTO))
            )
            .andExpect(status().isOk());

        // Validate the Supported in the database
        List<Supported> supportedList = supportedRepository.findAll();
        assertThat(supportedList).hasSize(databaseSizeBeforeUpdate);
        Supported testSupported = supportedList.get(supportedList.size() - 1);
        assertThat(testSupported.getFirstName()).isEqualTo(UPDATED_FIRST_NAME);
        assertThat(testSupported.getLastName()).isEqualTo(UPDATED_LAST_NAME);
        assertThat(testSupported.getImageUrl()).isEqualTo(UPDATED_IMAGE_URL);
        assertThat(testSupported.getBirthday()).isEqualTo(UPDATED_BIRTHDAY);
        assertThat(testSupported.getStatus()).isEqualTo(UPDATED_STATUS);

        // Validate the Supported in Elasticsearch
        verify(mockSupportedSearchRepository, times(1)).save(testSupported);
    }

    @Test
    @Transactional
    public void updateNonExistingSupported() throws Exception {
        int databaseSizeBeforeUpdate = supportedRepository.findAll().size();

        // Create the Supported
        SupportedDTO supportedDTO = supportedMapper.toDto(supported);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSupportedMockMvc
            .perform(
                put("/api/supporteds").contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(supportedDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Supported in the database
        List<Supported> supportedList = supportedRepository.findAll();
        assertThat(supportedList).hasSize(databaseSizeBeforeUpdate);

        // Validate the Supported in Elasticsearch
        verify(mockSupportedSearchRepository, times(0)).save(supported);
    }

    @Test
    @Transactional
    public void deleteSupported() throws Exception {
        // Initialize the database
        supportedRepository.saveAndFlush(supported);

        int databaseSizeBeforeDelete = supportedRepository.findAll().size();

        // Delete the supported
        restSupportedMockMvc
            .perform(delete("/api/supporteds/{id}", supported.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Supported> supportedList = supportedRepository.findAll();
        assertThat(supportedList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the Supported in Elasticsearch
        verify(mockSupportedSearchRepository, times(1)).deleteById(supported.getId());
    }

    @Test
    @Transactional
    public void searchSupported() throws Exception {
        // Configure the mock search repository
        // Initialize the database
        supportedRepository.saveAndFlush(supported);
        when(mockSupportedSearchRepository.search(queryStringQuery("id:" + supported.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(supported), PageRequest.of(0, 1), 1));

        // Search the supported
        restSupportedMockMvc
            .perform(get("/api/_search/supporteds?query=id:" + supported.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(supported.getId().intValue())))
            .andExpect(jsonPath("$.[*].firstName").value(hasItem(DEFAULT_FIRST_NAME)))
            .andExpect(jsonPath("$.[*].lastName").value(hasItem(DEFAULT_LAST_NAME)))
            .andExpect(jsonPath("$.[*].imageUrl").value(hasItem(DEFAULT_IMAGE_URL)))
            .andExpect(jsonPath("$.[*].birthday").value(hasItem(DEFAULT_BIRTHDAY.toString())))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.toString())));
    }
}
