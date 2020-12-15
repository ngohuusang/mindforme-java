package com.mindforme.app.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.elasticsearch.index.query.QueryBuilders.queryStringQuery;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.mindforme.app.MindformeApp;
import com.mindforme.app.domain.SupportedHelpRequest;
import com.mindforme.app.domain.enumeration.SupportedHelpType;
import com.mindforme.app.domain.enumeration.TimeType;
import com.mindforme.app.repository.SupportedHelpRequestRepository;
import com.mindforme.app.repository.search.SupportedHelpRequestSearchRepository;
import com.mindforme.app.service.SupportedHelpRequestService;
import com.mindforme.app.service.dto.SupportedHelpRequestDTO;
import com.mindforme.app.service.mapper.SupportedHelpRequestMapper;
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
 * Integration tests for the {@link SupportedHelpRequestResource} REST controller.
 */
@SpringBootTest(classes = MindformeApp.class)
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
public class SupportedHelpRequestResourceIT {
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

    private static final TimeType DEFAULT_TIME_TYPE = TimeType.HOUR;
    private static final TimeType UPDATED_TIME_TYPE = TimeType.SPECIFIC_TIME;

    private static final SupportedHelpType DEFAULT_SUPPORTED_HELP_TYPE = SupportedHelpType.WITH_ERRAN;
    private static final SupportedHelpType UPDATED_SUPPORTED_HELP_TYPE = SupportedHelpType.AT_HOME;

    private static final String DEFAULT_CONTENT = "AAAAAAAAAA";
    private static final String UPDATED_CONTENT = "BBBBBBBBBB";

    private static final String DEFAULT_OTHER_TASK = "AAAAAAAAAA";
    private static final String UPDATED_OTHER_TASK = "BBBBBBBBBB";

    @Autowired
    private SupportedHelpRequestRepository supportedHelpRequestRepository;

    @Mock
    private SupportedHelpRequestRepository supportedHelpRequestRepositoryMock;

    @Autowired
    private SupportedHelpRequestMapper supportedHelpRequestMapper;

    @Mock
    private SupportedHelpRequestService supportedHelpRequestServiceMock;

    @Autowired
    private SupportedHelpRequestService supportedHelpRequestService;

    /**
     * This repository is mocked in the com.mindforme.app.repository.search test package.
     *
     * @see com.mindforme.app.repository.search.SupportedHelpRequestSearchRepositoryMockConfiguration
     */
    @Autowired
    private SupportedHelpRequestSearchRepository mockSupportedHelpRequestSearchRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restSupportedHelpRequestMockMvc;

    private SupportedHelpRequest supportedHelpRequest;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static SupportedHelpRequest createEntity(EntityManager em) {
        SupportedHelpRequest supportedHelpRequest = new SupportedHelpRequest()
            .totalHelpTime(DEFAULT_TOTAL_HELP_TIME)
            .dateFrom(DEFAULT_DATE_FROM)
            .dateTo(DEFAULT_DATE_TO)
            .timeFrom(DEFAULT_TIME_FROM)
            .timeTo(DEFAULT_TIME_TO)
            .timeType(DEFAULT_TIME_TYPE)
            .supportedHelpType(DEFAULT_SUPPORTED_HELP_TYPE)
            .content(DEFAULT_CONTENT)
            .otherTask(DEFAULT_OTHER_TASK);
        return supportedHelpRequest;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static SupportedHelpRequest createUpdatedEntity(EntityManager em) {
        SupportedHelpRequest supportedHelpRequest = new SupportedHelpRequest()
            .totalHelpTime(UPDATED_TOTAL_HELP_TIME)
            .dateFrom(UPDATED_DATE_FROM)
            .dateTo(UPDATED_DATE_TO)
            .timeFrom(UPDATED_TIME_FROM)
            .timeTo(UPDATED_TIME_TO)
            .timeType(UPDATED_TIME_TYPE)
            .supportedHelpType(UPDATED_SUPPORTED_HELP_TYPE)
            .content(UPDATED_CONTENT)
            .otherTask(UPDATED_OTHER_TASK);
        return supportedHelpRequest;
    }

    @BeforeEach
    public void initTest() {
        supportedHelpRequest = createEntity(em);
    }

    @Test
    @Transactional
    public void createSupportedHelpRequest() throws Exception {
        int databaseSizeBeforeCreate = supportedHelpRequestRepository.findAll().size();
        // Create the SupportedHelpRequest
        SupportedHelpRequestDTO supportedHelpRequestDTO = supportedHelpRequestMapper.toDto(supportedHelpRequest);
        restSupportedHelpRequestMockMvc
            .perform(
                post("/api/supported-help-requests")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(supportedHelpRequestDTO))
            )
            .andExpect(status().isCreated());

        // Validate the SupportedHelpRequest in the database
        List<SupportedHelpRequest> supportedHelpRequestList = supportedHelpRequestRepository.findAll();
        assertThat(supportedHelpRequestList).hasSize(databaseSizeBeforeCreate + 1);
        SupportedHelpRequest testSupportedHelpRequest = supportedHelpRequestList.get(supportedHelpRequestList.size() - 1);
        assertThat(testSupportedHelpRequest.getTotalHelpTime()).isEqualTo(DEFAULT_TOTAL_HELP_TIME);
        assertThat(testSupportedHelpRequest.getDateFrom()).isEqualTo(DEFAULT_DATE_FROM);
        assertThat(testSupportedHelpRequest.getDateTo()).isEqualTo(DEFAULT_DATE_TO);
        assertThat(testSupportedHelpRequest.getTimeFrom()).isEqualTo(DEFAULT_TIME_FROM);
        assertThat(testSupportedHelpRequest.getTimeTo()).isEqualTo(DEFAULT_TIME_TO);
        assertThat(testSupportedHelpRequest.getTimeType()).isEqualTo(DEFAULT_TIME_TYPE);
        assertThat(testSupportedHelpRequest.getSupportedHelpType()).isEqualTo(DEFAULT_SUPPORTED_HELP_TYPE);
        assertThat(testSupportedHelpRequest.getContent()).isEqualTo(DEFAULT_CONTENT);
        assertThat(testSupportedHelpRequest.getOtherTask()).isEqualTo(DEFAULT_OTHER_TASK);

        // Validate the SupportedHelpRequest in Elasticsearch
        verify(mockSupportedHelpRequestSearchRepository, times(1)).save(testSupportedHelpRequest);
    }

    @Test
    @Transactional
    public void createSupportedHelpRequestWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = supportedHelpRequestRepository.findAll().size();

        // Create the SupportedHelpRequest with an existing ID
        supportedHelpRequest.setId(1L);
        SupportedHelpRequestDTO supportedHelpRequestDTO = supportedHelpRequestMapper.toDto(supportedHelpRequest);

        // An entity with an existing ID cannot be created, so this API call must fail
        restSupportedHelpRequestMockMvc
            .perform(
                post("/api/supported-help-requests")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(supportedHelpRequestDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the SupportedHelpRequest in the database
        List<SupportedHelpRequest> supportedHelpRequestList = supportedHelpRequestRepository.findAll();
        assertThat(supportedHelpRequestList).hasSize(databaseSizeBeforeCreate);

        // Validate the SupportedHelpRequest in Elasticsearch
        verify(mockSupportedHelpRequestSearchRepository, times(0)).save(supportedHelpRequest);
    }

    @Test
    @Transactional
    public void getAllSupportedHelpRequests() throws Exception {
        // Initialize the database
        supportedHelpRequestRepository.saveAndFlush(supportedHelpRequest);

        // Get all the supportedHelpRequestList
        restSupportedHelpRequestMockMvc
            .perform(get("/api/supported-help-requests?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(supportedHelpRequest.getId().intValue())))
            .andExpect(jsonPath("$.[*].totalHelpTime").value(hasItem(DEFAULT_TOTAL_HELP_TIME.intValue())))
            .andExpect(jsonPath("$.[*].dateFrom").value(hasItem(DEFAULT_DATE_FROM.toString())))
            .andExpect(jsonPath("$.[*].dateTo").value(hasItem(DEFAULT_DATE_TO.toString())))
            .andExpect(jsonPath("$.[*].timeFrom").value(hasItem(DEFAULT_TIME_FROM)))
            .andExpect(jsonPath("$.[*].timeTo").value(hasItem(DEFAULT_TIME_TO)))
            .andExpect(jsonPath("$.[*].timeType").value(hasItem(DEFAULT_TIME_TYPE.toString())))
            .andExpect(jsonPath("$.[*].supportedHelpType").value(hasItem(DEFAULT_SUPPORTED_HELP_TYPE.toString())))
            .andExpect(jsonPath("$.[*].content").value(hasItem(DEFAULT_CONTENT)))
            .andExpect(jsonPath("$.[*].otherTask").value(hasItem(DEFAULT_OTHER_TASK)));
    }

    @SuppressWarnings({ "unchecked" })
    public void getAllSupportedHelpRequestsWithEagerRelationshipsIsEnabled() throws Exception {
        when(supportedHelpRequestServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restSupportedHelpRequestMockMvc.perform(get("/api/supported-help-requests?eagerload=true")).andExpect(status().isOk());

        verify(supportedHelpRequestServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({ "unchecked" })
    public void getAllSupportedHelpRequestsWithEagerRelationshipsIsNotEnabled() throws Exception {
        when(supportedHelpRequestServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restSupportedHelpRequestMockMvc.perform(get("/api/supported-help-requests?eagerload=true")).andExpect(status().isOk());

        verify(supportedHelpRequestServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @Test
    @Transactional
    public void getSupportedHelpRequest() throws Exception {
        // Initialize the database
        supportedHelpRequestRepository.saveAndFlush(supportedHelpRequest);

        // Get the supportedHelpRequest
        restSupportedHelpRequestMockMvc
            .perform(get("/api/supported-help-requests/{id}", supportedHelpRequest.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(supportedHelpRequest.getId().intValue()))
            .andExpect(jsonPath("$.totalHelpTime").value(DEFAULT_TOTAL_HELP_TIME.intValue()))
            .andExpect(jsonPath("$.dateFrom").value(DEFAULT_DATE_FROM.toString()))
            .andExpect(jsonPath("$.dateTo").value(DEFAULT_DATE_TO.toString()))
            .andExpect(jsonPath("$.timeFrom").value(DEFAULT_TIME_FROM))
            .andExpect(jsonPath("$.timeTo").value(DEFAULT_TIME_TO))
            .andExpect(jsonPath("$.timeType").value(DEFAULT_TIME_TYPE.toString()))
            .andExpect(jsonPath("$.supportedHelpType").value(DEFAULT_SUPPORTED_HELP_TYPE.toString()))
            .andExpect(jsonPath("$.content").value(DEFAULT_CONTENT))
            .andExpect(jsonPath("$.otherTask").value(DEFAULT_OTHER_TASK));
    }

    @Test
    @Transactional
    public void getNonExistingSupportedHelpRequest() throws Exception {
        // Get the supportedHelpRequest
        restSupportedHelpRequestMockMvc.perform(get("/api/supported-help-requests/{id}", Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateSupportedHelpRequest() throws Exception {
        // Initialize the database
        supportedHelpRequestRepository.saveAndFlush(supportedHelpRequest);

        int databaseSizeBeforeUpdate = supportedHelpRequestRepository.findAll().size();

        // Update the supportedHelpRequest
        SupportedHelpRequest updatedSupportedHelpRequest = supportedHelpRequestRepository.findById(supportedHelpRequest.getId()).get();
        // Disconnect from session so that the updates on updatedSupportedHelpRequest are not directly saved in db
        em.detach(updatedSupportedHelpRequest);
        updatedSupportedHelpRequest
            .totalHelpTime(UPDATED_TOTAL_HELP_TIME)
            .dateFrom(UPDATED_DATE_FROM)
            .dateTo(UPDATED_DATE_TO)
            .timeFrom(UPDATED_TIME_FROM)
            .timeTo(UPDATED_TIME_TO)
            .timeType(UPDATED_TIME_TYPE)
            .supportedHelpType(UPDATED_SUPPORTED_HELP_TYPE)
            .content(UPDATED_CONTENT)
            .otherTask(UPDATED_OTHER_TASK);
        SupportedHelpRequestDTO supportedHelpRequestDTO = supportedHelpRequestMapper.toDto(updatedSupportedHelpRequest);

        restSupportedHelpRequestMockMvc
            .perform(
                put("/api/supported-help-requests")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(supportedHelpRequestDTO))
            )
            .andExpect(status().isOk());

        // Validate the SupportedHelpRequest in the database
        List<SupportedHelpRequest> supportedHelpRequestList = supportedHelpRequestRepository.findAll();
        assertThat(supportedHelpRequestList).hasSize(databaseSizeBeforeUpdate);
        SupportedHelpRequest testSupportedHelpRequest = supportedHelpRequestList.get(supportedHelpRequestList.size() - 1);
        assertThat(testSupportedHelpRequest.getTotalHelpTime()).isEqualTo(UPDATED_TOTAL_HELP_TIME);
        assertThat(testSupportedHelpRequest.getDateFrom()).isEqualTo(UPDATED_DATE_FROM);
        assertThat(testSupportedHelpRequest.getDateTo()).isEqualTo(UPDATED_DATE_TO);
        assertThat(testSupportedHelpRequest.getTimeFrom()).isEqualTo(UPDATED_TIME_FROM);
        assertThat(testSupportedHelpRequest.getTimeTo()).isEqualTo(UPDATED_TIME_TO);
        assertThat(testSupportedHelpRequest.getTimeType()).isEqualTo(UPDATED_TIME_TYPE);
        assertThat(testSupportedHelpRequest.getSupportedHelpType()).isEqualTo(UPDATED_SUPPORTED_HELP_TYPE);
        assertThat(testSupportedHelpRequest.getContent()).isEqualTo(UPDATED_CONTENT);
        assertThat(testSupportedHelpRequest.getOtherTask()).isEqualTo(UPDATED_OTHER_TASK);

        // Validate the SupportedHelpRequest in Elasticsearch
        verify(mockSupportedHelpRequestSearchRepository, times(1)).save(testSupportedHelpRequest);
    }

    @Test
    @Transactional
    public void updateNonExistingSupportedHelpRequest() throws Exception {
        int databaseSizeBeforeUpdate = supportedHelpRequestRepository.findAll().size();

        // Create the SupportedHelpRequest
        SupportedHelpRequestDTO supportedHelpRequestDTO = supportedHelpRequestMapper.toDto(supportedHelpRequest);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSupportedHelpRequestMockMvc
            .perform(
                put("/api/supported-help-requests")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(supportedHelpRequestDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the SupportedHelpRequest in the database
        List<SupportedHelpRequest> supportedHelpRequestList = supportedHelpRequestRepository.findAll();
        assertThat(supportedHelpRequestList).hasSize(databaseSizeBeforeUpdate);

        // Validate the SupportedHelpRequest in Elasticsearch
        verify(mockSupportedHelpRequestSearchRepository, times(0)).save(supportedHelpRequest);
    }

    @Test
    @Transactional
    public void deleteSupportedHelpRequest() throws Exception {
        // Initialize the database
        supportedHelpRequestRepository.saveAndFlush(supportedHelpRequest);

        int databaseSizeBeforeDelete = supportedHelpRequestRepository.findAll().size();

        // Delete the supportedHelpRequest
        restSupportedHelpRequestMockMvc
            .perform(delete("/api/supported-help-requests/{id}", supportedHelpRequest.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<SupportedHelpRequest> supportedHelpRequestList = supportedHelpRequestRepository.findAll();
        assertThat(supportedHelpRequestList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the SupportedHelpRequest in Elasticsearch
        verify(mockSupportedHelpRequestSearchRepository, times(1)).deleteById(supportedHelpRequest.getId());
    }

    @Test
    @Transactional
    public void searchSupportedHelpRequest() throws Exception {
        // Configure the mock search repository
        // Initialize the database
        supportedHelpRequestRepository.saveAndFlush(supportedHelpRequest);
        when(mockSupportedHelpRequestSearchRepository.search(queryStringQuery("id:" + supportedHelpRequest.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(supportedHelpRequest), PageRequest.of(0, 1), 1));

        // Search the supportedHelpRequest
        restSupportedHelpRequestMockMvc
            .perform(get("/api/_search/supported-help-requests?query=id:" + supportedHelpRequest.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(supportedHelpRequest.getId().intValue())))
            .andExpect(jsonPath("$.[*].totalHelpTime").value(hasItem(DEFAULT_TOTAL_HELP_TIME.intValue())))
            .andExpect(jsonPath("$.[*].dateFrom").value(hasItem(DEFAULT_DATE_FROM.toString())))
            .andExpect(jsonPath("$.[*].dateTo").value(hasItem(DEFAULT_DATE_TO.toString())))
            .andExpect(jsonPath("$.[*].timeFrom").value(hasItem(DEFAULT_TIME_FROM)))
            .andExpect(jsonPath("$.[*].timeTo").value(hasItem(DEFAULT_TIME_TO)))
            .andExpect(jsonPath("$.[*].timeType").value(hasItem(DEFAULT_TIME_TYPE.toString())))
            .andExpect(jsonPath("$.[*].supportedHelpType").value(hasItem(DEFAULT_SUPPORTED_HELP_TYPE.toString())))
            .andExpect(jsonPath("$.[*].content").value(hasItem(DEFAULT_CONTENT)))
            .andExpect(jsonPath("$.[*].otherTask").value(hasItem(DEFAULT_OTHER_TASK)));
    }
}
