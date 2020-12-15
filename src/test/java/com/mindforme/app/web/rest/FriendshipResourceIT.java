package com.mindforme.app.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.elasticsearch.index.query.QueryBuilders.queryStringQuery;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.mindforme.app.MindformeApp;
import com.mindforme.app.domain.Friendship;
import com.mindforme.app.repository.FriendshipRepository;
import com.mindforme.app.repository.search.FriendshipSearchRepository;
import com.mindforme.app.service.FriendshipService;
import com.mindforme.app.service.dto.FriendshipDTO;
import com.mindforme.app.service.mapper.FriendshipMapper;
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
 * Integration tests for the {@link FriendshipResource} REST controller.
 */
@SpringBootTest(classes = MindformeApp.class)
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
public class FriendshipResourceIT {
    @Autowired
    private FriendshipRepository friendshipRepository;

    @Autowired
    private FriendshipMapper friendshipMapper;

    @Autowired
    private FriendshipService friendshipService;

    /**
     * This repository is mocked in the com.mindforme.app.repository.search test package.
     *
     * @see com.mindforme.app.repository.search.FriendshipSearchRepositoryMockConfiguration
     */
    @Autowired
    private FriendshipSearchRepository mockFriendshipSearchRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restFriendshipMockMvc;

    private Friendship friendship;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Friendship createEntity(EntityManager em) {
        Friendship friendship = new Friendship();
        return friendship;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Friendship createUpdatedEntity(EntityManager em) {
        Friendship friendship = new Friendship();
        return friendship;
    }

    @BeforeEach
    public void initTest() {
        friendship = createEntity(em);
    }

    @Test
    @Transactional
    public void createFriendship() throws Exception {
        int databaseSizeBeforeCreate = friendshipRepository.findAll().size();
        // Create the Friendship
        FriendshipDTO friendshipDTO = friendshipMapper.toDto(friendship);
        restFriendshipMockMvc
            .perform(
                post("/api/friendships").contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(friendshipDTO))
            )
            .andExpect(status().isCreated());

        // Validate the Friendship in the database
        List<Friendship> friendshipList = friendshipRepository.findAll();
        assertThat(friendshipList).hasSize(databaseSizeBeforeCreate + 1);
        Friendship testFriendship = friendshipList.get(friendshipList.size() - 1);

        // Validate the Friendship in Elasticsearch
        verify(mockFriendshipSearchRepository, times(1)).save(testFriendship);
    }

    @Test
    @Transactional
    public void createFriendshipWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = friendshipRepository.findAll().size();

        // Create the Friendship with an existing ID
        friendship.setId(1L);
        FriendshipDTO friendshipDTO = friendshipMapper.toDto(friendship);

        // An entity with an existing ID cannot be created, so this API call must fail
        restFriendshipMockMvc
            .perform(
                post("/api/friendships").contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(friendshipDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Friendship in the database
        List<Friendship> friendshipList = friendshipRepository.findAll();
        assertThat(friendshipList).hasSize(databaseSizeBeforeCreate);

        // Validate the Friendship in Elasticsearch
        verify(mockFriendshipSearchRepository, times(0)).save(friendship);
    }

    @Test
    @Transactional
    public void getAllFriendships() throws Exception {
        // Initialize the database
        friendshipRepository.saveAndFlush(friendship);

        // Get all the friendshipList
        restFriendshipMockMvc
            .perform(get("/api/friendships?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(friendship.getId().intValue())));
    }

    @Test
    @Transactional
    public void getFriendship() throws Exception {
        // Initialize the database
        friendshipRepository.saveAndFlush(friendship);

        // Get the friendship
        restFriendshipMockMvc
            .perform(get("/api/friendships/{id}", friendship.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(friendship.getId().intValue()));
    }

    @Test
    @Transactional
    public void getNonExistingFriendship() throws Exception {
        // Get the friendship
        restFriendshipMockMvc.perform(get("/api/friendships/{id}", Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateFriendship() throws Exception {
        // Initialize the database
        friendshipRepository.saveAndFlush(friendship);

        int databaseSizeBeforeUpdate = friendshipRepository.findAll().size();

        // Update the friendship
        Friendship updatedFriendship = friendshipRepository.findById(friendship.getId()).get();
        // Disconnect from session so that the updates on updatedFriendship are not directly saved in db
        em.detach(updatedFriendship);
        FriendshipDTO friendshipDTO = friendshipMapper.toDto(updatedFriendship);

        restFriendshipMockMvc
            .perform(
                put("/api/friendships").contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(friendshipDTO))
            )
            .andExpect(status().isOk());

        // Validate the Friendship in the database
        List<Friendship> friendshipList = friendshipRepository.findAll();
        assertThat(friendshipList).hasSize(databaseSizeBeforeUpdate);
        Friendship testFriendship = friendshipList.get(friendshipList.size() - 1);

        // Validate the Friendship in Elasticsearch
        verify(mockFriendshipSearchRepository, times(1)).save(testFriendship);
    }

    @Test
    @Transactional
    public void updateNonExistingFriendship() throws Exception {
        int databaseSizeBeforeUpdate = friendshipRepository.findAll().size();

        // Create the Friendship
        FriendshipDTO friendshipDTO = friendshipMapper.toDto(friendship);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restFriendshipMockMvc
            .perform(
                put("/api/friendships").contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(friendshipDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Friendship in the database
        List<Friendship> friendshipList = friendshipRepository.findAll();
        assertThat(friendshipList).hasSize(databaseSizeBeforeUpdate);

        // Validate the Friendship in Elasticsearch
        verify(mockFriendshipSearchRepository, times(0)).save(friendship);
    }

    @Test
    @Transactional
    public void deleteFriendship() throws Exception {
        // Initialize the database
        friendshipRepository.saveAndFlush(friendship);

        int databaseSizeBeforeDelete = friendshipRepository.findAll().size();

        // Delete the friendship
        restFriendshipMockMvc
            .perform(delete("/api/friendships/{id}", friendship.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Friendship> friendshipList = friendshipRepository.findAll();
        assertThat(friendshipList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the Friendship in Elasticsearch
        verify(mockFriendshipSearchRepository, times(1)).deleteById(friendship.getId());
    }

    @Test
    @Transactional
    public void searchFriendship() throws Exception {
        // Configure the mock search repository
        // Initialize the database
        friendshipRepository.saveAndFlush(friendship);
        when(mockFriendshipSearchRepository.search(queryStringQuery("id:" + friendship.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(friendship), PageRequest.of(0, 1), 1));

        // Search the friendship
        restFriendshipMockMvc
            .perform(get("/api/_search/friendships?query=id:" + friendship.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(friendship.getId().intValue())));
    }
}
