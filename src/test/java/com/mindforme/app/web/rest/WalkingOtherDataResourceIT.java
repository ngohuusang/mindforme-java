package com.mindforme.app.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.elasticsearch.index.query.QueryBuilders.queryStringQuery;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.mindforme.app.MindformeApp;
import com.mindforme.app.domain.WalkingOtherData;
import com.mindforme.app.repository.WalkingOtherDataRepository;
import com.mindforme.app.repository.search.WalkingOtherDataSearchRepository;
import com.mindforme.app.service.WalkingOtherDataService;
import com.mindforme.app.service.dto.WalkingOtherDataDTO;
import com.mindforme.app.service.mapper.WalkingOtherDataMapper;
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
 * Integration tests for the {@link WalkingOtherDataResource} REST controller.
 */
@SpringBootTest(classes = MindformeApp.class)
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
public class WalkingOtherDataResourceIT {
    private static final String DEFAULT_CONTENT = "AAAAAAAAAA";
    private static final String UPDATED_CONTENT = "BBBBBBBBBB";

    @Autowired
    private WalkingOtherDataRepository walkingOtherDataRepository;

    @Autowired
    private WalkingOtherDataMapper walkingOtherDataMapper;

    @Autowired
    private WalkingOtherDataService walkingOtherDataService;

    /**
     * This repository is mocked in the com.mindforme.app.repository.search test package.
     *
     * @see com.mindforme.app.repository.search.WalkingOtherDataSearchRepositoryMockConfiguration
     */
    @Autowired
    private WalkingOtherDataSearchRepository mockWalkingOtherDataSearchRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restWalkingOtherDataMockMvc;

    private WalkingOtherData walkingOtherData;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static WalkingOtherData createEntity(EntityManager em) {
        WalkingOtherData walkingOtherData = new WalkingOtherData().content(DEFAULT_CONTENT);
        return walkingOtherData;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static WalkingOtherData createUpdatedEntity(EntityManager em) {
        WalkingOtherData walkingOtherData = new WalkingOtherData().content(UPDATED_CONTENT);
        return walkingOtherData;
    }

    @BeforeEach
    public void initTest() {
        walkingOtherData = createEntity(em);
    }

    @Test
    @Transactional
    public void createWalkingOtherData() throws Exception {
        int databaseSizeBeforeCreate = walkingOtherDataRepository.findAll().size();
        // Create the WalkingOtherData
        WalkingOtherDataDTO walkingOtherDataDTO = walkingOtherDataMapper.toDto(walkingOtherData);
        restWalkingOtherDataMockMvc
            .perform(
                post("/api/walking-other-data")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(walkingOtherDataDTO))
            )
            .andExpect(status().isCreated());

        // Validate the WalkingOtherData in the database
        List<WalkingOtherData> walkingOtherDataList = walkingOtherDataRepository.findAll();
        assertThat(walkingOtherDataList).hasSize(databaseSizeBeforeCreate + 1);
        WalkingOtherData testWalkingOtherData = walkingOtherDataList.get(walkingOtherDataList.size() - 1);
        assertThat(testWalkingOtherData.getContent()).isEqualTo(DEFAULT_CONTENT);

        // Validate the WalkingOtherData in Elasticsearch
        verify(mockWalkingOtherDataSearchRepository, times(1)).save(testWalkingOtherData);
    }

    @Test
    @Transactional
    public void createWalkingOtherDataWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = walkingOtherDataRepository.findAll().size();

        // Create the WalkingOtherData with an existing ID
        walkingOtherData.setId(1L);
        WalkingOtherDataDTO walkingOtherDataDTO = walkingOtherDataMapper.toDto(walkingOtherData);

        // An entity with an existing ID cannot be created, so this API call must fail
        restWalkingOtherDataMockMvc
            .perform(
                post("/api/walking-other-data")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(walkingOtherDataDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the WalkingOtherData in the database
        List<WalkingOtherData> walkingOtherDataList = walkingOtherDataRepository.findAll();
        assertThat(walkingOtherDataList).hasSize(databaseSizeBeforeCreate);

        // Validate the WalkingOtherData in Elasticsearch
        verify(mockWalkingOtherDataSearchRepository, times(0)).save(walkingOtherData);
    }

    @Test
    @Transactional
    public void getAllWalkingOtherData() throws Exception {
        // Initialize the database
        walkingOtherDataRepository.saveAndFlush(walkingOtherData);

        // Get all the walkingOtherDataList
        restWalkingOtherDataMockMvc
            .perform(get("/api/walking-other-data?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(walkingOtherData.getId().intValue())))
            .andExpect(jsonPath("$.[*].content").value(hasItem(DEFAULT_CONTENT)));
    }

    @Test
    @Transactional
    public void getWalkingOtherData() throws Exception {
        // Initialize the database
        walkingOtherDataRepository.saveAndFlush(walkingOtherData);

        // Get the walkingOtherData
        restWalkingOtherDataMockMvc
            .perform(get("/api/walking-other-data/{id}", walkingOtherData.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(walkingOtherData.getId().intValue()))
            .andExpect(jsonPath("$.content").value(DEFAULT_CONTENT));
    }

    @Test
    @Transactional
    public void getNonExistingWalkingOtherData() throws Exception {
        // Get the walkingOtherData
        restWalkingOtherDataMockMvc.perform(get("/api/walking-other-data/{id}", Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateWalkingOtherData() throws Exception {
        // Initialize the database
        walkingOtherDataRepository.saveAndFlush(walkingOtherData);

        int databaseSizeBeforeUpdate = walkingOtherDataRepository.findAll().size();

        // Update the walkingOtherData
        WalkingOtherData updatedWalkingOtherData = walkingOtherDataRepository.findById(walkingOtherData.getId()).get();
        // Disconnect from session so that the updates on updatedWalkingOtherData are not directly saved in db
        em.detach(updatedWalkingOtherData);
        updatedWalkingOtherData.content(UPDATED_CONTENT);
        WalkingOtherDataDTO walkingOtherDataDTO = walkingOtherDataMapper.toDto(updatedWalkingOtherData);

        restWalkingOtherDataMockMvc
            .perform(
                put("/api/walking-other-data")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(walkingOtherDataDTO))
            )
            .andExpect(status().isOk());

        // Validate the WalkingOtherData in the database
        List<WalkingOtherData> walkingOtherDataList = walkingOtherDataRepository.findAll();
        assertThat(walkingOtherDataList).hasSize(databaseSizeBeforeUpdate);
        WalkingOtherData testWalkingOtherData = walkingOtherDataList.get(walkingOtherDataList.size() - 1);
        assertThat(testWalkingOtherData.getContent()).isEqualTo(UPDATED_CONTENT);

        // Validate the WalkingOtherData in Elasticsearch
        verify(mockWalkingOtherDataSearchRepository, times(1)).save(testWalkingOtherData);
    }

    @Test
    @Transactional
    public void updateNonExistingWalkingOtherData() throws Exception {
        int databaseSizeBeforeUpdate = walkingOtherDataRepository.findAll().size();

        // Create the WalkingOtherData
        WalkingOtherDataDTO walkingOtherDataDTO = walkingOtherDataMapper.toDto(walkingOtherData);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restWalkingOtherDataMockMvc
            .perform(
                put("/api/walking-other-data")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(walkingOtherDataDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the WalkingOtherData in the database
        List<WalkingOtherData> walkingOtherDataList = walkingOtherDataRepository.findAll();
        assertThat(walkingOtherDataList).hasSize(databaseSizeBeforeUpdate);

        // Validate the WalkingOtherData in Elasticsearch
        verify(mockWalkingOtherDataSearchRepository, times(0)).save(walkingOtherData);
    }

    @Test
    @Transactional
    public void deleteWalkingOtherData() throws Exception {
        // Initialize the database
        walkingOtherDataRepository.saveAndFlush(walkingOtherData);

        int databaseSizeBeforeDelete = walkingOtherDataRepository.findAll().size();

        // Delete the walkingOtherData
        restWalkingOtherDataMockMvc
            .perform(delete("/api/walking-other-data/{id}", walkingOtherData.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<WalkingOtherData> walkingOtherDataList = walkingOtherDataRepository.findAll();
        assertThat(walkingOtherDataList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the WalkingOtherData in Elasticsearch
        verify(mockWalkingOtherDataSearchRepository, times(1)).deleteById(walkingOtherData.getId());
    }

    @Test
    @Transactional
    public void searchWalkingOtherData() throws Exception {
        // Configure the mock search repository
        // Initialize the database
        walkingOtherDataRepository.saveAndFlush(walkingOtherData);
        when(mockWalkingOtherDataSearchRepository.search(queryStringQuery("id:" + walkingOtherData.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(walkingOtherData), PageRequest.of(0, 1), 1));

        // Search the walkingOtherData
        restWalkingOtherDataMockMvc
            .perform(get("/api/_search/walking-other-data?query=id:" + walkingOtherData.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(walkingOtherData.getId().intValue())))
            .andExpect(jsonPath("$.[*].content").value(hasItem(DEFAULT_CONTENT)));
    }
}
