package com.mindforme.app.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.elasticsearch.index.query.QueryBuilders.queryStringQuery;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.mindforme.app.MindformeApp;
import com.mindforme.app.domain.FriendRequest;
import com.mindforme.app.domain.enumeration.FriendRequestStatus;
import com.mindforme.app.repository.FriendRequestRepository;
import com.mindforme.app.repository.search.FriendRequestSearchRepository;
import com.mindforme.app.service.FriendRequestService;
import com.mindforme.app.service.dto.FriendRequestDTO;
import com.mindforme.app.service.mapper.FriendRequestMapper;
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
 * Integration tests for the {@link FriendRequestResource} REST controller.
 */
@SpringBootTest(classes = MindformeApp.class)
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
public class FriendRequestResourceIT {
    private static final FriendRequestStatus DEFAULT_STATUS = FriendRequestStatus.REQUESTED;
    private static final FriendRequestStatus UPDATED_STATUS = FriendRequestStatus.ACCEPTED;

    @Autowired
    private FriendRequestRepository friendRequestRepository;

    @Autowired
    private FriendRequestMapper friendRequestMapper;

    @Autowired
    private FriendRequestService friendRequestService;

    /**
     * This repository is mocked in the com.mindforme.app.repository.search test package.
     *
     * @see com.mindforme.app.repository.search.FriendRequestSearchRepositoryMockConfiguration
     */
    @Autowired
    private FriendRequestSearchRepository mockFriendRequestSearchRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restFriendRequestMockMvc;

    private FriendRequest friendRequest;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static FriendRequest createEntity(EntityManager em) {
        FriendRequest friendRequest = new FriendRequest().status(DEFAULT_STATUS);
        return friendRequest;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static FriendRequest createUpdatedEntity(EntityManager em) {
        FriendRequest friendRequest = new FriendRequest().status(UPDATED_STATUS);
        return friendRequest;
    }

    @BeforeEach
    public void initTest() {
        friendRequest = createEntity(em);
    }

    @Test
    @Transactional
    public void createFriendRequest() throws Exception {
        int databaseSizeBeforeCreate = friendRequestRepository.findAll().size();
        // Create the FriendRequest
        FriendRequestDTO friendRequestDTO = friendRequestMapper.toDto(friendRequest);
        restFriendRequestMockMvc
            .perform(
                post("/api/friend-requests")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(friendRequestDTO))
            )
            .andExpect(status().isCreated());

        // Validate the FriendRequest in the database
        List<FriendRequest> friendRequestList = friendRequestRepository.findAll();
        assertThat(friendRequestList).hasSize(databaseSizeBeforeCreate + 1);
        FriendRequest testFriendRequest = friendRequestList.get(friendRequestList.size() - 1);
        assertThat(testFriendRequest.getStatus()).isEqualTo(DEFAULT_STATUS);

        // Validate the FriendRequest in Elasticsearch
        verify(mockFriendRequestSearchRepository, times(1)).save(testFriendRequest);
    }

    @Test
    @Transactional
    public void createFriendRequestWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = friendRequestRepository.findAll().size();

        // Create the FriendRequest with an existing ID
        friendRequest.setId(1L);
        FriendRequestDTO friendRequestDTO = friendRequestMapper.toDto(friendRequest);

        // An entity with an existing ID cannot be created, so this API call must fail
        restFriendRequestMockMvc
            .perform(
                post("/api/friend-requests")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(friendRequestDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the FriendRequest in the database
        List<FriendRequest> friendRequestList = friendRequestRepository.findAll();
        assertThat(friendRequestList).hasSize(databaseSizeBeforeCreate);

        // Validate the FriendRequest in Elasticsearch
        verify(mockFriendRequestSearchRepository, times(0)).save(friendRequest);
    }

    @Test
    @Transactional
    public void getAllFriendRequests() throws Exception {
        // Initialize the database
        friendRequestRepository.saveAndFlush(friendRequest);

        // Get all the friendRequestList
        restFriendRequestMockMvc
            .perform(get("/api/friend-requests?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(friendRequest.getId().intValue())))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.toString())));
    }

    @Test
    @Transactional
    public void getFriendRequest() throws Exception {
        // Initialize the database
        friendRequestRepository.saveAndFlush(friendRequest);

        // Get the friendRequest
        restFriendRequestMockMvc
            .perform(get("/api/friend-requests/{id}", friendRequest.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(friendRequest.getId().intValue()))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingFriendRequest() throws Exception {
        // Get the friendRequest
        restFriendRequestMockMvc.perform(get("/api/friend-requests/{id}", Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateFriendRequest() throws Exception {
        // Initialize the database
        friendRequestRepository.saveAndFlush(friendRequest);

        int databaseSizeBeforeUpdate = friendRequestRepository.findAll().size();

        // Update the friendRequest
        FriendRequest updatedFriendRequest = friendRequestRepository.findById(friendRequest.getId()).get();
        // Disconnect from session so that the updates on updatedFriendRequest are not directly saved in db
        em.detach(updatedFriendRequest);
        updatedFriendRequest.status(UPDATED_STATUS);
        FriendRequestDTO friendRequestDTO = friendRequestMapper.toDto(updatedFriendRequest);

        restFriendRequestMockMvc
            .perform(
                put("/api/friend-requests")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(friendRequestDTO))
            )
            .andExpect(status().isOk());

        // Validate the FriendRequest in the database
        List<FriendRequest> friendRequestList = friendRequestRepository.findAll();
        assertThat(friendRequestList).hasSize(databaseSizeBeforeUpdate);
        FriendRequest testFriendRequest = friendRequestList.get(friendRequestList.size() - 1);
        assertThat(testFriendRequest.getStatus()).isEqualTo(UPDATED_STATUS);

        // Validate the FriendRequest in Elasticsearch
        verify(mockFriendRequestSearchRepository, times(1)).save(testFriendRequest);
    }

    @Test
    @Transactional
    public void updateNonExistingFriendRequest() throws Exception {
        int databaseSizeBeforeUpdate = friendRequestRepository.findAll().size();

        // Create the FriendRequest
        FriendRequestDTO friendRequestDTO = friendRequestMapper.toDto(friendRequest);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restFriendRequestMockMvc
            .perform(
                put("/api/friend-requests")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(friendRequestDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the FriendRequest in the database
        List<FriendRequest> friendRequestList = friendRequestRepository.findAll();
        assertThat(friendRequestList).hasSize(databaseSizeBeforeUpdate);

        // Validate the FriendRequest in Elasticsearch
        verify(mockFriendRequestSearchRepository, times(0)).save(friendRequest);
    }

    @Test
    @Transactional
    public void deleteFriendRequest() throws Exception {
        // Initialize the database
        friendRequestRepository.saveAndFlush(friendRequest);

        int databaseSizeBeforeDelete = friendRequestRepository.findAll().size();

        // Delete the friendRequest
        restFriendRequestMockMvc
            .perform(delete("/api/friend-requests/{id}", friendRequest.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<FriendRequest> friendRequestList = friendRequestRepository.findAll();
        assertThat(friendRequestList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the FriendRequest in Elasticsearch
        verify(mockFriendRequestSearchRepository, times(1)).deleteById(friendRequest.getId());
    }

    @Test
    @Transactional
    public void searchFriendRequest() throws Exception {
        // Configure the mock search repository
        // Initialize the database
        friendRequestRepository.saveAndFlush(friendRequest);
        when(mockFriendRequestSearchRepository.search(queryStringQuery("id:" + friendRequest.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(friendRequest), PageRequest.of(0, 1), 1));

        // Search the friendRequest
        restFriendRequestMockMvc
            .perform(get("/api/_search/friend-requests?query=id:" + friendRequest.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(friendRequest.getId().intValue())))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.toString())));
    }
}
