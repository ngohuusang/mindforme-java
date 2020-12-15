package com.mindforme.app.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.elasticsearch.index.query.QueryBuilders.queryStringQuery;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.mindforme.app.MindformeApp;
import com.mindforme.app.domain.StateData;
import com.mindforme.app.repository.StateDataRepository;
import com.mindforme.app.repository.search.StateDataSearchRepository;
import com.mindforme.app.service.StateDataService;
import com.mindforme.app.service.dto.StateDataDTO;
import com.mindforme.app.service.mapper.StateDataMapper;
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
 * Integration tests for the {@link StateDataResource} REST controller.
 */
@SpringBootTest(classes = MindformeApp.class)
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
public class StateDataResourceIT {
    private static final String DEFAULT_CONTENT = "AAAAAAAAAA";
    private static final String UPDATED_CONTENT = "BBBBBBBBBB";

    @Autowired
    private StateDataRepository stateDataRepository;

    @Autowired
    private StateDataMapper stateDataMapper;

    @Autowired
    private StateDataService stateDataService;

    /**
     * This repository is mocked in the com.mindforme.app.repository.search test package.
     *
     * @see com.mindforme.app.repository.search.StateDataSearchRepositoryMockConfiguration
     */
    @Autowired
    private StateDataSearchRepository mockStateDataSearchRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restStateDataMockMvc;

    private StateData stateData;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static StateData createEntity(EntityManager em) {
        StateData stateData = new StateData().content(DEFAULT_CONTENT);
        return stateData;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static StateData createUpdatedEntity(EntityManager em) {
        StateData stateData = new StateData().content(UPDATED_CONTENT);
        return stateData;
    }

    @BeforeEach
    public void initTest() {
        stateData = createEntity(em);
    }

    @Test
    @Transactional
    public void createStateData() throws Exception {
        int databaseSizeBeforeCreate = stateDataRepository.findAll().size();
        // Create the StateData
        StateDataDTO stateDataDTO = stateDataMapper.toDto(stateData);
        restStateDataMockMvc
            .perform(
                post("/api/state-data").contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(stateDataDTO))
            )
            .andExpect(status().isCreated());

        // Validate the StateData in the database
        List<StateData> stateDataList = stateDataRepository.findAll();
        assertThat(stateDataList).hasSize(databaseSizeBeforeCreate + 1);
        StateData testStateData = stateDataList.get(stateDataList.size() - 1);
        assertThat(testStateData.getContent()).isEqualTo(DEFAULT_CONTENT);

        // Validate the StateData in Elasticsearch
        verify(mockStateDataSearchRepository, times(1)).save(testStateData);
    }

    @Test
    @Transactional
    public void createStateDataWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = stateDataRepository.findAll().size();

        // Create the StateData with an existing ID
        stateData.setId(1L);
        StateDataDTO stateDataDTO = stateDataMapper.toDto(stateData);

        // An entity with an existing ID cannot be created, so this API call must fail
        restStateDataMockMvc
            .perform(
                post("/api/state-data").contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(stateDataDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the StateData in the database
        List<StateData> stateDataList = stateDataRepository.findAll();
        assertThat(stateDataList).hasSize(databaseSizeBeforeCreate);

        // Validate the StateData in Elasticsearch
        verify(mockStateDataSearchRepository, times(0)).save(stateData);
    }

    @Test
    @Transactional
    public void getAllStateData() throws Exception {
        // Initialize the database
        stateDataRepository.saveAndFlush(stateData);

        // Get all the stateDataList
        restStateDataMockMvc
            .perform(get("/api/state-data?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(stateData.getId().intValue())))
            .andExpect(jsonPath("$.[*].content").value(hasItem(DEFAULT_CONTENT)));
    }

    @Test
    @Transactional
    public void getStateData() throws Exception {
        // Initialize the database
        stateDataRepository.saveAndFlush(stateData);

        // Get the stateData
        restStateDataMockMvc
            .perform(get("/api/state-data/{id}", stateData.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(stateData.getId().intValue()))
            .andExpect(jsonPath("$.content").value(DEFAULT_CONTENT));
    }

    @Test
    @Transactional
    public void getNonExistingStateData() throws Exception {
        // Get the stateData
        restStateDataMockMvc.perform(get("/api/state-data/{id}", Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateStateData() throws Exception {
        // Initialize the database
        stateDataRepository.saveAndFlush(stateData);

        int databaseSizeBeforeUpdate = stateDataRepository.findAll().size();

        // Update the stateData
        StateData updatedStateData = stateDataRepository.findById(stateData.getId()).get();
        // Disconnect from session so that the updates on updatedStateData are not directly saved in db
        em.detach(updatedStateData);
        updatedStateData.content(UPDATED_CONTENT);
        StateDataDTO stateDataDTO = stateDataMapper.toDto(updatedStateData);

        restStateDataMockMvc
            .perform(
                put("/api/state-data").contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(stateDataDTO))
            )
            .andExpect(status().isOk());

        // Validate the StateData in the database
        List<StateData> stateDataList = stateDataRepository.findAll();
        assertThat(stateDataList).hasSize(databaseSizeBeforeUpdate);
        StateData testStateData = stateDataList.get(stateDataList.size() - 1);
        assertThat(testStateData.getContent()).isEqualTo(UPDATED_CONTENT);

        // Validate the StateData in Elasticsearch
        verify(mockStateDataSearchRepository, times(1)).save(testStateData);
    }

    @Test
    @Transactional
    public void updateNonExistingStateData() throws Exception {
        int databaseSizeBeforeUpdate = stateDataRepository.findAll().size();

        // Create the StateData
        StateDataDTO stateDataDTO = stateDataMapper.toDto(stateData);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restStateDataMockMvc
            .perform(
                put("/api/state-data").contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(stateDataDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the StateData in the database
        List<StateData> stateDataList = stateDataRepository.findAll();
        assertThat(stateDataList).hasSize(databaseSizeBeforeUpdate);

        // Validate the StateData in Elasticsearch
        verify(mockStateDataSearchRepository, times(0)).save(stateData);
    }

    @Test
    @Transactional
    public void deleteStateData() throws Exception {
        // Initialize the database
        stateDataRepository.saveAndFlush(stateData);

        int databaseSizeBeforeDelete = stateDataRepository.findAll().size();

        // Delete the stateData
        restStateDataMockMvc
            .perform(delete("/api/state-data/{id}", stateData.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<StateData> stateDataList = stateDataRepository.findAll();
        assertThat(stateDataList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the StateData in Elasticsearch
        verify(mockStateDataSearchRepository, times(1)).deleteById(stateData.getId());
    }

    @Test
    @Transactional
    public void searchStateData() throws Exception {
        // Configure the mock search repository
        // Initialize the database
        stateDataRepository.saveAndFlush(stateData);
        when(mockStateDataSearchRepository.search(queryStringQuery("id:" + stateData.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(stateData), PageRequest.of(0, 1), 1));

        // Search the stateData
        restStateDataMockMvc
            .perform(get("/api/_search/state-data?query=id:" + stateData.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(stateData.getId().intValue())))
            .andExpect(jsonPath("$.[*].content").value(hasItem(DEFAULT_CONTENT)));
    }
}
