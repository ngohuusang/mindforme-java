package com.mindforme.app.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.elasticsearch.index.query.QueryBuilders.queryStringQuery;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.mindforme.app.MindformeApp;
import com.mindforme.app.domain.SupportedRelationData;
import com.mindforme.app.repository.SupportedRelationDataRepository;
import com.mindforme.app.repository.search.SupportedRelationDataSearchRepository;
import com.mindforme.app.service.SupportedRelationDataService;
import com.mindforme.app.service.dto.SupportedRelationDataDTO;
import com.mindforme.app.service.mapper.SupportedRelationDataMapper;
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
 * Integration tests for the {@link SupportedRelationDataResource} REST controller.
 */
@SpringBootTest(classes = MindformeApp.class)
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
public class SupportedRelationDataResourceIT {
    private static final String DEFAULT_CONTENT = "AAAAAAAAAA";
    private static final String UPDATED_CONTENT = "BBBBBBBBBB";

    @Autowired
    private SupportedRelationDataRepository supportedRelationDataRepository;

    @Autowired
    private SupportedRelationDataMapper supportedRelationDataMapper;

    @Autowired
    private SupportedRelationDataService supportedRelationDataService;

    /**
     * This repository is mocked in the com.mindforme.app.repository.search test package.
     *
     * @see com.mindforme.app.repository.search.SupportedRelationDataSearchRepositoryMockConfiguration
     */
    @Autowired
    private SupportedRelationDataSearchRepository mockSupportedRelationDataSearchRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restSupportedRelationDataMockMvc;

    private SupportedRelationData supportedRelationData;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static SupportedRelationData createEntity(EntityManager em) {
        SupportedRelationData supportedRelationData = new SupportedRelationData().content(DEFAULT_CONTENT);
        return supportedRelationData;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static SupportedRelationData createUpdatedEntity(EntityManager em) {
        SupportedRelationData supportedRelationData = new SupportedRelationData().content(UPDATED_CONTENT);
        return supportedRelationData;
    }

    @BeforeEach
    public void initTest() {
        supportedRelationData = createEntity(em);
    }

    @Test
    @Transactional
    public void createSupportedRelationData() throws Exception {
        int databaseSizeBeforeCreate = supportedRelationDataRepository.findAll().size();
        // Create the SupportedRelationData
        SupportedRelationDataDTO supportedRelationDataDTO = supportedRelationDataMapper.toDto(supportedRelationData);
        restSupportedRelationDataMockMvc
            .perform(
                post("/api/supported-relation-data")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(supportedRelationDataDTO))
            )
            .andExpect(status().isCreated());

        // Validate the SupportedRelationData in the database
        List<SupportedRelationData> supportedRelationDataList = supportedRelationDataRepository.findAll();
        assertThat(supportedRelationDataList).hasSize(databaseSizeBeforeCreate + 1);
        SupportedRelationData testSupportedRelationData = supportedRelationDataList.get(supportedRelationDataList.size() - 1);
        assertThat(testSupportedRelationData.getContent()).isEqualTo(DEFAULT_CONTENT);

        // Validate the SupportedRelationData in Elasticsearch
        verify(mockSupportedRelationDataSearchRepository, times(1)).save(testSupportedRelationData);
    }

    @Test
    @Transactional
    public void createSupportedRelationDataWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = supportedRelationDataRepository.findAll().size();

        // Create the SupportedRelationData with an existing ID
        supportedRelationData.setId(1L);
        SupportedRelationDataDTO supportedRelationDataDTO = supportedRelationDataMapper.toDto(supportedRelationData);

        // An entity with an existing ID cannot be created, so this API call must fail
        restSupportedRelationDataMockMvc
            .perform(
                post("/api/supported-relation-data")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(supportedRelationDataDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the SupportedRelationData in the database
        List<SupportedRelationData> supportedRelationDataList = supportedRelationDataRepository.findAll();
        assertThat(supportedRelationDataList).hasSize(databaseSizeBeforeCreate);

        // Validate the SupportedRelationData in Elasticsearch
        verify(mockSupportedRelationDataSearchRepository, times(0)).save(supportedRelationData);
    }

    @Test
    @Transactional
    public void getAllSupportedRelationData() throws Exception {
        // Initialize the database
        supportedRelationDataRepository.saveAndFlush(supportedRelationData);

        // Get all the supportedRelationDataList
        restSupportedRelationDataMockMvc
            .perform(get("/api/supported-relation-data?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(supportedRelationData.getId().intValue())))
            .andExpect(jsonPath("$.[*].content").value(hasItem(DEFAULT_CONTENT)));
    }

    @Test
    @Transactional
    public void getSupportedRelationData() throws Exception {
        // Initialize the database
        supportedRelationDataRepository.saveAndFlush(supportedRelationData);

        // Get the supportedRelationData
        restSupportedRelationDataMockMvc
            .perform(get("/api/supported-relation-data/{id}", supportedRelationData.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(supportedRelationData.getId().intValue()))
            .andExpect(jsonPath("$.content").value(DEFAULT_CONTENT));
    }

    @Test
    @Transactional
    public void getNonExistingSupportedRelationData() throws Exception {
        // Get the supportedRelationData
        restSupportedRelationDataMockMvc.perform(get("/api/supported-relation-data/{id}", Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateSupportedRelationData() throws Exception {
        // Initialize the database
        supportedRelationDataRepository.saveAndFlush(supportedRelationData);

        int databaseSizeBeforeUpdate = supportedRelationDataRepository.findAll().size();

        // Update the supportedRelationData
        SupportedRelationData updatedSupportedRelationData = supportedRelationDataRepository.findById(supportedRelationData.getId()).get();
        // Disconnect from session so that the updates on updatedSupportedRelationData are not directly saved in db
        em.detach(updatedSupportedRelationData);
        updatedSupportedRelationData.content(UPDATED_CONTENT);
        SupportedRelationDataDTO supportedRelationDataDTO = supportedRelationDataMapper.toDto(updatedSupportedRelationData);

        restSupportedRelationDataMockMvc
            .perform(
                put("/api/supported-relation-data")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(supportedRelationDataDTO))
            )
            .andExpect(status().isOk());

        // Validate the SupportedRelationData in the database
        List<SupportedRelationData> supportedRelationDataList = supportedRelationDataRepository.findAll();
        assertThat(supportedRelationDataList).hasSize(databaseSizeBeforeUpdate);
        SupportedRelationData testSupportedRelationData = supportedRelationDataList.get(supportedRelationDataList.size() - 1);
        assertThat(testSupportedRelationData.getContent()).isEqualTo(UPDATED_CONTENT);

        // Validate the SupportedRelationData in Elasticsearch
        verify(mockSupportedRelationDataSearchRepository, times(1)).save(testSupportedRelationData);
    }

    @Test
    @Transactional
    public void updateNonExistingSupportedRelationData() throws Exception {
        int databaseSizeBeforeUpdate = supportedRelationDataRepository.findAll().size();

        // Create the SupportedRelationData
        SupportedRelationDataDTO supportedRelationDataDTO = supportedRelationDataMapper.toDto(supportedRelationData);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSupportedRelationDataMockMvc
            .perform(
                put("/api/supported-relation-data")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(supportedRelationDataDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the SupportedRelationData in the database
        List<SupportedRelationData> supportedRelationDataList = supportedRelationDataRepository.findAll();
        assertThat(supportedRelationDataList).hasSize(databaseSizeBeforeUpdate);

        // Validate the SupportedRelationData in Elasticsearch
        verify(mockSupportedRelationDataSearchRepository, times(0)).save(supportedRelationData);
    }

    @Test
    @Transactional
    public void deleteSupportedRelationData() throws Exception {
        // Initialize the database
        supportedRelationDataRepository.saveAndFlush(supportedRelationData);

        int databaseSizeBeforeDelete = supportedRelationDataRepository.findAll().size();

        // Delete the supportedRelationData
        restSupportedRelationDataMockMvc
            .perform(delete("/api/supported-relation-data/{id}", supportedRelationData.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<SupportedRelationData> supportedRelationDataList = supportedRelationDataRepository.findAll();
        assertThat(supportedRelationDataList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the SupportedRelationData in Elasticsearch
        verify(mockSupportedRelationDataSearchRepository, times(1)).deleteById(supportedRelationData.getId());
    }

    @Test
    @Transactional
    public void searchSupportedRelationData() throws Exception {
        // Configure the mock search repository
        // Initialize the database
        supportedRelationDataRepository.saveAndFlush(supportedRelationData);
        when(
            mockSupportedRelationDataSearchRepository.search(queryStringQuery("id:" + supportedRelationData.getId()), PageRequest.of(0, 20))
        )
            .thenReturn(new PageImpl<>(Collections.singletonList(supportedRelationData), PageRequest.of(0, 1), 1));

        // Search the supportedRelationData
        restSupportedRelationDataMockMvc
            .perform(get("/api/_search/supported-relation-data?query=id:" + supportedRelationData.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(supportedRelationData.getId().intValue())))
            .andExpect(jsonPath("$.[*].content").value(hasItem(DEFAULT_CONTENT)));
    }
}
