package com.mindforme.app.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.elasticsearch.index.query.QueryBuilders.queryStringQuery;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.mindforme.app.MindformeApp;
import com.mindforme.app.domain.MindingNotification;
import com.mindforme.app.domain.enumeration.Frequency;
import com.mindforme.app.domain.enumeration.Privacy;
import com.mindforme.app.domain.enumeration.Status;
import com.mindforme.app.repository.MindingNotificationRepository;
import com.mindforme.app.repository.search.MindingNotificationSearchRepository;
import com.mindforme.app.service.MindingNotificationService;
import com.mindforme.app.service.dto.MindingNotificationDTO;
import com.mindforme.app.service.mapper.MindingNotificationMapper;
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
import org.springframework.util.Base64Utils;

/**
 * Integration tests for the {@link MindingNotificationResource} REST controller.
 */
@SpringBootTest(classes = MindformeApp.class)
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
public class MindingNotificationResourceIT {
    private static final Privacy DEFAULT_TYPE = Privacy.PUBLIC;
    private static final Privacy UPDATED_TYPE = Privacy.FRIENDS;

    private static final String DEFAULT_MINDING = "AAAAAAAAAA";
    private static final String UPDATED_MINDING = "BBBBBBBBBB";

    private static final Boolean DEFAULT_PUSH_NOTIFICATION = false;
    private static final Boolean UPDATED_PUSH_NOTIFICATION = true;

    private static final Frequency DEFAULT_EMAIL = Frequency.IMMEDIATELY;
    private static final Frequency UPDATED_EMAIL = Frequency.DAILY;

    private static final Status DEFAULT_STATUS = Status.Active;
    private static final Status UPDATED_STATUS = Status.Inactive;

    @Autowired
    private MindingNotificationRepository mindingNotificationRepository;

    @Autowired
    private MindingNotificationMapper mindingNotificationMapper;

    @Autowired
    private MindingNotificationService mindingNotificationService;

    /**
     * This repository is mocked in the com.mindforme.app.repository.search test package.
     *
     * @see com.mindforme.app.repository.search.MindingNotificationSearchRepositoryMockConfiguration
     */
    @Autowired
    private MindingNotificationSearchRepository mockMindingNotificationSearchRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restMindingNotificationMockMvc;

    private MindingNotification mindingNotification;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static MindingNotification createEntity(EntityManager em) {
        MindingNotification mindingNotification = new MindingNotification()
            .type(DEFAULT_TYPE)
            .minding(DEFAULT_MINDING)
            .pushNotification(DEFAULT_PUSH_NOTIFICATION)
            .email(DEFAULT_EMAIL)
            .status(DEFAULT_STATUS);
        return mindingNotification;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static MindingNotification createUpdatedEntity(EntityManager em) {
        MindingNotification mindingNotification = new MindingNotification()
            .type(UPDATED_TYPE)
            .minding(UPDATED_MINDING)
            .pushNotification(UPDATED_PUSH_NOTIFICATION)
            .email(UPDATED_EMAIL)
            .status(UPDATED_STATUS);
        return mindingNotification;
    }

    @BeforeEach
    public void initTest() {
        mindingNotification = createEntity(em);
    }

    @Test
    @Transactional
    public void createMindingNotification() throws Exception {
        int databaseSizeBeforeCreate = mindingNotificationRepository.findAll().size();
        // Create the MindingNotification
        MindingNotificationDTO mindingNotificationDTO = mindingNotificationMapper.toDto(mindingNotification);
        restMindingNotificationMockMvc
            .perform(
                post("/api/minding-notifications")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(mindingNotificationDTO))
            )
            .andExpect(status().isCreated());

        // Validate the MindingNotification in the database
        List<MindingNotification> mindingNotificationList = mindingNotificationRepository.findAll();
        assertThat(mindingNotificationList).hasSize(databaseSizeBeforeCreate + 1);
        MindingNotification testMindingNotification = mindingNotificationList.get(mindingNotificationList.size() - 1);
        assertThat(testMindingNotification.getType()).isEqualTo(DEFAULT_TYPE);
        assertThat(testMindingNotification.getMinding()).isEqualTo(DEFAULT_MINDING);
        assertThat(testMindingNotification.isPushNotification()).isEqualTo(DEFAULT_PUSH_NOTIFICATION);
        assertThat(testMindingNotification.getEmail()).isEqualTo(DEFAULT_EMAIL);
        assertThat(testMindingNotification.getStatus()).isEqualTo(DEFAULT_STATUS);

        // Validate the MindingNotification in Elasticsearch
        verify(mockMindingNotificationSearchRepository, times(1)).save(testMindingNotification);
    }

    @Test
    @Transactional
    public void createMindingNotificationWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = mindingNotificationRepository.findAll().size();

        // Create the MindingNotification with an existing ID
        mindingNotification.setId(1L);
        MindingNotificationDTO mindingNotificationDTO = mindingNotificationMapper.toDto(mindingNotification);

        // An entity with an existing ID cannot be created, so this API call must fail
        restMindingNotificationMockMvc
            .perform(
                post("/api/minding-notifications")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(mindingNotificationDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the MindingNotification in the database
        List<MindingNotification> mindingNotificationList = mindingNotificationRepository.findAll();
        assertThat(mindingNotificationList).hasSize(databaseSizeBeforeCreate);

        // Validate the MindingNotification in Elasticsearch
        verify(mockMindingNotificationSearchRepository, times(0)).save(mindingNotification);
    }

    @Test
    @Transactional
    public void getAllMindingNotifications() throws Exception {
        // Initialize the database
        mindingNotificationRepository.saveAndFlush(mindingNotification);

        // Get all the mindingNotificationList
        restMindingNotificationMockMvc
            .perform(get("/api/minding-notifications?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(mindingNotification.getId().intValue())))
            .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE.toString())))
            .andExpect(jsonPath("$.[*].minding").value(hasItem(DEFAULT_MINDING.toString())))
            .andExpect(jsonPath("$.[*].pushNotification").value(hasItem(DEFAULT_PUSH_NOTIFICATION.booleanValue())))
            .andExpect(jsonPath("$.[*].email").value(hasItem(DEFAULT_EMAIL.toString())))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.toString())));
    }

    @Test
    @Transactional
    public void getMindingNotification() throws Exception {
        // Initialize the database
        mindingNotificationRepository.saveAndFlush(mindingNotification);

        // Get the mindingNotification
        restMindingNotificationMockMvc
            .perform(get("/api/minding-notifications/{id}", mindingNotification.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(mindingNotification.getId().intValue()))
            .andExpect(jsonPath("$.type").value(DEFAULT_TYPE.toString()))
            .andExpect(jsonPath("$.minding").value(DEFAULT_MINDING.toString()))
            .andExpect(jsonPath("$.pushNotification").value(DEFAULT_PUSH_NOTIFICATION.booleanValue()))
            .andExpect(jsonPath("$.email").value(DEFAULT_EMAIL.toString()))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingMindingNotification() throws Exception {
        // Get the mindingNotification
        restMindingNotificationMockMvc.perform(get("/api/minding-notifications/{id}", Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateMindingNotification() throws Exception {
        // Initialize the database
        mindingNotificationRepository.saveAndFlush(mindingNotification);

        int databaseSizeBeforeUpdate = mindingNotificationRepository.findAll().size();

        // Update the mindingNotification
        MindingNotification updatedMindingNotification = mindingNotificationRepository.findById(mindingNotification.getId()).get();
        // Disconnect from session so that the updates on updatedMindingNotification are not directly saved in db
        em.detach(updatedMindingNotification);
        updatedMindingNotification
            .type(UPDATED_TYPE)
            .minding(UPDATED_MINDING)
            .pushNotification(UPDATED_PUSH_NOTIFICATION)
            .email(UPDATED_EMAIL)
            .status(UPDATED_STATUS);
        MindingNotificationDTO mindingNotificationDTO = mindingNotificationMapper.toDto(updatedMindingNotification);

        restMindingNotificationMockMvc
            .perform(
                put("/api/minding-notifications")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(mindingNotificationDTO))
            )
            .andExpect(status().isOk());

        // Validate the MindingNotification in the database
        List<MindingNotification> mindingNotificationList = mindingNotificationRepository.findAll();
        assertThat(mindingNotificationList).hasSize(databaseSizeBeforeUpdate);
        MindingNotification testMindingNotification = mindingNotificationList.get(mindingNotificationList.size() - 1);
        assertThat(testMindingNotification.getType()).isEqualTo(UPDATED_TYPE);
        assertThat(testMindingNotification.getMinding()).isEqualTo(UPDATED_MINDING);
        assertThat(testMindingNotification.isPushNotification()).isEqualTo(UPDATED_PUSH_NOTIFICATION);
        assertThat(testMindingNotification.getEmail()).isEqualTo(UPDATED_EMAIL);
        assertThat(testMindingNotification.getStatus()).isEqualTo(UPDATED_STATUS);

        // Validate the MindingNotification in Elasticsearch
        verify(mockMindingNotificationSearchRepository, times(1)).save(testMindingNotification);
    }

    @Test
    @Transactional
    public void updateNonExistingMindingNotification() throws Exception {
        int databaseSizeBeforeUpdate = mindingNotificationRepository.findAll().size();

        // Create the MindingNotification
        MindingNotificationDTO mindingNotificationDTO = mindingNotificationMapper.toDto(mindingNotification);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restMindingNotificationMockMvc
            .perform(
                put("/api/minding-notifications")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(mindingNotificationDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the MindingNotification in the database
        List<MindingNotification> mindingNotificationList = mindingNotificationRepository.findAll();
        assertThat(mindingNotificationList).hasSize(databaseSizeBeforeUpdate);

        // Validate the MindingNotification in Elasticsearch
        verify(mockMindingNotificationSearchRepository, times(0)).save(mindingNotification);
    }

    @Test
    @Transactional
    public void deleteMindingNotification() throws Exception {
        // Initialize the database
        mindingNotificationRepository.saveAndFlush(mindingNotification);

        int databaseSizeBeforeDelete = mindingNotificationRepository.findAll().size();

        // Delete the mindingNotification
        restMindingNotificationMockMvc
            .perform(delete("/api/minding-notifications/{id}", mindingNotification.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<MindingNotification> mindingNotificationList = mindingNotificationRepository.findAll();
        assertThat(mindingNotificationList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the MindingNotification in Elasticsearch
        verify(mockMindingNotificationSearchRepository, times(1)).deleteById(mindingNotification.getId());
    }

    @Test
    @Transactional
    public void searchMindingNotification() throws Exception {
        // Configure the mock search repository
        // Initialize the database
        mindingNotificationRepository.saveAndFlush(mindingNotification);
        when(mockMindingNotificationSearchRepository.search(queryStringQuery("id:" + mindingNotification.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(mindingNotification), PageRequest.of(0, 1), 1));

        // Search the mindingNotification
        restMindingNotificationMockMvc
            .perform(get("/api/_search/minding-notifications?query=id:" + mindingNotification.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(mindingNotification.getId().intValue())))
            .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE.toString())))
            .andExpect(jsonPath("$.[*].minding").value(hasItem(DEFAULT_MINDING.toString())))
            .andExpect(jsonPath("$.[*].pushNotification").value(hasItem(DEFAULT_PUSH_NOTIFICATION.booleanValue())))
            .andExpect(jsonPath("$.[*].email").value(hasItem(DEFAULT_EMAIL.toString())))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.toString())));
    }
}
