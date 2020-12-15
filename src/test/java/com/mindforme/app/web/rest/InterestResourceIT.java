package com.mindforme.app.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.elasticsearch.index.query.QueryBuilders.queryStringQuery;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.mindforme.app.MindformeApp;
import com.mindforme.app.domain.Interest;
import com.mindforme.app.domain.enumeration.Status;
import com.mindforme.app.repository.InterestRepository;
import com.mindforme.app.repository.search.InterestSearchRepository;
import com.mindforme.app.service.InterestService;
import com.mindforme.app.service.dto.InterestDTO;
import com.mindforme.app.service.mapper.InterestMapper;
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
 * Integration tests for the {@link InterestResource} REST controller.
 */
@SpringBootTest(classes = MindformeApp.class)
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
public class InterestResourceIT {
    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final Status DEFAULT_STATUS = Status.Active;
    private static final Status UPDATED_STATUS = Status.Inactive;

    @Autowired
    private InterestRepository interestRepository;

    @Autowired
    private InterestMapper interestMapper;

    @Autowired
    private InterestService interestService;

    /**
     * This repository is mocked in the com.mindforme.app.repository.search test package.
     *
     * @see com.mindforme.app.repository.search.InterestSearchRepositoryMockConfiguration
     */
    @Autowired
    private InterestSearchRepository mockInterestSearchRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restInterestMockMvc;

    private Interest interest;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Interest createEntity(EntityManager em) {
        Interest interest = new Interest().name(DEFAULT_NAME).status(DEFAULT_STATUS);
        return interest;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Interest createUpdatedEntity(EntityManager em) {
        Interest interest = new Interest().name(UPDATED_NAME).status(UPDATED_STATUS);
        return interest;
    }

    @BeforeEach
    public void initTest() {
        interest = createEntity(em);
    }

    @Test
    @Transactional
    public void createInterest() throws Exception {
        int databaseSizeBeforeCreate = interestRepository.findAll().size();
        // Create the Interest
        InterestDTO interestDTO = interestMapper.toDto(interest);
        restInterestMockMvc
            .perform(post("/api/interests").contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(interestDTO)))
            .andExpect(status().isCreated());

        // Validate the Interest in the database
        List<Interest> interestList = interestRepository.findAll();
        assertThat(interestList).hasSize(databaseSizeBeforeCreate + 1);
        Interest testInterest = interestList.get(interestList.size() - 1);
        assertThat(testInterest.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testInterest.getStatus()).isEqualTo(DEFAULT_STATUS);

        // Validate the Interest in Elasticsearch
        verify(mockInterestSearchRepository, times(1)).save(testInterest);
    }

    @Test
    @Transactional
    public void createInterestWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = interestRepository.findAll().size();

        // Create the Interest with an existing ID
        interest.setId(1L);
        InterestDTO interestDTO = interestMapper.toDto(interest);

        // An entity with an existing ID cannot be created, so this API call must fail
        restInterestMockMvc
            .perform(post("/api/interests").contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(interestDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Interest in the database
        List<Interest> interestList = interestRepository.findAll();
        assertThat(interestList).hasSize(databaseSizeBeforeCreate);

        // Validate the Interest in Elasticsearch
        verify(mockInterestSearchRepository, times(0)).save(interest);
    }

    @Test
    @Transactional
    public void getAllInterests() throws Exception {
        // Initialize the database
        interestRepository.saveAndFlush(interest);

        // Get all the interestList
        restInterestMockMvc
            .perform(get("/api/interests?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(interest.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.toString())));
    }

    @Test
    @Transactional
    public void getInterest() throws Exception {
        // Initialize the database
        interestRepository.saveAndFlush(interest);

        // Get the interest
        restInterestMockMvc
            .perform(get("/api/interests/{id}", interest.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(interest.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingInterest() throws Exception {
        // Get the interest
        restInterestMockMvc.perform(get("/api/interests/{id}", Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateInterest() throws Exception {
        // Initialize the database
        interestRepository.saveAndFlush(interest);

        int databaseSizeBeforeUpdate = interestRepository.findAll().size();

        // Update the interest
        Interest updatedInterest = interestRepository.findById(interest.getId()).get();
        // Disconnect from session so that the updates on updatedInterest are not directly saved in db
        em.detach(updatedInterest);
        updatedInterest.name(UPDATED_NAME).status(UPDATED_STATUS);
        InterestDTO interestDTO = interestMapper.toDto(updatedInterest);

        restInterestMockMvc
            .perform(put("/api/interests").contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(interestDTO)))
            .andExpect(status().isOk());

        // Validate the Interest in the database
        List<Interest> interestList = interestRepository.findAll();
        assertThat(interestList).hasSize(databaseSizeBeforeUpdate);
        Interest testInterest = interestList.get(interestList.size() - 1);
        assertThat(testInterest.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testInterest.getStatus()).isEqualTo(UPDATED_STATUS);

        // Validate the Interest in Elasticsearch
        verify(mockInterestSearchRepository, times(1)).save(testInterest);
    }

    @Test
    @Transactional
    public void updateNonExistingInterest() throws Exception {
        int databaseSizeBeforeUpdate = interestRepository.findAll().size();

        // Create the Interest
        InterestDTO interestDTO = interestMapper.toDto(interest);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restInterestMockMvc
            .perform(put("/api/interests").contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(interestDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Interest in the database
        List<Interest> interestList = interestRepository.findAll();
        assertThat(interestList).hasSize(databaseSizeBeforeUpdate);

        // Validate the Interest in Elasticsearch
        verify(mockInterestSearchRepository, times(0)).save(interest);
    }

    @Test
    @Transactional
    public void deleteInterest() throws Exception {
        // Initialize the database
        interestRepository.saveAndFlush(interest);

        int databaseSizeBeforeDelete = interestRepository.findAll().size();

        // Delete the interest
        restInterestMockMvc
            .perform(delete("/api/interests/{id}", interest.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Interest> interestList = interestRepository.findAll();
        assertThat(interestList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the Interest in Elasticsearch
        verify(mockInterestSearchRepository, times(1)).deleteById(interest.getId());
    }

    @Test
    @Transactional
    public void searchInterest() throws Exception {
        // Configure the mock search repository
        // Initialize the database
        interestRepository.saveAndFlush(interest);
        when(mockInterestSearchRepository.search(queryStringQuery("id:" + interest.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(interest), PageRequest.of(0, 1), 1));

        // Search the interest
        restInterestMockMvc
            .perform(get("/api/_search/interests?query=id:" + interest.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(interest.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.toString())));
    }
}
