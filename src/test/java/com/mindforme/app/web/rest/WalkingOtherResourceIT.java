package com.mindforme.app.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.elasticsearch.index.query.QueryBuilders.queryStringQuery;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.mindforme.app.MindformeApp;
import com.mindforme.app.domain.WalkingOther;
import com.mindforme.app.domain.enumeration.Status;
import com.mindforme.app.repository.WalkingOtherRepository;
import com.mindforme.app.repository.search.WalkingOtherSearchRepository;
import com.mindforme.app.service.WalkingOtherService;
import com.mindforme.app.service.dto.WalkingOtherDTO;
import com.mindforme.app.service.mapper.WalkingOtherMapper;
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
 * Integration tests for the {@link WalkingOtherResource} REST controller.
 */
@SpringBootTest(classes = MindformeApp.class)
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
public class WalkingOtherResourceIT {
    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final Status DEFAULT_STATUS = Status.Active;
    private static final Status UPDATED_STATUS = Status.Inactive;

    @Autowired
    private WalkingOtherRepository walkingOtherRepository;

    @Autowired
    private WalkingOtherMapper walkingOtherMapper;

    @Autowired
    private WalkingOtherService walkingOtherService;

    /**
     * This repository is mocked in the com.mindforme.app.repository.search test package.
     *
     * @see com.mindforme.app.repository.search.WalkingOtherSearchRepositoryMockConfiguration
     */
    @Autowired
    private WalkingOtherSearchRepository mockWalkingOtherSearchRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restWalkingOtherMockMvc;

    private WalkingOther walkingOther;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static WalkingOther createEntity(EntityManager em) {
        WalkingOther walkingOther = new WalkingOther().name(DEFAULT_NAME).status(DEFAULT_STATUS);
        return walkingOther;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static WalkingOther createUpdatedEntity(EntityManager em) {
        WalkingOther walkingOther = new WalkingOther().name(UPDATED_NAME).status(UPDATED_STATUS);
        return walkingOther;
    }

    @BeforeEach
    public void initTest() {
        walkingOther = createEntity(em);
    }

    @Test
    @Transactional
    public void createWalkingOther() throws Exception {
        int databaseSizeBeforeCreate = walkingOtherRepository.findAll().size();
        // Create the WalkingOther
        WalkingOtherDTO walkingOtherDTO = walkingOtherMapper.toDto(walkingOther);
        restWalkingOtherMockMvc
            .perform(
                post("/api/walking-others")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(walkingOtherDTO))
            )
            .andExpect(status().isCreated());

        // Validate the WalkingOther in the database
        List<WalkingOther> walkingOtherList = walkingOtherRepository.findAll();
        assertThat(walkingOtherList).hasSize(databaseSizeBeforeCreate + 1);
        WalkingOther testWalkingOther = walkingOtherList.get(walkingOtherList.size() - 1);
        assertThat(testWalkingOther.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testWalkingOther.getStatus()).isEqualTo(DEFAULT_STATUS);

        // Validate the WalkingOther in Elasticsearch
        verify(mockWalkingOtherSearchRepository, times(1)).save(testWalkingOther);
    }

    @Test
    @Transactional
    public void createWalkingOtherWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = walkingOtherRepository.findAll().size();

        // Create the WalkingOther with an existing ID
        walkingOther.setId(1L);
        WalkingOtherDTO walkingOtherDTO = walkingOtherMapper.toDto(walkingOther);

        // An entity with an existing ID cannot be created, so this API call must fail
        restWalkingOtherMockMvc
            .perform(
                post("/api/walking-others")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(walkingOtherDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the WalkingOther in the database
        List<WalkingOther> walkingOtherList = walkingOtherRepository.findAll();
        assertThat(walkingOtherList).hasSize(databaseSizeBeforeCreate);

        // Validate the WalkingOther in Elasticsearch
        verify(mockWalkingOtherSearchRepository, times(0)).save(walkingOther);
    }

    @Test
    @Transactional
    public void getAllWalkingOthers() throws Exception {
        // Initialize the database
        walkingOtherRepository.saveAndFlush(walkingOther);

        // Get all the walkingOtherList
        restWalkingOtherMockMvc
            .perform(get("/api/walking-others?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(walkingOther.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.toString())));
    }

    @Test
    @Transactional
    public void getWalkingOther() throws Exception {
        // Initialize the database
        walkingOtherRepository.saveAndFlush(walkingOther);

        // Get the walkingOther
        restWalkingOtherMockMvc
            .perform(get("/api/walking-others/{id}", walkingOther.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(walkingOther.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingWalkingOther() throws Exception {
        // Get the walkingOther
        restWalkingOtherMockMvc.perform(get("/api/walking-others/{id}", Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateWalkingOther() throws Exception {
        // Initialize the database
        walkingOtherRepository.saveAndFlush(walkingOther);

        int databaseSizeBeforeUpdate = walkingOtherRepository.findAll().size();

        // Update the walkingOther
        WalkingOther updatedWalkingOther = walkingOtherRepository.findById(walkingOther.getId()).get();
        // Disconnect from session so that the updates on updatedWalkingOther are not directly saved in db
        em.detach(updatedWalkingOther);
        updatedWalkingOther.name(UPDATED_NAME).status(UPDATED_STATUS);
        WalkingOtherDTO walkingOtherDTO = walkingOtherMapper.toDto(updatedWalkingOther);

        restWalkingOtherMockMvc
            .perform(
                put("/api/walking-others")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(walkingOtherDTO))
            )
            .andExpect(status().isOk());

        // Validate the WalkingOther in the database
        List<WalkingOther> walkingOtherList = walkingOtherRepository.findAll();
        assertThat(walkingOtherList).hasSize(databaseSizeBeforeUpdate);
        WalkingOther testWalkingOther = walkingOtherList.get(walkingOtherList.size() - 1);
        assertThat(testWalkingOther.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testWalkingOther.getStatus()).isEqualTo(UPDATED_STATUS);

        // Validate the WalkingOther in Elasticsearch
        verify(mockWalkingOtherSearchRepository, times(1)).save(testWalkingOther);
    }

    @Test
    @Transactional
    public void updateNonExistingWalkingOther() throws Exception {
        int databaseSizeBeforeUpdate = walkingOtherRepository.findAll().size();

        // Create the WalkingOther
        WalkingOtherDTO walkingOtherDTO = walkingOtherMapper.toDto(walkingOther);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restWalkingOtherMockMvc
            .perform(
                put("/api/walking-others")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(walkingOtherDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the WalkingOther in the database
        List<WalkingOther> walkingOtherList = walkingOtherRepository.findAll();
        assertThat(walkingOtherList).hasSize(databaseSizeBeforeUpdate);

        // Validate the WalkingOther in Elasticsearch
        verify(mockWalkingOtherSearchRepository, times(0)).save(walkingOther);
    }

    @Test
    @Transactional
    public void deleteWalkingOther() throws Exception {
        // Initialize the database
        walkingOtherRepository.saveAndFlush(walkingOther);

        int databaseSizeBeforeDelete = walkingOtherRepository.findAll().size();

        // Delete the walkingOther
        restWalkingOtherMockMvc
            .perform(delete("/api/walking-others/{id}", walkingOther.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<WalkingOther> walkingOtherList = walkingOtherRepository.findAll();
        assertThat(walkingOtherList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the WalkingOther in Elasticsearch
        verify(mockWalkingOtherSearchRepository, times(1)).deleteById(walkingOther.getId());
    }

    @Test
    @Transactional
    public void searchWalkingOther() throws Exception {
        // Configure the mock search repository
        // Initialize the database
        walkingOtherRepository.saveAndFlush(walkingOther);
        when(mockWalkingOtherSearchRepository.search(queryStringQuery("id:" + walkingOther.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(walkingOther), PageRequest.of(0, 1), 1));

        // Search the walkingOther
        restWalkingOtherMockMvc
            .perform(get("/api/_search/walking-others?query=id:" + walkingOther.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(walkingOther.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.toString())));
    }
}
