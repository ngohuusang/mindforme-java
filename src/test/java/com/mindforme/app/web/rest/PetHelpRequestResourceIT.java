package com.mindforme.app.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.elasticsearch.index.query.QueryBuilders.queryStringQuery;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.mindforme.app.MindformeApp;
import com.mindforme.app.domain.PetHelpRequest;
import com.mindforme.app.repository.PetHelpRequestRepository;
import com.mindforme.app.repository.search.PetHelpRequestSearchRepository;
import com.mindforme.app.service.PetHelpRequestService;
import com.mindforme.app.service.dto.PetHelpRequestDTO;
import com.mindforme.app.service.mapper.PetHelpRequestMapper;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
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
 * Integration tests for the {@link PetHelpRequestResource} REST controller.
 */
@SpringBootTest(classes = MindformeApp.class)
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
public class PetHelpRequestResourceIT {
    private static final Long DEFAULT_TOTAL_HELP_TIME = 1L;
    private static final Long UPDATED_TOTAL_HELP_TIME = 2L;

    private static final Instant DEFAULT_DATE_FROM = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_DATE_FROM = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_DATE_TO = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_DATE_TO = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_TIME_FROM = "AAAAAAAAAA";
    private static final String UPDATED_TIME_FROM = "BBBBBBBBBB";

    private static final String DEFAULT_TIME_TO = "AAAAAAAAAA";
    private static final String UPDATED_TIME_TO = "BBBBBBBBBB";

    private static final String DEFAULT_CONTENT = "AAAAAAAAAA";
    private static final String UPDATED_CONTENT = "BBBBBBBBBB";

    @Autowired
    private PetHelpRequestRepository petHelpRequestRepository;

    @Mock
    private PetHelpRequestRepository petHelpRequestRepositoryMock;

    @Autowired
    private PetHelpRequestMapper petHelpRequestMapper;

    @Mock
    private PetHelpRequestService petHelpRequestServiceMock;

    @Autowired
    private PetHelpRequestService petHelpRequestService;

    /**
     * This repository is mocked in the com.mindforme.app.repository.search test package.
     *
     * @see com.mindforme.app.repository.search.PetHelpRequestSearchRepositoryMockConfiguration
     */
    @Autowired
    private PetHelpRequestSearchRepository mockPetHelpRequestSearchRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restPetHelpRequestMockMvc;

    private PetHelpRequest petHelpRequest;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PetHelpRequest createEntity(EntityManager em) {
        PetHelpRequest petHelpRequest = new PetHelpRequest()
            .totalHelpTime(DEFAULT_TOTAL_HELP_TIME)
            .dateFrom(DEFAULT_DATE_FROM)
            .dateTo(DEFAULT_DATE_TO)
            .timeFrom(DEFAULT_TIME_FROM)
            .timeTo(DEFAULT_TIME_TO)
            .content(DEFAULT_CONTENT);
        return petHelpRequest;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PetHelpRequest createUpdatedEntity(EntityManager em) {
        PetHelpRequest petHelpRequest = new PetHelpRequest()
            .totalHelpTime(UPDATED_TOTAL_HELP_TIME)
            .dateFrom(UPDATED_DATE_FROM)
            .dateTo(UPDATED_DATE_TO)
            .timeFrom(UPDATED_TIME_FROM)
            .timeTo(UPDATED_TIME_TO)
            .content(UPDATED_CONTENT);
        return petHelpRequest;
    }

    @BeforeEach
    public void initTest() {
        petHelpRequest = createEntity(em);
    }

    @Test
    @Transactional
    public void createPetHelpRequest() throws Exception {
        int databaseSizeBeforeCreate = petHelpRequestRepository.findAll().size();
        // Create the PetHelpRequest
        PetHelpRequestDTO petHelpRequestDTO = petHelpRequestMapper.toDto(petHelpRequest);
        restPetHelpRequestMockMvc
            .perform(
                post("/api/pet-help-requests")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(petHelpRequestDTO))
            )
            .andExpect(status().isCreated());

        // Validate the PetHelpRequest in the database
        List<PetHelpRequest> petHelpRequestList = petHelpRequestRepository.findAll();
        assertThat(petHelpRequestList).hasSize(databaseSizeBeforeCreate + 1);
        PetHelpRequest testPetHelpRequest = petHelpRequestList.get(petHelpRequestList.size() - 1);
        assertThat(testPetHelpRequest.getTotalHelpTime()).isEqualTo(DEFAULT_TOTAL_HELP_TIME);
        assertThat(testPetHelpRequest.getDateFrom()).isEqualTo(DEFAULT_DATE_FROM);
        assertThat(testPetHelpRequest.getDateTo()).isEqualTo(DEFAULT_DATE_TO);
        assertThat(testPetHelpRequest.getTimeFrom()).isEqualTo(DEFAULT_TIME_FROM);
        assertThat(testPetHelpRequest.getTimeTo()).isEqualTo(DEFAULT_TIME_TO);
        assertThat(testPetHelpRequest.getContent()).isEqualTo(DEFAULT_CONTENT);

        // Validate the PetHelpRequest in Elasticsearch
        verify(mockPetHelpRequestSearchRepository, times(1)).save(testPetHelpRequest);
    }

    @Test
    @Transactional
    public void createPetHelpRequestWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = petHelpRequestRepository.findAll().size();

        // Create the PetHelpRequest with an existing ID
        petHelpRequest.setId(1L);
        PetHelpRequestDTO petHelpRequestDTO = petHelpRequestMapper.toDto(petHelpRequest);

        // An entity with an existing ID cannot be created, so this API call must fail
        restPetHelpRequestMockMvc
            .perform(
                post("/api/pet-help-requests")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(petHelpRequestDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the PetHelpRequest in the database
        List<PetHelpRequest> petHelpRequestList = petHelpRequestRepository.findAll();
        assertThat(petHelpRequestList).hasSize(databaseSizeBeforeCreate);

        // Validate the PetHelpRequest in Elasticsearch
        verify(mockPetHelpRequestSearchRepository, times(0)).save(petHelpRequest);
    }

    @Test
    @Transactional
    public void getAllPetHelpRequests() throws Exception {
        // Initialize the database
        petHelpRequestRepository.saveAndFlush(petHelpRequest);

        // Get all the petHelpRequestList
        restPetHelpRequestMockMvc
            .perform(get("/api/pet-help-requests?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(petHelpRequest.getId().intValue())))
            .andExpect(jsonPath("$.[*].totalHelpTime").value(hasItem(DEFAULT_TOTAL_HELP_TIME.intValue())))
            .andExpect(jsonPath("$.[*].dateFrom").value(hasItem(DEFAULT_DATE_FROM.toString())))
            .andExpect(jsonPath("$.[*].dateTo").value(hasItem(DEFAULT_DATE_TO.toString())))
            .andExpect(jsonPath("$.[*].timeFrom").value(hasItem(DEFAULT_TIME_FROM)))
            .andExpect(jsonPath("$.[*].timeTo").value(hasItem(DEFAULT_TIME_TO)))
            .andExpect(jsonPath("$.[*].content").value(hasItem(DEFAULT_CONTENT)));
    }

    @SuppressWarnings({ "unchecked" })
    public void getAllPetHelpRequestsWithEagerRelationshipsIsEnabled() throws Exception {
        when(petHelpRequestServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restPetHelpRequestMockMvc.perform(get("/api/pet-help-requests?eagerload=true")).andExpect(status().isOk());

        verify(petHelpRequestServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({ "unchecked" })
    public void getAllPetHelpRequestsWithEagerRelationshipsIsNotEnabled() throws Exception {
        when(petHelpRequestServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restPetHelpRequestMockMvc.perform(get("/api/pet-help-requests?eagerload=true")).andExpect(status().isOk());

        verify(petHelpRequestServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @Test
    @Transactional
    public void getPetHelpRequest() throws Exception {
        // Initialize the database
        petHelpRequestRepository.saveAndFlush(petHelpRequest);

        // Get the petHelpRequest
        restPetHelpRequestMockMvc
            .perform(get("/api/pet-help-requests/{id}", petHelpRequest.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(petHelpRequest.getId().intValue()))
            .andExpect(jsonPath("$.totalHelpTime").value(DEFAULT_TOTAL_HELP_TIME.intValue()))
            .andExpect(jsonPath("$.dateFrom").value(DEFAULT_DATE_FROM.toString()))
            .andExpect(jsonPath("$.dateTo").value(DEFAULT_DATE_TO.toString()))
            .andExpect(jsonPath("$.timeFrom").value(DEFAULT_TIME_FROM))
            .andExpect(jsonPath("$.timeTo").value(DEFAULT_TIME_TO))
            .andExpect(jsonPath("$.content").value(DEFAULT_CONTENT));
    }

    @Test
    @Transactional
    public void getNonExistingPetHelpRequest() throws Exception {
        // Get the petHelpRequest
        restPetHelpRequestMockMvc.perform(get("/api/pet-help-requests/{id}", Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePetHelpRequest() throws Exception {
        // Initialize the database
        petHelpRequestRepository.saveAndFlush(petHelpRequest);

        int databaseSizeBeforeUpdate = petHelpRequestRepository.findAll().size();

        // Update the petHelpRequest
        PetHelpRequest updatedPetHelpRequest = petHelpRequestRepository.findById(petHelpRequest.getId()).get();
        // Disconnect from session so that the updates on updatedPetHelpRequest are not directly saved in db
        em.detach(updatedPetHelpRequest);
        updatedPetHelpRequest
            .totalHelpTime(UPDATED_TOTAL_HELP_TIME)
            .dateFrom(UPDATED_DATE_FROM)
            .dateTo(UPDATED_DATE_TO)
            .timeFrom(UPDATED_TIME_FROM)
            .timeTo(UPDATED_TIME_TO)
            .content(UPDATED_CONTENT);
        PetHelpRequestDTO petHelpRequestDTO = petHelpRequestMapper.toDto(updatedPetHelpRequest);

        restPetHelpRequestMockMvc
            .perform(
                put("/api/pet-help-requests")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(petHelpRequestDTO))
            )
            .andExpect(status().isOk());

        // Validate the PetHelpRequest in the database
        List<PetHelpRequest> petHelpRequestList = petHelpRequestRepository.findAll();
        assertThat(petHelpRequestList).hasSize(databaseSizeBeforeUpdate);
        PetHelpRequest testPetHelpRequest = petHelpRequestList.get(petHelpRequestList.size() - 1);
        assertThat(testPetHelpRequest.getTotalHelpTime()).isEqualTo(UPDATED_TOTAL_HELP_TIME);
        assertThat(testPetHelpRequest.getDateFrom()).isEqualTo(UPDATED_DATE_FROM);
        assertThat(testPetHelpRequest.getDateTo()).isEqualTo(UPDATED_DATE_TO);
        assertThat(testPetHelpRequest.getTimeFrom()).isEqualTo(UPDATED_TIME_FROM);
        assertThat(testPetHelpRequest.getTimeTo()).isEqualTo(UPDATED_TIME_TO);
        assertThat(testPetHelpRequest.getContent()).isEqualTo(UPDATED_CONTENT);

        // Validate the PetHelpRequest in Elasticsearch
        verify(mockPetHelpRequestSearchRepository, times(1)).save(testPetHelpRequest);
    }

    @Test
    @Transactional
    public void updateNonExistingPetHelpRequest() throws Exception {
        int databaseSizeBeforeUpdate = petHelpRequestRepository.findAll().size();

        // Create the PetHelpRequest
        PetHelpRequestDTO petHelpRequestDTO = petHelpRequestMapper.toDto(petHelpRequest);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPetHelpRequestMockMvc
            .perform(
                put("/api/pet-help-requests")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(petHelpRequestDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the PetHelpRequest in the database
        List<PetHelpRequest> petHelpRequestList = petHelpRequestRepository.findAll();
        assertThat(petHelpRequestList).hasSize(databaseSizeBeforeUpdate);

        // Validate the PetHelpRequest in Elasticsearch
        verify(mockPetHelpRequestSearchRepository, times(0)).save(petHelpRequest);
    }

    @Test
    @Transactional
    public void deletePetHelpRequest() throws Exception {
        // Initialize the database
        petHelpRequestRepository.saveAndFlush(petHelpRequest);

        int databaseSizeBeforeDelete = petHelpRequestRepository.findAll().size();

        // Delete the petHelpRequest
        restPetHelpRequestMockMvc
            .perform(delete("/api/pet-help-requests/{id}", petHelpRequest.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<PetHelpRequest> petHelpRequestList = petHelpRequestRepository.findAll();
        assertThat(petHelpRequestList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the PetHelpRequest in Elasticsearch
        verify(mockPetHelpRequestSearchRepository, times(1)).deleteById(petHelpRequest.getId());
    }

    @Test
    @Transactional
    public void searchPetHelpRequest() throws Exception {
        // Configure the mock search repository
        // Initialize the database
        petHelpRequestRepository.saveAndFlush(petHelpRequest);
        when(mockPetHelpRequestSearchRepository.search(queryStringQuery("id:" + petHelpRequest.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(petHelpRequest), PageRequest.of(0, 1), 1));

        // Search the petHelpRequest
        restPetHelpRequestMockMvc
            .perform(get("/api/_search/pet-help-requests?query=id:" + petHelpRequest.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(petHelpRequest.getId().intValue())))
            .andExpect(jsonPath("$.[*].totalHelpTime").value(hasItem(DEFAULT_TOTAL_HELP_TIME.intValue())))
            .andExpect(jsonPath("$.[*].dateFrom").value(hasItem(DEFAULT_DATE_FROM.toString())))
            .andExpect(jsonPath("$.[*].dateTo").value(hasItem(DEFAULT_DATE_TO.toString())))
            .andExpect(jsonPath("$.[*].timeFrom").value(hasItem(DEFAULT_TIME_FROM)))
            .andExpect(jsonPath("$.[*].timeTo").value(hasItem(DEFAULT_TIME_TO)))
            .andExpect(jsonPath("$.[*].content").value(hasItem(DEFAULT_CONTENT)));
    }
}
