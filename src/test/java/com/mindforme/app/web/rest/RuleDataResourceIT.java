package com.mindforme.app.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.elasticsearch.index.query.QueryBuilders.queryStringQuery;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.mindforme.app.MindformeApp;
import com.mindforme.app.domain.RuleData;
import com.mindforme.app.repository.RuleDataRepository;
import com.mindforme.app.repository.search.RuleDataSearchRepository;
import com.mindforme.app.service.RuleDataService;
import com.mindforme.app.service.dto.RuleDataDTO;
import com.mindforme.app.service.mapper.RuleDataMapper;
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
 * Integration tests for the {@link RuleDataResource} REST controller.
 */
@SpringBootTest(classes = MindformeApp.class)
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
public class RuleDataResourceIT {
    private static final String DEFAULT_CONTENT = "AAAAAAAAAA";
    private static final String UPDATED_CONTENT = "BBBBBBBBBB";

    @Autowired
    private RuleDataRepository ruleDataRepository;

    @Autowired
    private RuleDataMapper ruleDataMapper;

    @Autowired
    private RuleDataService ruleDataService;

    /**
     * This repository is mocked in the com.mindforme.app.repository.search test package.
     *
     * @see com.mindforme.app.repository.search.RuleDataSearchRepositoryMockConfiguration
     */
    @Autowired
    private RuleDataSearchRepository mockRuleDataSearchRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restRuleDataMockMvc;

    private RuleData ruleData;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static RuleData createEntity(EntityManager em) {
        RuleData ruleData = new RuleData().content(DEFAULT_CONTENT);
        return ruleData;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static RuleData createUpdatedEntity(EntityManager em) {
        RuleData ruleData = new RuleData().content(UPDATED_CONTENT);
        return ruleData;
    }

    @BeforeEach
    public void initTest() {
        ruleData = createEntity(em);
    }

    @Test
    @Transactional
    public void createRuleData() throws Exception {
        int databaseSizeBeforeCreate = ruleDataRepository.findAll().size();
        // Create the RuleData
        RuleDataDTO ruleDataDTO = ruleDataMapper.toDto(ruleData);
        restRuleDataMockMvc
            .perform(post("/api/rule-data").contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(ruleDataDTO)))
            .andExpect(status().isCreated());

        // Validate the RuleData in the database
        List<RuleData> ruleDataList = ruleDataRepository.findAll();
        assertThat(ruleDataList).hasSize(databaseSizeBeforeCreate + 1);
        RuleData testRuleData = ruleDataList.get(ruleDataList.size() - 1);
        assertThat(testRuleData.getContent()).isEqualTo(DEFAULT_CONTENT);

        // Validate the RuleData in Elasticsearch
        verify(mockRuleDataSearchRepository, times(1)).save(testRuleData);
    }

    @Test
    @Transactional
    public void createRuleDataWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = ruleDataRepository.findAll().size();

        // Create the RuleData with an existing ID
        ruleData.setId(1L);
        RuleDataDTO ruleDataDTO = ruleDataMapper.toDto(ruleData);

        // An entity with an existing ID cannot be created, so this API call must fail
        restRuleDataMockMvc
            .perform(post("/api/rule-data").contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(ruleDataDTO)))
            .andExpect(status().isBadRequest());

        // Validate the RuleData in the database
        List<RuleData> ruleDataList = ruleDataRepository.findAll();
        assertThat(ruleDataList).hasSize(databaseSizeBeforeCreate);

        // Validate the RuleData in Elasticsearch
        verify(mockRuleDataSearchRepository, times(0)).save(ruleData);
    }

    @Test
    @Transactional
    public void getAllRuleData() throws Exception {
        // Initialize the database
        ruleDataRepository.saveAndFlush(ruleData);

        // Get all the ruleDataList
        restRuleDataMockMvc
            .perform(get("/api/rule-data?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(ruleData.getId().intValue())))
            .andExpect(jsonPath("$.[*].content").value(hasItem(DEFAULT_CONTENT)));
    }

    @Test
    @Transactional
    public void getRuleData() throws Exception {
        // Initialize the database
        ruleDataRepository.saveAndFlush(ruleData);

        // Get the ruleData
        restRuleDataMockMvc
            .perform(get("/api/rule-data/{id}", ruleData.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(ruleData.getId().intValue()))
            .andExpect(jsonPath("$.content").value(DEFAULT_CONTENT));
    }

    @Test
    @Transactional
    public void getNonExistingRuleData() throws Exception {
        // Get the ruleData
        restRuleDataMockMvc.perform(get("/api/rule-data/{id}", Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateRuleData() throws Exception {
        // Initialize the database
        ruleDataRepository.saveAndFlush(ruleData);

        int databaseSizeBeforeUpdate = ruleDataRepository.findAll().size();

        // Update the ruleData
        RuleData updatedRuleData = ruleDataRepository.findById(ruleData.getId()).get();
        // Disconnect from session so that the updates on updatedRuleData are not directly saved in db
        em.detach(updatedRuleData);
        updatedRuleData.content(UPDATED_CONTENT);
        RuleDataDTO ruleDataDTO = ruleDataMapper.toDto(updatedRuleData);

        restRuleDataMockMvc
            .perform(put("/api/rule-data").contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(ruleDataDTO)))
            .andExpect(status().isOk());

        // Validate the RuleData in the database
        List<RuleData> ruleDataList = ruleDataRepository.findAll();
        assertThat(ruleDataList).hasSize(databaseSizeBeforeUpdate);
        RuleData testRuleData = ruleDataList.get(ruleDataList.size() - 1);
        assertThat(testRuleData.getContent()).isEqualTo(UPDATED_CONTENT);

        // Validate the RuleData in Elasticsearch
        verify(mockRuleDataSearchRepository, times(1)).save(testRuleData);
    }

    @Test
    @Transactional
    public void updateNonExistingRuleData() throws Exception {
        int databaseSizeBeforeUpdate = ruleDataRepository.findAll().size();

        // Create the RuleData
        RuleDataDTO ruleDataDTO = ruleDataMapper.toDto(ruleData);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restRuleDataMockMvc
            .perform(put("/api/rule-data").contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(ruleDataDTO)))
            .andExpect(status().isBadRequest());

        // Validate the RuleData in the database
        List<RuleData> ruleDataList = ruleDataRepository.findAll();
        assertThat(ruleDataList).hasSize(databaseSizeBeforeUpdate);

        // Validate the RuleData in Elasticsearch
        verify(mockRuleDataSearchRepository, times(0)).save(ruleData);
    }

    @Test
    @Transactional
    public void deleteRuleData() throws Exception {
        // Initialize the database
        ruleDataRepository.saveAndFlush(ruleData);

        int databaseSizeBeforeDelete = ruleDataRepository.findAll().size();

        // Delete the ruleData
        restRuleDataMockMvc
            .perform(delete("/api/rule-data/{id}", ruleData.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<RuleData> ruleDataList = ruleDataRepository.findAll();
        assertThat(ruleDataList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the RuleData in Elasticsearch
        verify(mockRuleDataSearchRepository, times(1)).deleteById(ruleData.getId());
    }

    @Test
    @Transactional
    public void searchRuleData() throws Exception {
        // Configure the mock search repository
        // Initialize the database
        ruleDataRepository.saveAndFlush(ruleData);
        when(mockRuleDataSearchRepository.search(queryStringQuery("id:" + ruleData.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(ruleData), PageRequest.of(0, 1), 1));

        // Search the ruleData
        restRuleDataMockMvc
            .perform(get("/api/_search/rule-data?query=id:" + ruleData.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(ruleData.getId().intValue())))
            .andExpect(jsonPath("$.[*].content").value(hasItem(DEFAULT_CONTENT)));
    }
}
