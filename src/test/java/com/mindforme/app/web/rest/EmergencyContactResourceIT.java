package com.mindforme.app.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.elasticsearch.index.query.QueryBuilders.queryStringQuery;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.mindforme.app.MindformeApp;
import com.mindforme.app.domain.EmergencyContact;
import com.mindforme.app.repository.EmergencyContactRepository;
import com.mindforme.app.repository.search.EmergencyContactSearchRepository;
import com.mindforme.app.service.EmergencyContactService;
import com.mindforme.app.service.dto.EmergencyContactDTO;
import com.mindforme.app.service.mapper.EmergencyContactMapper;
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
 * Integration tests for the {@link EmergencyContactResource} REST controller.
 */
@SpringBootTest(classes = MindformeApp.class)
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
public class EmergencyContactResourceIT {
    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_RELATION_TO_YOU = "AAAAAAAAAA";
    private static final String UPDATED_RELATION_TO_YOU = "BBBBBBBBBB";

    private static final String DEFAULT_PHONE_NUMBER = "AAAAAAAAAA";
    private static final String UPDATED_PHONE_NUMBER = "BBBBBBBBBB";

    private static final String DEFAULT_EMAIL = "AAAAAAAAAA";
    private static final String UPDATED_EMAIL = "BBBBBBBBBB";

    @Autowired
    private EmergencyContactRepository emergencyContactRepository;

    @Autowired
    private EmergencyContactMapper emergencyContactMapper;

    @Autowired
    private EmergencyContactService emergencyContactService;

    /**
     * This repository is mocked in the com.mindforme.app.repository.search test package.
     *
     * @see com.mindforme.app.repository.search.EmergencyContactSearchRepositoryMockConfiguration
     */
    @Autowired
    private EmergencyContactSearchRepository mockEmergencyContactSearchRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restEmergencyContactMockMvc;

    private EmergencyContact emergencyContact;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static EmergencyContact createEntity(EntityManager em) {
        EmergencyContact emergencyContact = new EmergencyContact()
            .name(DEFAULT_NAME)
            .relationToYou(DEFAULT_RELATION_TO_YOU)
            .phoneNumber(DEFAULT_PHONE_NUMBER)
            .email(DEFAULT_EMAIL);
        return emergencyContact;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static EmergencyContact createUpdatedEntity(EntityManager em) {
        EmergencyContact emergencyContact = new EmergencyContact()
            .name(UPDATED_NAME)
            .relationToYou(UPDATED_RELATION_TO_YOU)
            .phoneNumber(UPDATED_PHONE_NUMBER)
            .email(UPDATED_EMAIL);
        return emergencyContact;
    }

    @BeforeEach
    public void initTest() {
        emergencyContact = createEntity(em);
    }

    @Test
    @Transactional
    public void createEmergencyContact() throws Exception {
        int databaseSizeBeforeCreate = emergencyContactRepository.findAll().size();
        // Create the EmergencyContact
        EmergencyContactDTO emergencyContactDTO = emergencyContactMapper.toDto(emergencyContact);
        restEmergencyContactMockMvc
            .perform(
                post("/api/emergency-contacts")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(emergencyContactDTO))
            )
            .andExpect(status().isCreated());

        // Validate the EmergencyContact in the database
        List<EmergencyContact> emergencyContactList = emergencyContactRepository.findAll();
        assertThat(emergencyContactList).hasSize(databaseSizeBeforeCreate + 1);
        EmergencyContact testEmergencyContact = emergencyContactList.get(emergencyContactList.size() - 1);
        assertThat(testEmergencyContact.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testEmergencyContact.getRelationToYou()).isEqualTo(DEFAULT_RELATION_TO_YOU);
        assertThat(testEmergencyContact.getPhoneNumber()).isEqualTo(DEFAULT_PHONE_NUMBER);
        assertThat(testEmergencyContact.getEmail()).isEqualTo(DEFAULT_EMAIL);

        // Validate the EmergencyContact in Elasticsearch
        verify(mockEmergencyContactSearchRepository, times(1)).save(testEmergencyContact);
    }

    @Test
    @Transactional
    public void createEmergencyContactWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = emergencyContactRepository.findAll().size();

        // Create the EmergencyContact with an existing ID
        emergencyContact.setId(1L);
        EmergencyContactDTO emergencyContactDTO = emergencyContactMapper.toDto(emergencyContact);

        // An entity with an existing ID cannot be created, so this API call must fail
        restEmergencyContactMockMvc
            .perform(
                post("/api/emergency-contacts")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(emergencyContactDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the EmergencyContact in the database
        List<EmergencyContact> emergencyContactList = emergencyContactRepository.findAll();
        assertThat(emergencyContactList).hasSize(databaseSizeBeforeCreate);

        // Validate the EmergencyContact in Elasticsearch
        verify(mockEmergencyContactSearchRepository, times(0)).save(emergencyContact);
    }

    @Test
    @Transactional
    public void checkEmailIsRequired() throws Exception {
        int databaseSizeBeforeTest = emergencyContactRepository.findAll().size();
        // set the field null
        emergencyContact.setEmail(null);

        // Create the EmergencyContact, which fails.
        EmergencyContactDTO emergencyContactDTO = emergencyContactMapper.toDto(emergencyContact);

        restEmergencyContactMockMvc
            .perform(
                post("/api/emergency-contacts")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(emergencyContactDTO))
            )
            .andExpect(status().isBadRequest());

        List<EmergencyContact> emergencyContactList = emergencyContactRepository.findAll();
        assertThat(emergencyContactList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllEmergencyContacts() throws Exception {
        // Initialize the database
        emergencyContactRepository.saveAndFlush(emergencyContact);

        // Get all the emergencyContactList
        restEmergencyContactMockMvc
            .perform(get("/api/emergency-contacts?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(emergencyContact.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].relationToYou").value(hasItem(DEFAULT_RELATION_TO_YOU)))
            .andExpect(jsonPath("$.[*].phoneNumber").value(hasItem(DEFAULT_PHONE_NUMBER)))
            .andExpect(jsonPath("$.[*].email").value(hasItem(DEFAULT_EMAIL)));
    }

    @Test
    @Transactional
    public void getEmergencyContact() throws Exception {
        // Initialize the database
        emergencyContactRepository.saveAndFlush(emergencyContact);

        // Get the emergencyContact
        restEmergencyContactMockMvc
            .perform(get("/api/emergency-contacts/{id}", emergencyContact.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(emergencyContact.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.relationToYou").value(DEFAULT_RELATION_TO_YOU))
            .andExpect(jsonPath("$.phoneNumber").value(DEFAULT_PHONE_NUMBER))
            .andExpect(jsonPath("$.email").value(DEFAULT_EMAIL));
    }

    @Test
    @Transactional
    public void getNonExistingEmergencyContact() throws Exception {
        // Get the emergencyContact
        restEmergencyContactMockMvc.perform(get("/api/emergency-contacts/{id}", Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateEmergencyContact() throws Exception {
        // Initialize the database
        emergencyContactRepository.saveAndFlush(emergencyContact);

        int databaseSizeBeforeUpdate = emergencyContactRepository.findAll().size();

        // Update the emergencyContact
        EmergencyContact updatedEmergencyContact = emergencyContactRepository.findById(emergencyContact.getId()).get();
        // Disconnect from session so that the updates on updatedEmergencyContact are not directly saved in db
        em.detach(updatedEmergencyContact);
        updatedEmergencyContact
            .name(UPDATED_NAME)
            .relationToYou(UPDATED_RELATION_TO_YOU)
            .phoneNumber(UPDATED_PHONE_NUMBER)
            .email(UPDATED_EMAIL);
        EmergencyContactDTO emergencyContactDTO = emergencyContactMapper.toDto(updatedEmergencyContact);

        restEmergencyContactMockMvc
            .perform(
                put("/api/emergency-contacts")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(emergencyContactDTO))
            )
            .andExpect(status().isOk());

        // Validate the EmergencyContact in the database
        List<EmergencyContact> emergencyContactList = emergencyContactRepository.findAll();
        assertThat(emergencyContactList).hasSize(databaseSizeBeforeUpdate);
        EmergencyContact testEmergencyContact = emergencyContactList.get(emergencyContactList.size() - 1);
        assertThat(testEmergencyContact.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testEmergencyContact.getRelationToYou()).isEqualTo(UPDATED_RELATION_TO_YOU);
        assertThat(testEmergencyContact.getPhoneNumber()).isEqualTo(UPDATED_PHONE_NUMBER);
        assertThat(testEmergencyContact.getEmail()).isEqualTo(UPDATED_EMAIL);

        // Validate the EmergencyContact in Elasticsearch
        verify(mockEmergencyContactSearchRepository, times(1)).save(testEmergencyContact);
    }

    @Test
    @Transactional
    public void updateNonExistingEmergencyContact() throws Exception {
        int databaseSizeBeforeUpdate = emergencyContactRepository.findAll().size();

        // Create the EmergencyContact
        EmergencyContactDTO emergencyContactDTO = emergencyContactMapper.toDto(emergencyContact);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restEmergencyContactMockMvc
            .perform(
                put("/api/emergency-contacts")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(emergencyContactDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the EmergencyContact in the database
        List<EmergencyContact> emergencyContactList = emergencyContactRepository.findAll();
        assertThat(emergencyContactList).hasSize(databaseSizeBeforeUpdate);

        // Validate the EmergencyContact in Elasticsearch
        verify(mockEmergencyContactSearchRepository, times(0)).save(emergencyContact);
    }

    @Test
    @Transactional
    public void deleteEmergencyContact() throws Exception {
        // Initialize the database
        emergencyContactRepository.saveAndFlush(emergencyContact);

        int databaseSizeBeforeDelete = emergencyContactRepository.findAll().size();

        // Delete the emergencyContact
        restEmergencyContactMockMvc
            .perform(delete("/api/emergency-contacts/{id}", emergencyContact.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<EmergencyContact> emergencyContactList = emergencyContactRepository.findAll();
        assertThat(emergencyContactList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the EmergencyContact in Elasticsearch
        verify(mockEmergencyContactSearchRepository, times(1)).deleteById(emergencyContact.getId());
    }

    @Test
    @Transactional
    public void searchEmergencyContact() throws Exception {
        // Configure the mock search repository
        // Initialize the database
        emergencyContactRepository.saveAndFlush(emergencyContact);
        when(mockEmergencyContactSearchRepository.search(queryStringQuery("id:" + emergencyContact.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(emergencyContact), PageRequest.of(0, 1), 1));

        // Search the emergencyContact
        restEmergencyContactMockMvc
            .perform(get("/api/_search/emergency-contacts?query=id:" + emergencyContact.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(emergencyContact.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].relationToYou").value(hasItem(DEFAULT_RELATION_TO_YOU)))
            .andExpect(jsonPath("$.[*].phoneNumber").value(hasItem(DEFAULT_PHONE_NUMBER)))
            .andExpect(jsonPath("$.[*].email").value(hasItem(DEFAULT_EMAIL)));
    }
}
