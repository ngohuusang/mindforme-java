package com.mindforme.app.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.elasticsearch.index.query.QueryBuilders.queryStringQuery;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.mindforme.app.MindformeApp;
import com.mindforme.app.domain.Child;
import com.mindforme.app.domain.enumeration.Status;
import com.mindforme.app.repository.ChildRepository;
import com.mindforme.app.repository.search.ChildSearchRepository;
import com.mindforme.app.service.ChildService;
import com.mindforme.app.service.dto.ChildDTO;
import com.mindforme.app.service.mapper.ChildMapper;
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
 * Integration tests for the {@link ChildResource} REST controller.
 */
@SpringBootTest(classes = MindformeApp.class)
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
public class ChildResourceIT {
    private static final String DEFAULT_FIRST_NAME = "AAAAAAAAAA";
    private static final String UPDATED_FIRST_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_LAST_NAME = "AAAAAAAAAA";
    private static final String UPDATED_LAST_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_IMAGE_URL = "AAAAAAAAAA";
    private static final String UPDATED_IMAGE_URL = "BBBBBBBBBB";

    private static final Instant DEFAULT_BIRTHDAY = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_BIRTHDAY = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Status DEFAULT_STATUS = Status.Active;
    private static final Status UPDATED_STATUS = Status.Inactive;

    @Autowired
    private ChildRepository childRepository;

    @Mock
    private ChildRepository childRepositoryMock;

    @Autowired
    private ChildMapper childMapper;

    @Mock
    private ChildService childServiceMock;

    @Autowired
    private ChildService childService;

    /**
     * This repository is mocked in the com.mindforme.app.repository.search test package.
     *
     * @see com.mindforme.app.repository.search.ChildSearchRepositoryMockConfiguration
     */
    @Autowired
    private ChildSearchRepository mockChildSearchRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restChildMockMvc;

    private Child child;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Child createEntity(EntityManager em) {
        Child child = new Child()
            .firstName(DEFAULT_FIRST_NAME)
            .lastName(DEFAULT_LAST_NAME)
            .imageUrl(DEFAULT_IMAGE_URL)
            .birthday(DEFAULT_BIRTHDAY)
            .status(DEFAULT_STATUS);
        return child;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Child createUpdatedEntity(EntityManager em) {
        Child child = new Child()
            .firstName(UPDATED_FIRST_NAME)
            .lastName(UPDATED_LAST_NAME)
            .imageUrl(UPDATED_IMAGE_URL)
            .birthday(UPDATED_BIRTHDAY)
            .status(UPDATED_STATUS);
        return child;
    }

    @BeforeEach
    public void initTest() {
        child = createEntity(em);
    }

    @Test
    @Transactional
    public void createChild() throws Exception {
        int databaseSizeBeforeCreate = childRepository.findAll().size();
        // Create the Child
        ChildDTO childDTO = childMapper.toDto(child);
        restChildMockMvc
            .perform(post("/api/children").contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(childDTO)))
            .andExpect(status().isCreated());

        // Validate the Child in the database
        List<Child> childList = childRepository.findAll();
        assertThat(childList).hasSize(databaseSizeBeforeCreate + 1);
        Child testChild = childList.get(childList.size() - 1);
        assertThat(testChild.getFirstName()).isEqualTo(DEFAULT_FIRST_NAME);
        assertThat(testChild.getLastName()).isEqualTo(DEFAULT_LAST_NAME);
        assertThat(testChild.getImageUrl()).isEqualTo(DEFAULT_IMAGE_URL);
        assertThat(testChild.getBirthday()).isEqualTo(DEFAULT_BIRTHDAY);
        assertThat(testChild.getStatus()).isEqualTo(DEFAULT_STATUS);

        // Validate the Child in Elasticsearch
        verify(mockChildSearchRepository, times(1)).save(testChild);
    }

    @Test
    @Transactional
    public void createChildWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = childRepository.findAll().size();

        // Create the Child with an existing ID
        child.setId(1L);
        ChildDTO childDTO = childMapper.toDto(child);

        // An entity with an existing ID cannot be created, so this API call must fail
        restChildMockMvc
            .perform(post("/api/children").contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(childDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Child in the database
        List<Child> childList = childRepository.findAll();
        assertThat(childList).hasSize(databaseSizeBeforeCreate);

        // Validate the Child in Elasticsearch
        verify(mockChildSearchRepository, times(0)).save(child);
    }

    @Test
    @Transactional
    public void getAllChildren() throws Exception {
        // Initialize the database
        childRepository.saveAndFlush(child);

        // Get all the childList
        restChildMockMvc
            .perform(get("/api/children?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(child.getId().intValue())))
            .andExpect(jsonPath("$.[*].firstName").value(hasItem(DEFAULT_FIRST_NAME)))
            .andExpect(jsonPath("$.[*].lastName").value(hasItem(DEFAULT_LAST_NAME)))
            .andExpect(jsonPath("$.[*].imageUrl").value(hasItem(DEFAULT_IMAGE_URL)))
            .andExpect(jsonPath("$.[*].birthday").value(hasItem(DEFAULT_BIRTHDAY.toString())))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.toString())));
    }

    @SuppressWarnings({ "unchecked" })
    public void getAllChildrenWithEagerRelationshipsIsEnabled() throws Exception {
        when(childServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restChildMockMvc.perform(get("/api/children?eagerload=true")).andExpect(status().isOk());

        verify(childServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({ "unchecked" })
    public void getAllChildrenWithEagerRelationshipsIsNotEnabled() throws Exception {
        when(childServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restChildMockMvc.perform(get("/api/children?eagerload=true")).andExpect(status().isOk());

        verify(childServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @Test
    @Transactional
    public void getChild() throws Exception {
        // Initialize the database
        childRepository.saveAndFlush(child);

        // Get the child
        restChildMockMvc
            .perform(get("/api/children/{id}", child.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(child.getId().intValue()))
            .andExpect(jsonPath("$.firstName").value(DEFAULT_FIRST_NAME))
            .andExpect(jsonPath("$.lastName").value(DEFAULT_LAST_NAME))
            .andExpect(jsonPath("$.imageUrl").value(DEFAULT_IMAGE_URL))
            .andExpect(jsonPath("$.birthday").value(DEFAULT_BIRTHDAY.toString()))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingChild() throws Exception {
        // Get the child
        restChildMockMvc.perform(get("/api/children/{id}", Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateChild() throws Exception {
        // Initialize the database
        childRepository.saveAndFlush(child);

        int databaseSizeBeforeUpdate = childRepository.findAll().size();

        // Update the child
        Child updatedChild = childRepository.findById(child.getId()).get();
        // Disconnect from session so that the updates on updatedChild are not directly saved in db
        em.detach(updatedChild);
        updatedChild
            .firstName(UPDATED_FIRST_NAME)
            .lastName(UPDATED_LAST_NAME)
            .imageUrl(UPDATED_IMAGE_URL)
            .birthday(UPDATED_BIRTHDAY)
            .status(UPDATED_STATUS);
        ChildDTO childDTO = childMapper.toDto(updatedChild);

        restChildMockMvc
            .perform(put("/api/children").contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(childDTO)))
            .andExpect(status().isOk());

        // Validate the Child in the database
        List<Child> childList = childRepository.findAll();
        assertThat(childList).hasSize(databaseSizeBeforeUpdate);
        Child testChild = childList.get(childList.size() - 1);
        assertThat(testChild.getFirstName()).isEqualTo(UPDATED_FIRST_NAME);
        assertThat(testChild.getLastName()).isEqualTo(UPDATED_LAST_NAME);
        assertThat(testChild.getImageUrl()).isEqualTo(UPDATED_IMAGE_URL);
        assertThat(testChild.getBirthday()).isEqualTo(UPDATED_BIRTHDAY);
        assertThat(testChild.getStatus()).isEqualTo(UPDATED_STATUS);

        // Validate the Child in Elasticsearch
        verify(mockChildSearchRepository, times(1)).save(testChild);
    }

    @Test
    @Transactional
    public void updateNonExistingChild() throws Exception {
        int databaseSizeBeforeUpdate = childRepository.findAll().size();

        // Create the Child
        ChildDTO childDTO = childMapper.toDto(child);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restChildMockMvc
            .perform(put("/api/children").contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(childDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Child in the database
        List<Child> childList = childRepository.findAll();
        assertThat(childList).hasSize(databaseSizeBeforeUpdate);

        // Validate the Child in Elasticsearch
        verify(mockChildSearchRepository, times(0)).save(child);
    }

    @Test
    @Transactional
    public void deleteChild() throws Exception {
        // Initialize the database
        childRepository.saveAndFlush(child);

        int databaseSizeBeforeDelete = childRepository.findAll().size();

        // Delete the child
        restChildMockMvc
            .perform(delete("/api/children/{id}", child.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Child> childList = childRepository.findAll();
        assertThat(childList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the Child in Elasticsearch
        verify(mockChildSearchRepository, times(1)).deleteById(child.getId());
    }

    @Test
    @Transactional
    public void searchChild() throws Exception {
        // Configure the mock search repository
        // Initialize the database
        childRepository.saveAndFlush(child);
        when(mockChildSearchRepository.search(queryStringQuery("id:" + child.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(child), PageRequest.of(0, 1), 1));

        // Search the child
        restChildMockMvc
            .perform(get("/api/_search/children?query=id:" + child.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(child.getId().intValue())))
            .andExpect(jsonPath("$.[*].firstName").value(hasItem(DEFAULT_FIRST_NAME)))
            .andExpect(jsonPath("$.[*].lastName").value(hasItem(DEFAULT_LAST_NAME)))
            .andExpect(jsonPath("$.[*].imageUrl").value(hasItem(DEFAULT_IMAGE_URL)))
            .andExpect(jsonPath("$.[*].birthday").value(hasItem(DEFAULT_BIRTHDAY.toString())))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.toString())));
    }
}
