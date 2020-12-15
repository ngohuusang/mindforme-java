package com.mindforme.app.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.elasticsearch.index.query.QueryBuilders.queryStringQuery;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.mindforme.app.MindformeApp;
import com.mindforme.app.domain.ChildHelpRequest;
import com.mindforme.app.domain.enumeration.TimeType;
import com.mindforme.app.repository.ChildHelpRequestRepository;
import com.mindforme.app.repository.search.ChildHelpRequestSearchRepository;
import com.mindforme.app.service.ChildHelpRequestService;
import com.mindforme.app.service.dto.ChildHelpRequestDTO;
import com.mindforme.app.service.mapper.ChildHelpRequestMapper;
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
 * Integration tests for the {@link ChildHelpRequestResource} REST controller.
 */
@SpringBootTest(classes = MindformeApp.class)
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
public class ChildHelpRequestResourceIT {
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

    private static final String DEFAULT_CONTENT = "AAAAAAAAAA";
    private static final String UPDATED_CONTENT = "BBBBBBBBBB";

    private static final String DEFAULT_OTHER_TASK = "AAAAAAAAAA";
    private static final String UPDATED_OTHER_TASK = "BBBBBBBBBB";

    @Autowired
    private ChildHelpRequestRepository childHelpRequestRepository;

    @Mock
    private ChildHelpRequestRepository childHelpRequestRepositoryMock;

    @Autowired
    private ChildHelpRequestMapper childHelpRequestMapper;

    @Mock
    private ChildHelpRequestService childHelpRequestServiceMock;

    @Autowired
    private ChildHelpRequestService childHelpRequestService;

    /**
     * This repository is mocked in the com.mindforme.app.repository.search test package.
     *
     * @see com.mindforme.app.repository.search.ChildHelpRequestSearchRepositoryMockConfiguration
     */
    @Autowired
    private ChildHelpRequestSearchRepository mockChildHelpRequestSearchRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restChildHelpRequestMockMvc;

    private ChildHelpRequest childHelpRequest;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ChildHelpRequest createEntity(EntityManager em) {
        ChildHelpRequest childHelpRequest = new ChildHelpRequest()
            .totalHelpTime(DEFAULT_TOTAL_HELP_TIME)
            .dateFrom(DEFAULT_DATE_FROM)
            .dateTo(DEFAULT_DATE_TO)
            .timeFrom(DEFAULT_TIME_FROM)
            .timeTo(DEFAULT_TIME_TO)
            .timeType(DEFAULT_TIME_TYPE)
            .content(DEFAULT_CONTENT)
            .otherTask(DEFAULT_OTHER_TASK);
        return childHelpRequest;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ChildHelpRequest createUpdatedEntity(EntityManager em) {
        ChildHelpRequest childHelpRequest = new ChildHelpRequest()
            .totalHelpTime(UPDATED_TOTAL_HELP_TIME)
            .dateFrom(UPDATED_DATE_FROM)
            .dateTo(UPDATED_DATE_TO)
            .timeFrom(UPDATED_TIME_FROM)
            .timeTo(UPDATED_TIME_TO)
            .timeType(UPDATED_TIME_TYPE)
            .content(UPDATED_CONTENT)
            .otherTask(UPDATED_OTHER_TASK);
        return childHelpRequest;
    }

    @BeforeEach
    public void initTest() {
        childHelpRequest = createEntity(em);
    }

    @Test
    @Transactional
    public void createChildHelpRequest() throws Exception {
        int databaseSizeBeforeCreate = childHelpRequestRepository.findAll().size();
        // Create the ChildHelpRequest
        ChildHelpRequestDTO childHelpRequestDTO = childHelpRequestMapper.toDto(childHelpRequest);
        restChildHelpRequestMockMvc
            .perform(
                post("/api/child-help-requests")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(childHelpRequestDTO))
            )
            .andExpect(status().isCreated());

        // Validate the ChildHelpRequest in the database
        List<ChildHelpRequest> childHelpRequestList = childHelpRequestRepository.findAll();
        assertThat(childHelpRequestList).hasSize(databaseSizeBeforeCreate + 1);
        ChildHelpRequest testChildHelpRequest = childHelpRequestList.get(childHelpRequestList.size() - 1);
        assertThat(testChildHelpRequest.getTotalHelpTime()).isEqualTo(DEFAULT_TOTAL_HELP_TIME);
        assertThat(testChildHelpRequest.getDateFrom()).isEqualTo(DEFAULT_DATE_FROM);
        assertThat(testChildHelpRequest.getDateTo()).isEqualTo(DEFAULT_DATE_TO);
        assertThat(testChildHelpRequest.getTimeFrom()).isEqualTo(DEFAULT_TIME_FROM);
        assertThat(testChildHelpRequest.getTimeTo()).isEqualTo(DEFAULT_TIME_TO);
        assertThat(testChildHelpRequest.getTimeType()).isEqualTo(DEFAULT_TIME_TYPE);
        assertThat(testChildHelpRequest.getContent()).isEqualTo(DEFAULT_CONTENT);
        assertThat(testChildHelpRequest.getOtherTask()).isEqualTo(DEFAULT_OTHER_TASK);

        // Validate the ChildHelpRequest in Elasticsearch
        verify(mockChildHelpRequestSearchRepository, times(1)).save(testChildHelpRequest);
    }

    @Test
    @Transactional
    public void createChildHelpRequestWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = childHelpRequestRepository.findAll().size();

        // Create the ChildHelpRequest with an existing ID
        childHelpRequest.setId(1L);
        ChildHelpRequestDTO childHelpRequestDTO = childHelpRequestMapper.toDto(childHelpRequest);

        // An entity with an existing ID cannot be created, so this API call must fail
        restChildHelpRequestMockMvc
            .perform(
                post("/api/child-help-requests")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(childHelpRequestDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ChildHelpRequest in the database
        List<ChildHelpRequest> childHelpRequestList = childHelpRequestRepository.findAll();
        assertThat(childHelpRequestList).hasSize(databaseSizeBeforeCreate);

        // Validate the ChildHelpRequest in Elasticsearch
        verify(mockChildHelpRequestSearchRepository, times(0)).save(childHelpRequest);
    }

    @Test
    @Transactional
    public void getAllChildHelpRequests() throws Exception {
        // Initialize the database
        childHelpRequestRepository.saveAndFlush(childHelpRequest);

        // Get all the childHelpRequestList
        restChildHelpRequestMockMvc
            .perform(get("/api/child-help-requests?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(childHelpRequest.getId().intValue())))
            .andExpect(jsonPath("$.[*].totalHelpTime").value(hasItem(DEFAULT_TOTAL_HELP_TIME.intValue())))
            .andExpect(jsonPath("$.[*].dateFrom").value(hasItem(DEFAULT_DATE_FROM.toString())))
            .andExpect(jsonPath("$.[*].dateTo").value(hasItem(DEFAULT_DATE_TO.toString())))
            .andExpect(jsonPath("$.[*].timeFrom").value(hasItem(DEFAULT_TIME_FROM)))
            .andExpect(jsonPath("$.[*].timeTo").value(hasItem(DEFAULT_TIME_TO)))
            .andExpect(jsonPath("$.[*].timeType").value(hasItem(DEFAULT_TIME_TYPE.toString())))
            .andExpect(jsonPath("$.[*].content").value(hasItem(DEFAULT_CONTENT)))
            .andExpect(jsonPath("$.[*].otherTask").value(hasItem(DEFAULT_OTHER_TASK)));
    }

    @SuppressWarnings({ "unchecked" })
    public void getAllChildHelpRequestsWithEagerRelationshipsIsEnabled() throws Exception {
        when(childHelpRequestServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restChildHelpRequestMockMvc.perform(get("/api/child-help-requests?eagerload=true")).andExpect(status().isOk());

        verify(childHelpRequestServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({ "unchecked" })
    public void getAllChildHelpRequestsWithEagerRelationshipsIsNotEnabled() throws Exception {
        when(childHelpRequestServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restChildHelpRequestMockMvc.perform(get("/api/child-help-requests?eagerload=true")).andExpect(status().isOk());

        verify(childHelpRequestServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @Test
    @Transactional
    public void getChildHelpRequest() throws Exception {
        // Initialize the database
        childHelpRequestRepository.saveAndFlush(childHelpRequest);

        // Get the childHelpRequest
        restChildHelpRequestMockMvc
            .perform(get("/api/child-help-requests/{id}", childHelpRequest.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(childHelpRequest.getId().intValue()))
            .andExpect(jsonPath("$.totalHelpTime").value(DEFAULT_TOTAL_HELP_TIME.intValue()))
            .andExpect(jsonPath("$.dateFrom").value(DEFAULT_DATE_FROM.toString()))
            .andExpect(jsonPath("$.dateTo").value(DEFAULT_DATE_TO.toString()))
            .andExpect(jsonPath("$.timeFrom").value(DEFAULT_TIME_FROM))
            .andExpect(jsonPath("$.timeTo").value(DEFAULT_TIME_TO))
            .andExpect(jsonPath("$.timeType").value(DEFAULT_TIME_TYPE.toString()))
            .andExpect(jsonPath("$.content").value(DEFAULT_CONTENT))
            .andExpect(jsonPath("$.otherTask").value(DEFAULT_OTHER_TASK));
    }

    @Test
    @Transactional
    public void getNonExistingChildHelpRequest() throws Exception {
        // Get the childHelpRequest
        restChildHelpRequestMockMvc.perform(get("/api/child-help-requests/{id}", Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateChildHelpRequest() throws Exception {
        // Initialize the database
        childHelpRequestRepository.saveAndFlush(childHelpRequest);

        int databaseSizeBeforeUpdate = childHelpRequestRepository.findAll().size();

        // Update the childHelpRequest
        ChildHelpRequest updatedChildHelpRequest = childHelpRequestRepository.findById(childHelpRequest.getId()).get();
        // Disconnect from session so that the updates on updatedChildHelpRequest are not directly saved in db
        em.detach(updatedChildHelpRequest);
        updatedChildHelpRequest
            .totalHelpTime(UPDATED_TOTAL_HELP_TIME)
            .dateFrom(UPDATED_DATE_FROM)
            .dateTo(UPDATED_DATE_TO)
            .timeFrom(UPDATED_TIME_FROM)
            .timeTo(UPDATED_TIME_TO)
            .timeType(UPDATED_TIME_TYPE)
            .content(UPDATED_CONTENT)
            .otherTask(UPDATED_OTHER_TASK);
        ChildHelpRequestDTO childHelpRequestDTO = childHelpRequestMapper.toDto(updatedChildHelpRequest);

        restChildHelpRequestMockMvc
            .perform(
                put("/api/child-help-requests")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(childHelpRequestDTO))
            )
            .andExpect(status().isOk());

        // Validate the ChildHelpRequest in the database
        List<ChildHelpRequest> childHelpRequestList = childHelpRequestRepository.findAll();
        assertThat(childHelpRequestList).hasSize(databaseSizeBeforeUpdate);
        ChildHelpRequest testChildHelpRequest = childHelpRequestList.get(childHelpRequestList.size() - 1);
        assertThat(testChildHelpRequest.getTotalHelpTime()).isEqualTo(UPDATED_TOTAL_HELP_TIME);
        assertThat(testChildHelpRequest.getDateFrom()).isEqualTo(UPDATED_DATE_FROM);
        assertThat(testChildHelpRequest.getDateTo()).isEqualTo(UPDATED_DATE_TO);
        assertThat(testChildHelpRequest.getTimeFrom()).isEqualTo(UPDATED_TIME_FROM);
        assertThat(testChildHelpRequest.getTimeTo()).isEqualTo(UPDATED_TIME_TO);
        assertThat(testChildHelpRequest.getTimeType()).isEqualTo(UPDATED_TIME_TYPE);
        assertThat(testChildHelpRequest.getContent()).isEqualTo(UPDATED_CONTENT);
        assertThat(testChildHelpRequest.getOtherTask()).isEqualTo(UPDATED_OTHER_TASK);

        // Validate the ChildHelpRequest in Elasticsearch
        verify(mockChildHelpRequestSearchRepository, times(1)).save(testChildHelpRequest);
    }

    @Test
    @Transactional
    public void updateNonExistingChildHelpRequest() throws Exception {
        int databaseSizeBeforeUpdate = childHelpRequestRepository.findAll().size();

        // Create the ChildHelpRequest
        ChildHelpRequestDTO childHelpRequestDTO = childHelpRequestMapper.toDto(childHelpRequest);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restChildHelpRequestMockMvc
            .perform(
                put("/api/child-help-requests")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(childHelpRequestDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ChildHelpRequest in the database
        List<ChildHelpRequest> childHelpRequestList = childHelpRequestRepository.findAll();
        assertThat(childHelpRequestList).hasSize(databaseSizeBeforeUpdate);

        // Validate the ChildHelpRequest in Elasticsearch
        verify(mockChildHelpRequestSearchRepository, times(0)).save(childHelpRequest);
    }

    @Test
    @Transactional
    public void deleteChildHelpRequest() throws Exception {
        // Initialize the database
        childHelpRequestRepository.saveAndFlush(childHelpRequest);

        int databaseSizeBeforeDelete = childHelpRequestRepository.findAll().size();

        // Delete the childHelpRequest
        restChildHelpRequestMockMvc
            .perform(delete("/api/child-help-requests/{id}", childHelpRequest.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<ChildHelpRequest> childHelpRequestList = childHelpRequestRepository.findAll();
        assertThat(childHelpRequestList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the ChildHelpRequest in Elasticsearch
        verify(mockChildHelpRequestSearchRepository, times(1)).deleteById(childHelpRequest.getId());
    }

    @Test
    @Transactional
    public void searchChildHelpRequest() throws Exception {
        // Configure the mock search repository
        // Initialize the database
        childHelpRequestRepository.saveAndFlush(childHelpRequest);
        when(mockChildHelpRequestSearchRepository.search(queryStringQuery("id:" + childHelpRequest.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(childHelpRequest), PageRequest.of(0, 1), 1));

        // Search the childHelpRequest
        restChildHelpRequestMockMvc
            .perform(get("/api/_search/child-help-requests?query=id:" + childHelpRequest.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(childHelpRequest.getId().intValue())))
            .andExpect(jsonPath("$.[*].totalHelpTime").value(hasItem(DEFAULT_TOTAL_HELP_TIME.intValue())))
            .andExpect(jsonPath("$.[*].dateFrom").value(hasItem(DEFAULT_DATE_FROM.toString())))
            .andExpect(jsonPath("$.[*].dateTo").value(hasItem(DEFAULT_DATE_TO.toString())))
            .andExpect(jsonPath("$.[*].timeFrom").value(hasItem(DEFAULT_TIME_FROM)))
            .andExpect(jsonPath("$.[*].timeTo").value(hasItem(DEFAULT_TIME_TO)))
            .andExpect(jsonPath("$.[*].timeType").value(hasItem(DEFAULT_TIME_TYPE.toString())))
            .andExpect(jsonPath("$.[*].content").value(hasItem(DEFAULT_CONTENT)))
            .andExpect(jsonPath("$.[*].otherTask").value(hasItem(DEFAULT_OTHER_TASK)));
    }
}
