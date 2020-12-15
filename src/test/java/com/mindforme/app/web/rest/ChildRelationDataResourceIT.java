package com.mindforme.app.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.elasticsearch.index.query.QueryBuilders.queryStringQuery;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.mindforme.app.MindformeApp;
import com.mindforme.app.domain.ChildRelationData;
import com.mindforme.app.repository.ChildRelationDataRepository;
import com.mindforme.app.repository.search.ChildRelationDataSearchRepository;
import com.mindforme.app.service.ChildRelationDataService;
import com.mindforme.app.service.dto.ChildRelationDataDTO;
import com.mindforme.app.service.mapper.ChildRelationDataMapper;
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
 * Integration tests for the {@link ChildRelationDataResource} REST controller.
 */
@SpringBootTest(classes = MindformeApp.class)
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
public class ChildRelationDataResourceIT {
    private static final String DEFAULT_CONTENT = "AAAAAAAAAA";
    private static final String UPDATED_CONTENT = "BBBBBBBBBB";

    @Autowired
    private ChildRelationDataRepository childRelationDataRepository;

    @Autowired
    private ChildRelationDataMapper childRelationDataMapper;

    @Autowired
    private ChildRelationDataService childRelationDataService;

    /**
     * This repository is mocked in the com.mindforme.app.repository.search test package.
     *
     * @see com.mindforme.app.repository.search.ChildRelationDataSearchRepositoryMockConfiguration
     */
    @Autowired
    private ChildRelationDataSearchRepository mockChildRelationDataSearchRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restChildRelationDataMockMvc;

    private ChildRelationData childRelationData;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ChildRelationData createEntity(EntityManager em) {
        ChildRelationData childRelationData = new ChildRelationData().content(DEFAULT_CONTENT);
        return childRelationData;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ChildRelationData createUpdatedEntity(EntityManager em) {
        ChildRelationData childRelationData = new ChildRelationData().content(UPDATED_CONTENT);
        return childRelationData;
    }

    @BeforeEach
    public void initTest() {
        childRelationData = createEntity(em);
    }

    @Test
    @Transactional
    public void createChildRelationData() throws Exception {
        int databaseSizeBeforeCreate = childRelationDataRepository.findAll().size();
        // Create the ChildRelationData
        ChildRelationDataDTO childRelationDataDTO = childRelationDataMapper.toDto(childRelationData);
        restChildRelationDataMockMvc
            .perform(
                post("/api/child-relation-data")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(childRelationDataDTO))
            )
            .andExpect(status().isCreated());

        // Validate the ChildRelationData in the database
        List<ChildRelationData> childRelationDataList = childRelationDataRepository.findAll();
        assertThat(childRelationDataList).hasSize(databaseSizeBeforeCreate + 1);
        ChildRelationData testChildRelationData = childRelationDataList.get(childRelationDataList.size() - 1);
        assertThat(testChildRelationData.getContent()).isEqualTo(DEFAULT_CONTENT);

        // Validate the ChildRelationData in Elasticsearch
        verify(mockChildRelationDataSearchRepository, times(1)).save(testChildRelationData);
    }

    @Test
    @Transactional
    public void createChildRelationDataWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = childRelationDataRepository.findAll().size();

        // Create the ChildRelationData with an existing ID
        childRelationData.setId(1L);
        ChildRelationDataDTO childRelationDataDTO = childRelationDataMapper.toDto(childRelationData);

        // An entity with an existing ID cannot be created, so this API call must fail
        restChildRelationDataMockMvc
            .perform(
                post("/api/child-relation-data")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(childRelationDataDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ChildRelationData in the database
        List<ChildRelationData> childRelationDataList = childRelationDataRepository.findAll();
        assertThat(childRelationDataList).hasSize(databaseSizeBeforeCreate);

        // Validate the ChildRelationData in Elasticsearch
        verify(mockChildRelationDataSearchRepository, times(0)).save(childRelationData);
    }

    @Test
    @Transactional
    public void getAllChildRelationData() throws Exception {
        // Initialize the database
        childRelationDataRepository.saveAndFlush(childRelationData);

        // Get all the childRelationDataList
        restChildRelationDataMockMvc
            .perform(get("/api/child-relation-data?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(childRelationData.getId().intValue())))
            .andExpect(jsonPath("$.[*].content").value(hasItem(DEFAULT_CONTENT)));
    }

    @Test
    @Transactional
    public void getChildRelationData() throws Exception {
        // Initialize the database
        childRelationDataRepository.saveAndFlush(childRelationData);

        // Get the childRelationData
        restChildRelationDataMockMvc
            .perform(get("/api/child-relation-data/{id}", childRelationData.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(childRelationData.getId().intValue()))
            .andExpect(jsonPath("$.content").value(DEFAULT_CONTENT));
    }

    @Test
    @Transactional
    public void getNonExistingChildRelationData() throws Exception {
        // Get the childRelationData
        restChildRelationDataMockMvc.perform(get("/api/child-relation-data/{id}", Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateChildRelationData() throws Exception {
        // Initialize the database
        childRelationDataRepository.saveAndFlush(childRelationData);

        int databaseSizeBeforeUpdate = childRelationDataRepository.findAll().size();

        // Update the childRelationData
        ChildRelationData updatedChildRelationData = childRelationDataRepository.findById(childRelationData.getId()).get();
        // Disconnect from session so that the updates on updatedChildRelationData are not directly saved in db
        em.detach(updatedChildRelationData);
        updatedChildRelationData.content(UPDATED_CONTENT);
        ChildRelationDataDTO childRelationDataDTO = childRelationDataMapper.toDto(updatedChildRelationData);

        restChildRelationDataMockMvc
            .perform(
                put("/api/child-relation-data")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(childRelationDataDTO))
            )
            .andExpect(status().isOk());

        // Validate the ChildRelationData in the database
        List<ChildRelationData> childRelationDataList = childRelationDataRepository.findAll();
        assertThat(childRelationDataList).hasSize(databaseSizeBeforeUpdate);
        ChildRelationData testChildRelationData = childRelationDataList.get(childRelationDataList.size() - 1);
        assertThat(testChildRelationData.getContent()).isEqualTo(UPDATED_CONTENT);

        // Validate the ChildRelationData in Elasticsearch
        verify(mockChildRelationDataSearchRepository, times(1)).save(testChildRelationData);
    }

    @Test
    @Transactional
    public void updateNonExistingChildRelationData() throws Exception {
        int databaseSizeBeforeUpdate = childRelationDataRepository.findAll().size();

        // Create the ChildRelationData
        ChildRelationDataDTO childRelationDataDTO = childRelationDataMapper.toDto(childRelationData);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restChildRelationDataMockMvc
            .perform(
                put("/api/child-relation-data")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(childRelationDataDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ChildRelationData in the database
        List<ChildRelationData> childRelationDataList = childRelationDataRepository.findAll();
        assertThat(childRelationDataList).hasSize(databaseSizeBeforeUpdate);

        // Validate the ChildRelationData in Elasticsearch
        verify(mockChildRelationDataSearchRepository, times(0)).save(childRelationData);
    }

    @Test
    @Transactional
    public void deleteChildRelationData() throws Exception {
        // Initialize the database
        childRelationDataRepository.saveAndFlush(childRelationData);

        int databaseSizeBeforeDelete = childRelationDataRepository.findAll().size();

        // Delete the childRelationData
        restChildRelationDataMockMvc
            .perform(delete("/api/child-relation-data/{id}", childRelationData.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<ChildRelationData> childRelationDataList = childRelationDataRepository.findAll();
        assertThat(childRelationDataList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the ChildRelationData in Elasticsearch
        verify(mockChildRelationDataSearchRepository, times(1)).deleteById(childRelationData.getId());
    }

    @Test
    @Transactional
    public void searchChildRelationData() throws Exception {
        // Configure the mock search repository
        // Initialize the database
        childRelationDataRepository.saveAndFlush(childRelationData);
        when(mockChildRelationDataSearchRepository.search(queryStringQuery("id:" + childRelationData.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(childRelationData), PageRequest.of(0, 1), 1));

        // Search the childRelationData
        restChildRelationDataMockMvc
            .perform(get("/api/_search/child-relation-data?query=id:" + childRelationData.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(childRelationData.getId().intValue())))
            .andExpect(jsonPath("$.[*].content").value(hasItem(DEFAULT_CONTENT)));
    }
}
