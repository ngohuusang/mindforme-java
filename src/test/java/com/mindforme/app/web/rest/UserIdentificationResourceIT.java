package com.mindforme.app.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.elasticsearch.index.query.QueryBuilders.queryStringQuery;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.mindforme.app.MindformeApp;
import com.mindforme.app.domain.UserIdentification;
import com.mindforme.app.domain.enumeration.IdType;
import com.mindforme.app.domain.enumeration.VerificationStatus;
import com.mindforme.app.repository.UserIdentificationRepository;
import com.mindforme.app.repository.search.UserIdentificationSearchRepository;
import com.mindforme.app.service.UserIdentificationService;
import com.mindforme.app.service.dto.UserIdentificationDTO;
import com.mindforme.app.service.mapper.UserIdentificationMapper;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
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
 * Integration tests for the {@link UserIdentificationResource} REST controller.
 */
@SpringBootTest(classes = MindformeApp.class)
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
public class UserIdentificationResourceIT {
    private static final IdType DEFAULT_ID_TYPE = IdType.PASSPORT;
    private static final IdType UPDATED_ID_TYPE = IdType.DRIVER_LICENSE;

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_EXPIRED = "AAAAAAAAAA";
    private static final String UPDATED_EXPIRED = "BBBBBBBBBB";

    private static final String DEFAULT_ID_NUMBER = "AAAAAAAAAA";
    private static final String UPDATED_ID_NUMBER = "BBBBBBBBBB";

    private static final String DEFAULT_FRONT_IMAGE = "AAAAAAAAAA";
    private static final String UPDATED_FRONT_IMAGE = "BBBBBBBBBB";

    private static final String DEFAULT_BACK_IMAGE = "AAAAAAAAAA";
    private static final String UPDATED_BACK_IMAGE = "BBBBBBBBBB";

    private static final String DEFAULT_NOTE = "AAAAAAAAAA";
    private static final String UPDATED_NOTE = "BBBBBBBBBB";

    private static final String DEFAULT_VERIFIER = "AAAAAAAAAA";
    private static final String UPDATED_VERIFIER = "BBBBBBBBBB";

    private static final Instant DEFAULT_VERIFIED_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_VERIFIED_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final VerificationStatus DEFAULT_VERIFICATION_STATUS = VerificationStatus.ADDED;
    private static final VerificationStatus UPDATED_VERIFICATION_STATUS = VerificationStatus.VERIFYING;

    @Autowired
    private UserIdentificationRepository userIdentificationRepository;

    @Autowired
    private UserIdentificationMapper userIdentificationMapper;

    @Autowired
    private UserIdentificationService userIdentificationService;

    /**
     * This repository is mocked in the com.mindforme.app.repository.search test package.
     *
     * @see com.mindforme.app.repository.search.UserIdentificationSearchRepositoryMockConfiguration
     */
    @Autowired
    private UserIdentificationSearchRepository mockUserIdentificationSearchRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restUserIdentificationMockMvc;

    private UserIdentification userIdentification;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static UserIdentification createEntity(EntityManager em) {
        UserIdentification userIdentification = new UserIdentification()
            .idType(DEFAULT_ID_TYPE)
            .name(DEFAULT_NAME)
            .expired(DEFAULT_EXPIRED)
            .idNumber(DEFAULT_ID_NUMBER)
            .frontImage(DEFAULT_FRONT_IMAGE)
            .backImage(DEFAULT_BACK_IMAGE)
            .note(DEFAULT_NOTE)
            .verifier(DEFAULT_VERIFIER)
            .verifiedDate(DEFAULT_VERIFIED_DATE)
            .verificationStatus(DEFAULT_VERIFICATION_STATUS);
        return userIdentification;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static UserIdentification createUpdatedEntity(EntityManager em) {
        UserIdentification userIdentification = new UserIdentification()
            .idType(UPDATED_ID_TYPE)
            .name(UPDATED_NAME)
            .expired(UPDATED_EXPIRED)
            .idNumber(UPDATED_ID_NUMBER)
            .frontImage(UPDATED_FRONT_IMAGE)
            .backImage(UPDATED_BACK_IMAGE)
            .note(UPDATED_NOTE)
            .verifier(UPDATED_VERIFIER)
            .verifiedDate(UPDATED_VERIFIED_DATE)
            .verificationStatus(UPDATED_VERIFICATION_STATUS);
        return userIdentification;
    }

    @BeforeEach
    public void initTest() {
        userIdentification = createEntity(em);
    }

    @Test
    @Transactional
    public void createUserIdentification() throws Exception {
        int databaseSizeBeforeCreate = userIdentificationRepository.findAll().size();
        // Create the UserIdentification
        UserIdentificationDTO userIdentificationDTO = userIdentificationMapper.toDto(userIdentification);
        restUserIdentificationMockMvc
            .perform(
                post("/api/user-identifications")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(userIdentificationDTO))
            )
            .andExpect(status().isCreated());

        // Validate the UserIdentification in the database
        List<UserIdentification> userIdentificationList = userIdentificationRepository.findAll();
        assertThat(userIdentificationList).hasSize(databaseSizeBeforeCreate + 1);
        UserIdentification testUserIdentification = userIdentificationList.get(userIdentificationList.size() - 1);
        assertThat(testUserIdentification.getIdType()).isEqualTo(DEFAULT_ID_TYPE);
        assertThat(testUserIdentification.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testUserIdentification.getExpired()).isEqualTo(DEFAULT_EXPIRED);
        assertThat(testUserIdentification.getIdNumber()).isEqualTo(DEFAULT_ID_NUMBER);
        assertThat(testUserIdentification.getFrontImage()).isEqualTo(DEFAULT_FRONT_IMAGE);
        assertThat(testUserIdentification.getBackImage()).isEqualTo(DEFAULT_BACK_IMAGE);
        assertThat(testUserIdentification.getNote()).isEqualTo(DEFAULT_NOTE);
        assertThat(testUserIdentification.getVerifier()).isEqualTo(DEFAULT_VERIFIER);
        assertThat(testUserIdentification.getVerifiedDate()).isEqualTo(DEFAULT_VERIFIED_DATE);
        assertThat(testUserIdentification.getVerificationStatus()).isEqualTo(DEFAULT_VERIFICATION_STATUS);

        // Validate the UserIdentification in Elasticsearch
        verify(mockUserIdentificationSearchRepository, times(1)).save(testUserIdentification);
    }

    @Test
    @Transactional
    public void createUserIdentificationWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = userIdentificationRepository.findAll().size();

        // Create the UserIdentification with an existing ID
        userIdentification.setId(1L);
        UserIdentificationDTO userIdentificationDTO = userIdentificationMapper.toDto(userIdentification);

        // An entity with an existing ID cannot be created, so this API call must fail
        restUserIdentificationMockMvc
            .perform(
                post("/api/user-identifications")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(userIdentificationDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the UserIdentification in the database
        List<UserIdentification> userIdentificationList = userIdentificationRepository.findAll();
        assertThat(userIdentificationList).hasSize(databaseSizeBeforeCreate);

        // Validate the UserIdentification in Elasticsearch
        verify(mockUserIdentificationSearchRepository, times(0)).save(userIdentification);
    }

    @Test
    @Transactional
    public void getAllUserIdentifications() throws Exception {
        // Initialize the database
        userIdentificationRepository.saveAndFlush(userIdentification);

        // Get all the userIdentificationList
        restUserIdentificationMockMvc
            .perform(get("/api/user-identifications?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(userIdentification.getId().intValue())))
            .andExpect(jsonPath("$.[*].idType").value(hasItem(DEFAULT_ID_TYPE.toString())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].expired").value(hasItem(DEFAULT_EXPIRED)))
            .andExpect(jsonPath("$.[*].idNumber").value(hasItem(DEFAULT_ID_NUMBER)))
            .andExpect(jsonPath("$.[*].frontImage").value(hasItem(DEFAULT_FRONT_IMAGE)))
            .andExpect(jsonPath("$.[*].backImage").value(hasItem(DEFAULT_BACK_IMAGE)))
            .andExpect(jsonPath("$.[*].note").value(hasItem(DEFAULT_NOTE)))
            .andExpect(jsonPath("$.[*].verifier").value(hasItem(DEFAULT_VERIFIER)))
            .andExpect(jsonPath("$.[*].verifiedDate").value(hasItem(DEFAULT_VERIFIED_DATE.toString())))
            .andExpect(jsonPath("$.[*].verificationStatus").value(hasItem(DEFAULT_VERIFICATION_STATUS.toString())));
    }

    @Test
    @Transactional
    public void getUserIdentification() throws Exception {
        // Initialize the database
        userIdentificationRepository.saveAndFlush(userIdentification);

        // Get the userIdentification
        restUserIdentificationMockMvc
            .perform(get("/api/user-identifications/{id}", userIdentification.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(userIdentification.getId().intValue()))
            .andExpect(jsonPath("$.idType").value(DEFAULT_ID_TYPE.toString()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.expired").value(DEFAULT_EXPIRED))
            .andExpect(jsonPath("$.idNumber").value(DEFAULT_ID_NUMBER))
            .andExpect(jsonPath("$.frontImage").value(DEFAULT_FRONT_IMAGE))
            .andExpect(jsonPath("$.backImage").value(DEFAULT_BACK_IMAGE))
            .andExpect(jsonPath("$.note").value(DEFAULT_NOTE))
            .andExpect(jsonPath("$.verifier").value(DEFAULT_VERIFIER))
            .andExpect(jsonPath("$.verifiedDate").value(DEFAULT_VERIFIED_DATE.toString()))
            .andExpect(jsonPath("$.verificationStatus").value(DEFAULT_VERIFICATION_STATUS.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingUserIdentification() throws Exception {
        // Get the userIdentification
        restUserIdentificationMockMvc.perform(get("/api/user-identifications/{id}", Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateUserIdentification() throws Exception {
        // Initialize the database
        userIdentificationRepository.saveAndFlush(userIdentification);

        int databaseSizeBeforeUpdate = userIdentificationRepository.findAll().size();

        // Update the userIdentification
        UserIdentification updatedUserIdentification = userIdentificationRepository.findById(userIdentification.getId()).get();
        // Disconnect from session so that the updates on updatedUserIdentification are not directly saved in db
        em.detach(updatedUserIdentification);
        updatedUserIdentification
            .idType(UPDATED_ID_TYPE)
            .name(UPDATED_NAME)
            .expired(UPDATED_EXPIRED)
            .idNumber(UPDATED_ID_NUMBER)
            .frontImage(UPDATED_FRONT_IMAGE)
            .backImage(UPDATED_BACK_IMAGE)
            .note(UPDATED_NOTE)
            .verifier(UPDATED_VERIFIER)
            .verifiedDate(UPDATED_VERIFIED_DATE)
            .verificationStatus(UPDATED_VERIFICATION_STATUS);
        UserIdentificationDTO userIdentificationDTO = userIdentificationMapper.toDto(updatedUserIdentification);

        restUserIdentificationMockMvc
            .perform(
                put("/api/user-identifications")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(userIdentificationDTO))
            )
            .andExpect(status().isOk());

        // Validate the UserIdentification in the database
        List<UserIdentification> userIdentificationList = userIdentificationRepository.findAll();
        assertThat(userIdentificationList).hasSize(databaseSizeBeforeUpdate);
        UserIdentification testUserIdentification = userIdentificationList.get(userIdentificationList.size() - 1);
        assertThat(testUserIdentification.getIdType()).isEqualTo(UPDATED_ID_TYPE);
        assertThat(testUserIdentification.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testUserIdentification.getExpired()).isEqualTo(UPDATED_EXPIRED);
        assertThat(testUserIdentification.getIdNumber()).isEqualTo(UPDATED_ID_NUMBER);
        assertThat(testUserIdentification.getFrontImage()).isEqualTo(UPDATED_FRONT_IMAGE);
        assertThat(testUserIdentification.getBackImage()).isEqualTo(UPDATED_BACK_IMAGE);
        assertThat(testUserIdentification.getNote()).isEqualTo(UPDATED_NOTE);
        assertThat(testUserIdentification.getVerifier()).isEqualTo(UPDATED_VERIFIER);
        assertThat(testUserIdentification.getVerifiedDate()).isEqualTo(UPDATED_VERIFIED_DATE);
        assertThat(testUserIdentification.getVerificationStatus()).isEqualTo(UPDATED_VERIFICATION_STATUS);

        // Validate the UserIdentification in Elasticsearch
        verify(mockUserIdentificationSearchRepository, times(1)).save(testUserIdentification);
    }

    @Test
    @Transactional
    public void updateNonExistingUserIdentification() throws Exception {
        int databaseSizeBeforeUpdate = userIdentificationRepository.findAll().size();

        // Create the UserIdentification
        UserIdentificationDTO userIdentificationDTO = userIdentificationMapper.toDto(userIdentification);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restUserIdentificationMockMvc
            .perform(
                put("/api/user-identifications")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(userIdentificationDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the UserIdentification in the database
        List<UserIdentification> userIdentificationList = userIdentificationRepository.findAll();
        assertThat(userIdentificationList).hasSize(databaseSizeBeforeUpdate);

        // Validate the UserIdentification in Elasticsearch
        verify(mockUserIdentificationSearchRepository, times(0)).save(userIdentification);
    }

    @Test
    @Transactional
    public void deleteUserIdentification() throws Exception {
        // Initialize the database
        userIdentificationRepository.saveAndFlush(userIdentification);

        int databaseSizeBeforeDelete = userIdentificationRepository.findAll().size();

        // Delete the userIdentification
        restUserIdentificationMockMvc
            .perform(delete("/api/user-identifications/{id}", userIdentification.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<UserIdentification> userIdentificationList = userIdentificationRepository.findAll();
        assertThat(userIdentificationList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the UserIdentification in Elasticsearch
        verify(mockUserIdentificationSearchRepository, times(1)).deleteById(userIdentification.getId());
    }

    @Test
    @Transactional
    public void searchUserIdentification() throws Exception {
        // Configure the mock search repository
        // Initialize the database
        userIdentificationRepository.saveAndFlush(userIdentification);
        when(mockUserIdentificationSearchRepository.search(queryStringQuery("id:" + userIdentification.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(userIdentification), PageRequest.of(0, 1), 1));

        // Search the userIdentification
        restUserIdentificationMockMvc
            .perform(get("/api/_search/user-identifications?query=id:" + userIdentification.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(userIdentification.getId().intValue())))
            .andExpect(jsonPath("$.[*].idType").value(hasItem(DEFAULT_ID_TYPE.toString())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].expired").value(hasItem(DEFAULT_EXPIRED)))
            .andExpect(jsonPath("$.[*].idNumber").value(hasItem(DEFAULT_ID_NUMBER)))
            .andExpect(jsonPath("$.[*].frontImage").value(hasItem(DEFAULT_FRONT_IMAGE)))
            .andExpect(jsonPath("$.[*].backImage").value(hasItem(DEFAULT_BACK_IMAGE)))
            .andExpect(jsonPath("$.[*].note").value(hasItem(DEFAULT_NOTE)))
            .andExpect(jsonPath("$.[*].verifier").value(hasItem(DEFAULT_VERIFIER)))
            .andExpect(jsonPath("$.[*].verifiedDate").value(hasItem(DEFAULT_VERIFIED_DATE.toString())))
            .andExpect(jsonPath("$.[*].verificationStatus").value(hasItem(DEFAULT_VERIFICATION_STATUS.toString())));
    }
}
