package com.mindforme.app.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.elasticsearch.index.query.QueryBuilders.queryStringQuery;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.mindforme.app.MindformeApp;
import com.mindforme.app.domain.FriendshipGroup;
import com.mindforme.app.repository.FriendshipGroupRepository;
import com.mindforme.app.repository.search.FriendshipGroupSearchRepository;
import com.mindforme.app.service.FriendshipGroupService;
import com.mindforme.app.service.dto.FriendshipGroupDTO;
import com.mindforme.app.service.mapper.FriendshipGroupMapper;
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
 * Integration tests for the {@link FriendshipGroupResource} REST controller.
 */
@SpringBootTest(classes = MindformeApp.class)
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
public class FriendshipGroupResourceIT {
    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final Integer DEFAULT_ORDER = 1;
    private static final Integer UPDATED_ORDER = 2;

    private static final Integer DEFAULT_NUMBER_OF_MEMBERS = 1;
    private static final Integer UPDATED_NUMBER_OF_MEMBERS = 2;

    @Autowired
    private FriendshipGroupRepository friendshipGroupRepository;

    @Autowired
    private FriendshipGroupMapper friendshipGroupMapper;

    @Autowired
    private FriendshipGroupService friendshipGroupService;

    /**
     * This repository is mocked in the com.mindforme.app.repository.search test package.
     *
     * @see com.mindforme.app.repository.search.FriendshipGroupSearchRepositoryMockConfiguration
     */
    @Autowired
    private FriendshipGroupSearchRepository mockFriendshipGroupSearchRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restFriendshipGroupMockMvc;

    private FriendshipGroup friendshipGroup;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static FriendshipGroup createEntity(EntityManager em) {
        FriendshipGroup friendshipGroup = new FriendshipGroup()
            .name(DEFAULT_NAME)
            .description(DEFAULT_DESCRIPTION)
            .order(DEFAULT_ORDER)
            .numberOfMembers(DEFAULT_NUMBER_OF_MEMBERS);
        return friendshipGroup;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static FriendshipGroup createUpdatedEntity(EntityManager em) {
        FriendshipGroup friendshipGroup = new FriendshipGroup()
            .name(UPDATED_NAME)
            .description(UPDATED_DESCRIPTION)
            .order(UPDATED_ORDER)
            .numberOfMembers(UPDATED_NUMBER_OF_MEMBERS);
        return friendshipGroup;
    }

    @BeforeEach
    public void initTest() {
        friendshipGroup = createEntity(em);
    }

    @Test
    @Transactional
    public void createFriendshipGroup() throws Exception {
        int databaseSizeBeforeCreate = friendshipGroupRepository.findAll().size();
        // Create the FriendshipGroup
        FriendshipGroupDTO friendshipGroupDTO = friendshipGroupMapper.toDto(friendshipGroup);
        restFriendshipGroupMockMvc
            .perform(
                post("/api/friendship-groups")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(friendshipGroupDTO))
            )
            .andExpect(status().isCreated());

        // Validate the FriendshipGroup in the database
        List<FriendshipGroup> friendshipGroupList = friendshipGroupRepository.findAll();
        assertThat(friendshipGroupList).hasSize(databaseSizeBeforeCreate + 1);
        FriendshipGroup testFriendshipGroup = friendshipGroupList.get(friendshipGroupList.size() - 1);
        assertThat(testFriendshipGroup.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testFriendshipGroup.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testFriendshipGroup.getOrder()).isEqualTo(DEFAULT_ORDER);
        assertThat(testFriendshipGroup.getNumberOfMembers()).isEqualTo(DEFAULT_NUMBER_OF_MEMBERS);

        // Validate the FriendshipGroup in Elasticsearch
        verify(mockFriendshipGroupSearchRepository, times(1)).save(testFriendshipGroup);
    }

    @Test
    @Transactional
    public void createFriendshipGroupWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = friendshipGroupRepository.findAll().size();

        // Create the FriendshipGroup with an existing ID
        friendshipGroup.setId(1L);
        FriendshipGroupDTO friendshipGroupDTO = friendshipGroupMapper.toDto(friendshipGroup);

        // An entity with an existing ID cannot be created, so this API call must fail
        restFriendshipGroupMockMvc
            .perform(
                post("/api/friendship-groups")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(friendshipGroupDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the FriendshipGroup in the database
        List<FriendshipGroup> friendshipGroupList = friendshipGroupRepository.findAll();
        assertThat(friendshipGroupList).hasSize(databaseSizeBeforeCreate);

        // Validate the FriendshipGroup in Elasticsearch
        verify(mockFriendshipGroupSearchRepository, times(0)).save(friendshipGroup);
    }

    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = friendshipGroupRepository.findAll().size();
        // set the field null
        friendshipGroup.setName(null);

        // Create the FriendshipGroup, which fails.
        FriendshipGroupDTO friendshipGroupDTO = friendshipGroupMapper.toDto(friendshipGroup);

        restFriendshipGroupMockMvc
            .perform(
                post("/api/friendship-groups")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(friendshipGroupDTO))
            )
            .andExpect(status().isBadRequest());

        List<FriendshipGroup> friendshipGroupList = friendshipGroupRepository.findAll();
        assertThat(friendshipGroupList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllFriendshipGroups() throws Exception {
        // Initialize the database
        friendshipGroupRepository.saveAndFlush(friendshipGroup);

        // Get all the friendshipGroupList
        restFriendshipGroupMockMvc
            .perform(get("/api/friendship-groups?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(friendshipGroup.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].order").value(hasItem(DEFAULT_ORDER)))
            .andExpect(jsonPath("$.[*].numberOfMembers").value(hasItem(DEFAULT_NUMBER_OF_MEMBERS)));
    }

    @Test
    @Transactional
    public void getFriendshipGroup() throws Exception {
        // Initialize the database
        friendshipGroupRepository.saveAndFlush(friendshipGroup);

        // Get the friendshipGroup
        restFriendshipGroupMockMvc
            .perform(get("/api/friendship-groups/{id}", friendshipGroup.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(friendshipGroup.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION))
            .andExpect(jsonPath("$.order").value(DEFAULT_ORDER))
            .andExpect(jsonPath("$.numberOfMembers").value(DEFAULT_NUMBER_OF_MEMBERS));
    }

    @Test
    @Transactional
    public void getNonExistingFriendshipGroup() throws Exception {
        // Get the friendshipGroup
        restFriendshipGroupMockMvc.perform(get("/api/friendship-groups/{id}", Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateFriendshipGroup() throws Exception {
        // Initialize the database
        friendshipGroupRepository.saveAndFlush(friendshipGroup);

        int databaseSizeBeforeUpdate = friendshipGroupRepository.findAll().size();

        // Update the friendshipGroup
        FriendshipGroup updatedFriendshipGroup = friendshipGroupRepository.findById(friendshipGroup.getId()).get();
        // Disconnect from session so that the updates on updatedFriendshipGroup are not directly saved in db
        em.detach(updatedFriendshipGroup);
        updatedFriendshipGroup
            .name(UPDATED_NAME)
            .description(UPDATED_DESCRIPTION)
            .order(UPDATED_ORDER)
            .numberOfMembers(UPDATED_NUMBER_OF_MEMBERS);
        FriendshipGroupDTO friendshipGroupDTO = friendshipGroupMapper.toDto(updatedFriendshipGroup);

        restFriendshipGroupMockMvc
            .perform(
                put("/api/friendship-groups")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(friendshipGroupDTO))
            )
            .andExpect(status().isOk());

        // Validate the FriendshipGroup in the database
        List<FriendshipGroup> friendshipGroupList = friendshipGroupRepository.findAll();
        assertThat(friendshipGroupList).hasSize(databaseSizeBeforeUpdate);
        FriendshipGroup testFriendshipGroup = friendshipGroupList.get(friendshipGroupList.size() - 1);
        assertThat(testFriendshipGroup.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testFriendshipGroup.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testFriendshipGroup.getOrder()).isEqualTo(UPDATED_ORDER);
        assertThat(testFriendshipGroup.getNumberOfMembers()).isEqualTo(UPDATED_NUMBER_OF_MEMBERS);

        // Validate the FriendshipGroup in Elasticsearch
        verify(mockFriendshipGroupSearchRepository, times(1)).save(testFriendshipGroup);
    }

    @Test
    @Transactional
    public void updateNonExistingFriendshipGroup() throws Exception {
        int databaseSizeBeforeUpdate = friendshipGroupRepository.findAll().size();

        // Create the FriendshipGroup
        FriendshipGroupDTO friendshipGroupDTO = friendshipGroupMapper.toDto(friendshipGroup);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restFriendshipGroupMockMvc
            .perform(
                put("/api/friendship-groups")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(friendshipGroupDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the FriendshipGroup in the database
        List<FriendshipGroup> friendshipGroupList = friendshipGroupRepository.findAll();
        assertThat(friendshipGroupList).hasSize(databaseSizeBeforeUpdate);

        // Validate the FriendshipGroup in Elasticsearch
        verify(mockFriendshipGroupSearchRepository, times(0)).save(friendshipGroup);
    }

    @Test
    @Transactional
    public void deleteFriendshipGroup() throws Exception {
        // Initialize the database
        friendshipGroupRepository.saveAndFlush(friendshipGroup);

        int databaseSizeBeforeDelete = friendshipGroupRepository.findAll().size();

        // Delete the friendshipGroup
        restFriendshipGroupMockMvc
            .perform(delete("/api/friendship-groups/{id}", friendshipGroup.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<FriendshipGroup> friendshipGroupList = friendshipGroupRepository.findAll();
        assertThat(friendshipGroupList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the FriendshipGroup in Elasticsearch
        verify(mockFriendshipGroupSearchRepository, times(1)).deleteById(friendshipGroup.getId());
    }

    @Test
    @Transactional
    public void searchFriendshipGroup() throws Exception {
        // Configure the mock search repository
        // Initialize the database
        friendshipGroupRepository.saveAndFlush(friendshipGroup);
        when(mockFriendshipGroupSearchRepository.search(queryStringQuery("id:" + friendshipGroup.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(friendshipGroup), PageRequest.of(0, 1), 1));

        // Search the friendshipGroup
        restFriendshipGroupMockMvc
            .perform(get("/api/_search/friendship-groups?query=id:" + friendshipGroup.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(friendshipGroup.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].order").value(hasItem(DEFAULT_ORDER)))
            .andExpect(jsonPath("$.[*].numberOfMembers").value(hasItem(DEFAULT_NUMBER_OF_MEMBERS)));
    }
}
