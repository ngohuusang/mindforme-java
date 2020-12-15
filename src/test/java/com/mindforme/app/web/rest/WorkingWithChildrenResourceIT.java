package com.mindforme.app.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.elasticsearch.index.query.QueryBuilders.queryStringQuery;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.mindforme.app.MindformeApp;
import com.mindforme.app.domain.WorkingWithChildren;
import com.mindforme.app.domain.enumeration.VerificationStatus;
import com.mindforme.app.repository.WorkingWithChildrenRepository;
import com.mindforme.app.repository.search.WorkingWithChildrenSearchRepository;
import com.mindforme.app.service.WorkingWithChildrenService;
import com.mindforme.app.service.dto.WorkingWithChildrenDTO;
import com.mindforme.app.service.mapper.WorkingWithChildrenMapper;
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
 * Integration tests for the {@link WorkingWithChildrenResource} REST controller.
 */
@SpringBootTest(classes = MindformeApp.class)
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
public class WorkingWithChildrenResourceIT {
    private static final String DEFAULT_FIRST_NAME = "AAAAAAAAAA";
    private static final String UPDATED_FIRST_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_OTHER_NAME = "AAAAAAAAAA";
    private static final String UPDATED_OTHER_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_FAMILY_NAME = "AAAAAAAAAA";
    private static final String UPDATED_FAMILY_NAME = "BBBBBBBBBB";

    private static final Instant DEFAULT_BIRTHDAY = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_BIRTHDAY = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_CHECK_NUMBER = "AAAAAAAAAA";
    private static final String UPDATED_CHECK_NUMBER = "BBBBBBBBBB";

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
    private WorkingWithChildrenRepository workingWithChildrenRepository;

    @Autowired
    private WorkingWithChildrenMapper workingWithChildrenMapper;

    @Autowired
    private WorkingWithChildrenService workingWithChildrenService;

    /**
     * This repository is mocked in the com.mindforme.app.repository.search test package.
     *
     * @see com.mindforme.app.repository.search.WorkingWithChildrenSearchRepositoryMockConfiguration
     */
    @Autowired
    private WorkingWithChildrenSearchRepository mockWorkingWithChildrenSearchRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restWorkingWithChildrenMockMvc;

    private WorkingWithChildren workingWithChildren;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static WorkingWithChildren createEntity(EntityManager em) {
        WorkingWithChildren workingWithChildren = new WorkingWithChildren()
            .firstName(DEFAULT_FIRST_NAME)
            .otherName(DEFAULT_OTHER_NAME)
            .familyName(DEFAULT_FAMILY_NAME)
            .birthday(DEFAULT_BIRTHDAY)
            .checkNumber(DEFAULT_CHECK_NUMBER)
            .frontImage(DEFAULT_FRONT_IMAGE)
            .backImage(DEFAULT_BACK_IMAGE)
            .note(DEFAULT_NOTE)
            .verifier(DEFAULT_VERIFIER)
            .verifiedDate(DEFAULT_VERIFIED_DATE)
            .verificationStatus(DEFAULT_VERIFICATION_STATUS);
        return workingWithChildren;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static WorkingWithChildren createUpdatedEntity(EntityManager em) {
        WorkingWithChildren workingWithChildren = new WorkingWithChildren()
            .firstName(UPDATED_FIRST_NAME)
            .otherName(UPDATED_OTHER_NAME)
            .familyName(UPDATED_FAMILY_NAME)
            .birthday(UPDATED_BIRTHDAY)
            .checkNumber(UPDATED_CHECK_NUMBER)
            .frontImage(UPDATED_FRONT_IMAGE)
            .backImage(UPDATED_BACK_IMAGE)
            .note(UPDATED_NOTE)
            .verifier(UPDATED_VERIFIER)
            .verifiedDate(UPDATED_VERIFIED_DATE)
            .verificationStatus(UPDATED_VERIFICATION_STATUS);
        return workingWithChildren;
    }

    @BeforeEach
    public void initTest() {
        workingWithChildren = createEntity(em);
    }

    @Test
    @Transactional
    public void createWorkingWithChildren() throws Exception {
        int databaseSizeBeforeCreate = workingWithChildrenRepository.findAll().size();
        // Create the WorkingWithChildren
        WorkingWithChildrenDTO workingWithChildrenDTO = workingWithChildrenMapper.toDto(workingWithChildren);
        restWorkingWithChildrenMockMvc
            .perform(
                post("/api/working-with-children")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(workingWithChildrenDTO))
            )
            .andExpect(status().isCreated());

        // Validate the WorkingWithChildren in the database
        List<WorkingWithChildren> workingWithChildrenList = workingWithChildrenRepository.findAll();
        assertThat(workingWithChildrenList).hasSize(databaseSizeBeforeCreate + 1);
        WorkingWithChildren testWorkingWithChildren = workingWithChildrenList.get(workingWithChildrenList.size() - 1);
        assertThat(testWorkingWithChildren.getFirstName()).isEqualTo(DEFAULT_FIRST_NAME);
        assertThat(testWorkingWithChildren.getOtherName()).isEqualTo(DEFAULT_OTHER_NAME);
        assertThat(testWorkingWithChildren.getFamilyName()).isEqualTo(DEFAULT_FAMILY_NAME);
        assertThat(testWorkingWithChildren.getBirthday()).isEqualTo(DEFAULT_BIRTHDAY);
        assertThat(testWorkingWithChildren.getCheckNumber()).isEqualTo(DEFAULT_CHECK_NUMBER);
        assertThat(testWorkingWithChildren.getFrontImage()).isEqualTo(DEFAULT_FRONT_IMAGE);
        assertThat(testWorkingWithChildren.getBackImage()).isEqualTo(DEFAULT_BACK_IMAGE);
        assertThat(testWorkingWithChildren.getNote()).isEqualTo(DEFAULT_NOTE);
        assertThat(testWorkingWithChildren.getVerifier()).isEqualTo(DEFAULT_VERIFIER);
        assertThat(testWorkingWithChildren.getVerifiedDate()).isEqualTo(DEFAULT_VERIFIED_DATE);
        assertThat(testWorkingWithChildren.getVerificationStatus()).isEqualTo(DEFAULT_VERIFICATION_STATUS);

        // Validate the WorkingWithChildren in Elasticsearch
        verify(mockWorkingWithChildrenSearchRepository, times(1)).save(testWorkingWithChildren);
    }

    @Test
    @Transactional
    public void createWorkingWithChildrenWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = workingWithChildrenRepository.findAll().size();

        // Create the WorkingWithChildren with an existing ID
        workingWithChildren.setId(1L);
        WorkingWithChildrenDTO workingWithChildrenDTO = workingWithChildrenMapper.toDto(workingWithChildren);

        // An entity with an existing ID cannot be created, so this API call must fail
        restWorkingWithChildrenMockMvc
            .perform(
                post("/api/working-with-children")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(workingWithChildrenDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the WorkingWithChildren in the database
        List<WorkingWithChildren> workingWithChildrenList = workingWithChildrenRepository.findAll();
        assertThat(workingWithChildrenList).hasSize(databaseSizeBeforeCreate);

        // Validate the WorkingWithChildren in Elasticsearch
        verify(mockWorkingWithChildrenSearchRepository, times(0)).save(workingWithChildren);
    }

    @Test
    @Transactional
    public void getAllWorkingWithChildren() throws Exception {
        // Initialize the database
        workingWithChildrenRepository.saveAndFlush(workingWithChildren);

        // Get all the workingWithChildrenList
        restWorkingWithChildrenMockMvc
            .perform(get("/api/working-with-children?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(workingWithChildren.getId().intValue())))
            .andExpect(jsonPath("$.[*].firstName").value(hasItem(DEFAULT_FIRST_NAME)))
            .andExpect(jsonPath("$.[*].otherName").value(hasItem(DEFAULT_OTHER_NAME)))
            .andExpect(jsonPath("$.[*].familyName").value(hasItem(DEFAULT_FAMILY_NAME)))
            .andExpect(jsonPath("$.[*].birthday").value(hasItem(DEFAULT_BIRTHDAY.toString())))
            .andExpect(jsonPath("$.[*].checkNumber").value(hasItem(DEFAULT_CHECK_NUMBER)))
            .andExpect(jsonPath("$.[*].frontImage").value(hasItem(DEFAULT_FRONT_IMAGE)))
            .andExpect(jsonPath("$.[*].backImage").value(hasItem(DEFAULT_BACK_IMAGE)))
            .andExpect(jsonPath("$.[*].note").value(hasItem(DEFAULT_NOTE)))
            .andExpect(jsonPath("$.[*].verifier").value(hasItem(DEFAULT_VERIFIER)))
            .andExpect(jsonPath("$.[*].verifiedDate").value(hasItem(DEFAULT_VERIFIED_DATE.toString())))
            .andExpect(jsonPath("$.[*].verificationStatus").value(hasItem(DEFAULT_VERIFICATION_STATUS.toString())));
    }

    @Test
    @Transactional
    public void getWorkingWithChildren() throws Exception {
        // Initialize the database
        workingWithChildrenRepository.saveAndFlush(workingWithChildren);

        // Get the workingWithChildren
        restWorkingWithChildrenMockMvc
            .perform(get("/api/working-with-children/{id}", workingWithChildren.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(workingWithChildren.getId().intValue()))
            .andExpect(jsonPath("$.firstName").value(DEFAULT_FIRST_NAME))
            .andExpect(jsonPath("$.otherName").value(DEFAULT_OTHER_NAME))
            .andExpect(jsonPath("$.familyName").value(DEFAULT_FAMILY_NAME))
            .andExpect(jsonPath("$.birthday").value(DEFAULT_BIRTHDAY.toString()))
            .andExpect(jsonPath("$.checkNumber").value(DEFAULT_CHECK_NUMBER))
            .andExpect(jsonPath("$.frontImage").value(DEFAULT_FRONT_IMAGE))
            .andExpect(jsonPath("$.backImage").value(DEFAULT_BACK_IMAGE))
            .andExpect(jsonPath("$.note").value(DEFAULT_NOTE))
            .andExpect(jsonPath("$.verifier").value(DEFAULT_VERIFIER))
            .andExpect(jsonPath("$.verifiedDate").value(DEFAULT_VERIFIED_DATE.toString()))
            .andExpect(jsonPath("$.verificationStatus").value(DEFAULT_VERIFICATION_STATUS.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingWorkingWithChildren() throws Exception {
        // Get the workingWithChildren
        restWorkingWithChildrenMockMvc.perform(get("/api/working-with-children/{id}", Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateWorkingWithChildren() throws Exception {
        // Initialize the database
        workingWithChildrenRepository.saveAndFlush(workingWithChildren);

        int databaseSizeBeforeUpdate = workingWithChildrenRepository.findAll().size();

        // Update the workingWithChildren
        WorkingWithChildren updatedWorkingWithChildren = workingWithChildrenRepository.findById(workingWithChildren.getId()).get();
        // Disconnect from session so that the updates on updatedWorkingWithChildren are not directly saved in db
        em.detach(updatedWorkingWithChildren);
        updatedWorkingWithChildren
            .firstName(UPDATED_FIRST_NAME)
            .otherName(UPDATED_OTHER_NAME)
            .familyName(UPDATED_FAMILY_NAME)
            .birthday(UPDATED_BIRTHDAY)
            .checkNumber(UPDATED_CHECK_NUMBER)
            .frontImage(UPDATED_FRONT_IMAGE)
            .backImage(UPDATED_BACK_IMAGE)
            .note(UPDATED_NOTE)
            .verifier(UPDATED_VERIFIER)
            .verifiedDate(UPDATED_VERIFIED_DATE)
            .verificationStatus(UPDATED_VERIFICATION_STATUS);
        WorkingWithChildrenDTO workingWithChildrenDTO = workingWithChildrenMapper.toDto(updatedWorkingWithChildren);

        restWorkingWithChildrenMockMvc
            .perform(
                put("/api/working-with-children")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(workingWithChildrenDTO))
            )
            .andExpect(status().isOk());

        // Validate the WorkingWithChildren in the database
        List<WorkingWithChildren> workingWithChildrenList = workingWithChildrenRepository.findAll();
        assertThat(workingWithChildrenList).hasSize(databaseSizeBeforeUpdate);
        WorkingWithChildren testWorkingWithChildren = workingWithChildrenList.get(workingWithChildrenList.size() - 1);
        assertThat(testWorkingWithChildren.getFirstName()).isEqualTo(UPDATED_FIRST_NAME);
        assertThat(testWorkingWithChildren.getOtherName()).isEqualTo(UPDATED_OTHER_NAME);
        assertThat(testWorkingWithChildren.getFamilyName()).isEqualTo(UPDATED_FAMILY_NAME);
        assertThat(testWorkingWithChildren.getBirthday()).isEqualTo(UPDATED_BIRTHDAY);
        assertThat(testWorkingWithChildren.getCheckNumber()).isEqualTo(UPDATED_CHECK_NUMBER);
        assertThat(testWorkingWithChildren.getFrontImage()).isEqualTo(UPDATED_FRONT_IMAGE);
        assertThat(testWorkingWithChildren.getBackImage()).isEqualTo(UPDATED_BACK_IMAGE);
        assertThat(testWorkingWithChildren.getNote()).isEqualTo(UPDATED_NOTE);
        assertThat(testWorkingWithChildren.getVerifier()).isEqualTo(UPDATED_VERIFIER);
        assertThat(testWorkingWithChildren.getVerifiedDate()).isEqualTo(UPDATED_VERIFIED_DATE);
        assertThat(testWorkingWithChildren.getVerificationStatus()).isEqualTo(UPDATED_VERIFICATION_STATUS);

        // Validate the WorkingWithChildren in Elasticsearch
        verify(mockWorkingWithChildrenSearchRepository, times(1)).save(testWorkingWithChildren);
    }

    @Test
    @Transactional
    public void updateNonExistingWorkingWithChildren() throws Exception {
        int databaseSizeBeforeUpdate = workingWithChildrenRepository.findAll().size();

        // Create the WorkingWithChildren
        WorkingWithChildrenDTO workingWithChildrenDTO = workingWithChildrenMapper.toDto(workingWithChildren);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restWorkingWithChildrenMockMvc
            .perform(
                put("/api/working-with-children")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(workingWithChildrenDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the WorkingWithChildren in the database
        List<WorkingWithChildren> workingWithChildrenList = workingWithChildrenRepository.findAll();
        assertThat(workingWithChildrenList).hasSize(databaseSizeBeforeUpdate);

        // Validate the WorkingWithChildren in Elasticsearch
        verify(mockWorkingWithChildrenSearchRepository, times(0)).save(workingWithChildren);
    }

    @Test
    @Transactional
    public void deleteWorkingWithChildren() throws Exception {
        // Initialize the database
        workingWithChildrenRepository.saveAndFlush(workingWithChildren);

        int databaseSizeBeforeDelete = workingWithChildrenRepository.findAll().size();

        // Delete the workingWithChildren
        restWorkingWithChildrenMockMvc
            .perform(delete("/api/working-with-children/{id}", workingWithChildren.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<WorkingWithChildren> workingWithChildrenList = workingWithChildrenRepository.findAll();
        assertThat(workingWithChildrenList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the WorkingWithChildren in Elasticsearch
        verify(mockWorkingWithChildrenSearchRepository, times(1)).deleteById(workingWithChildren.getId());
    }

    @Test
    @Transactional
    public void searchWorkingWithChildren() throws Exception {
        // Configure the mock search repository
        // Initialize the database
        workingWithChildrenRepository.saveAndFlush(workingWithChildren);
        when(mockWorkingWithChildrenSearchRepository.search(queryStringQuery("id:" + workingWithChildren.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(workingWithChildren), PageRequest.of(0, 1), 1));

        // Search the workingWithChildren
        restWorkingWithChildrenMockMvc
            .perform(get("/api/_search/working-with-children?query=id:" + workingWithChildren.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(workingWithChildren.getId().intValue())))
            .andExpect(jsonPath("$.[*].firstName").value(hasItem(DEFAULT_FIRST_NAME)))
            .andExpect(jsonPath("$.[*].otherName").value(hasItem(DEFAULT_OTHER_NAME)))
            .andExpect(jsonPath("$.[*].familyName").value(hasItem(DEFAULT_FAMILY_NAME)))
            .andExpect(jsonPath("$.[*].birthday").value(hasItem(DEFAULT_BIRTHDAY.toString())))
            .andExpect(jsonPath("$.[*].checkNumber").value(hasItem(DEFAULT_CHECK_NUMBER)))
            .andExpect(jsonPath("$.[*].frontImage").value(hasItem(DEFAULT_FRONT_IMAGE)))
            .andExpect(jsonPath("$.[*].backImage").value(hasItem(DEFAULT_BACK_IMAGE)))
            .andExpect(jsonPath("$.[*].note").value(hasItem(DEFAULT_NOTE)))
            .andExpect(jsonPath("$.[*].verifier").value(hasItem(DEFAULT_VERIFIER)))
            .andExpect(jsonPath("$.[*].verifiedDate").value(hasItem(DEFAULT_VERIFIED_DATE.toString())))
            .andExpect(jsonPath("$.[*].verificationStatus").value(hasItem(DEFAULT_VERIFICATION_STATUS.toString())));
    }
}
