package com.mindforme.app.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.elasticsearch.index.query.QueryBuilders.queryStringQuery;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.mindforme.app.MindformeApp;
import com.mindforme.app.domain.Invitation;
import com.mindforme.app.repository.InvitationRepository;
import com.mindforme.app.repository.search.InvitationSearchRepository;
import com.mindforme.app.service.InvitationService;
import com.mindforme.app.service.dto.InvitationDTO;
import com.mindforme.app.service.mapper.InvitationMapper;
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
 * Integration tests for the {@link InvitationResource} REST controller.
 */
@SpringBootTest(classes = MindformeApp.class)
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
public class InvitationResourceIT {
    private static final String DEFAULT_EMAIL = "AAAAAAAAAA";
    private static final String UPDATED_EMAIL = "BBBBBBBBBB";

    private static final String DEFAULT_FACEBOOK = "AAAAAAAAAA";
    private static final String UPDATED_FACEBOOK = "BBBBBBBBBB";

    private static final Instant DEFAULT_FREE_PERIOD = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_FREE_PERIOD = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    @Autowired
    private InvitationRepository invitationRepository;

    @Autowired
    private InvitationMapper invitationMapper;

    @Autowired
    private InvitationService invitationService;

    /**
     * This repository is mocked in the com.mindforme.app.repository.search test package.
     *
     * @see com.mindforme.app.repository.search.InvitationSearchRepositoryMockConfiguration
     */
    @Autowired
    private InvitationSearchRepository mockInvitationSearchRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restInvitationMockMvc;

    private Invitation invitation;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Invitation createEntity(EntityManager em) {
        Invitation invitation = new Invitation().email(DEFAULT_EMAIL).facebook(DEFAULT_FACEBOOK).freePeriod(DEFAULT_FREE_PERIOD);
        return invitation;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Invitation createUpdatedEntity(EntityManager em) {
        Invitation invitation = new Invitation().email(UPDATED_EMAIL).facebook(UPDATED_FACEBOOK).freePeriod(UPDATED_FREE_PERIOD);
        return invitation;
    }

    @BeforeEach
    public void initTest() {
        invitation = createEntity(em);
    }

    @Test
    @Transactional
    public void createInvitation() throws Exception {
        int databaseSizeBeforeCreate = invitationRepository.findAll().size();
        // Create the Invitation
        InvitationDTO invitationDTO = invitationMapper.toDto(invitation);
        restInvitationMockMvc
            .perform(
                post("/api/invitations").contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(invitationDTO))
            )
            .andExpect(status().isCreated());

        // Validate the Invitation in the database
        List<Invitation> invitationList = invitationRepository.findAll();
        assertThat(invitationList).hasSize(databaseSizeBeforeCreate + 1);
        Invitation testInvitation = invitationList.get(invitationList.size() - 1);
        assertThat(testInvitation.getEmail()).isEqualTo(DEFAULT_EMAIL);
        assertThat(testInvitation.getFacebook()).isEqualTo(DEFAULT_FACEBOOK);
        assertThat(testInvitation.getFreePeriod()).isEqualTo(DEFAULT_FREE_PERIOD);

        // Validate the Invitation in Elasticsearch
        verify(mockInvitationSearchRepository, times(1)).save(testInvitation);
    }

    @Test
    @Transactional
    public void createInvitationWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = invitationRepository.findAll().size();

        // Create the Invitation with an existing ID
        invitation.setId(1L);
        InvitationDTO invitationDTO = invitationMapper.toDto(invitation);

        // An entity with an existing ID cannot be created, so this API call must fail
        restInvitationMockMvc
            .perform(
                post("/api/invitations").contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(invitationDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Invitation in the database
        List<Invitation> invitationList = invitationRepository.findAll();
        assertThat(invitationList).hasSize(databaseSizeBeforeCreate);

        // Validate the Invitation in Elasticsearch
        verify(mockInvitationSearchRepository, times(0)).save(invitation);
    }

    @Test
    @Transactional
    public void getAllInvitations() throws Exception {
        // Initialize the database
        invitationRepository.saveAndFlush(invitation);

        // Get all the invitationList
        restInvitationMockMvc
            .perform(get("/api/invitations?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(invitation.getId().intValue())))
            .andExpect(jsonPath("$.[*].email").value(hasItem(DEFAULT_EMAIL)))
            .andExpect(jsonPath("$.[*].facebook").value(hasItem(DEFAULT_FACEBOOK)))
            .andExpect(jsonPath("$.[*].freePeriod").value(hasItem(DEFAULT_FREE_PERIOD.toString())));
    }

    @Test
    @Transactional
    public void getInvitation() throws Exception {
        // Initialize the database
        invitationRepository.saveAndFlush(invitation);

        // Get the invitation
        restInvitationMockMvc
            .perform(get("/api/invitations/{id}", invitation.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(invitation.getId().intValue()))
            .andExpect(jsonPath("$.email").value(DEFAULT_EMAIL))
            .andExpect(jsonPath("$.facebook").value(DEFAULT_FACEBOOK))
            .andExpect(jsonPath("$.freePeriod").value(DEFAULT_FREE_PERIOD.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingInvitation() throws Exception {
        // Get the invitation
        restInvitationMockMvc.perform(get("/api/invitations/{id}", Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateInvitation() throws Exception {
        // Initialize the database
        invitationRepository.saveAndFlush(invitation);

        int databaseSizeBeforeUpdate = invitationRepository.findAll().size();

        // Update the invitation
        Invitation updatedInvitation = invitationRepository.findById(invitation.getId()).get();
        // Disconnect from session so that the updates on updatedInvitation are not directly saved in db
        em.detach(updatedInvitation);
        updatedInvitation.email(UPDATED_EMAIL).facebook(UPDATED_FACEBOOK).freePeriod(UPDATED_FREE_PERIOD);
        InvitationDTO invitationDTO = invitationMapper.toDto(updatedInvitation);

        restInvitationMockMvc
            .perform(
                put("/api/invitations").contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(invitationDTO))
            )
            .andExpect(status().isOk());

        // Validate the Invitation in the database
        List<Invitation> invitationList = invitationRepository.findAll();
        assertThat(invitationList).hasSize(databaseSizeBeforeUpdate);
        Invitation testInvitation = invitationList.get(invitationList.size() - 1);
        assertThat(testInvitation.getEmail()).isEqualTo(UPDATED_EMAIL);
        assertThat(testInvitation.getFacebook()).isEqualTo(UPDATED_FACEBOOK);
        assertThat(testInvitation.getFreePeriod()).isEqualTo(UPDATED_FREE_PERIOD);

        // Validate the Invitation in Elasticsearch
        verify(mockInvitationSearchRepository, times(1)).save(testInvitation);
    }

    @Test
    @Transactional
    public void updateNonExistingInvitation() throws Exception {
        int databaseSizeBeforeUpdate = invitationRepository.findAll().size();

        // Create the Invitation
        InvitationDTO invitationDTO = invitationMapper.toDto(invitation);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restInvitationMockMvc
            .perform(
                put("/api/invitations").contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(invitationDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Invitation in the database
        List<Invitation> invitationList = invitationRepository.findAll();
        assertThat(invitationList).hasSize(databaseSizeBeforeUpdate);

        // Validate the Invitation in Elasticsearch
        verify(mockInvitationSearchRepository, times(0)).save(invitation);
    }

    @Test
    @Transactional
    public void deleteInvitation() throws Exception {
        // Initialize the database
        invitationRepository.saveAndFlush(invitation);

        int databaseSizeBeforeDelete = invitationRepository.findAll().size();

        // Delete the invitation
        restInvitationMockMvc
            .perform(delete("/api/invitations/{id}", invitation.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Invitation> invitationList = invitationRepository.findAll();
        assertThat(invitationList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the Invitation in Elasticsearch
        verify(mockInvitationSearchRepository, times(1)).deleteById(invitation.getId());
    }

    @Test
    @Transactional
    public void searchInvitation() throws Exception {
        // Configure the mock search repository
        // Initialize the database
        invitationRepository.saveAndFlush(invitation);
        when(mockInvitationSearchRepository.search(queryStringQuery("id:" + invitation.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(invitation), PageRequest.of(0, 1), 1));

        // Search the invitation
        restInvitationMockMvc
            .perform(get("/api/_search/invitations?query=id:" + invitation.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(invitation.getId().intValue())))
            .andExpect(jsonPath("$.[*].email").value(hasItem(DEFAULT_EMAIL)))
            .andExpect(jsonPath("$.[*].facebook").value(hasItem(DEFAULT_FACEBOOK)))
            .andExpect(jsonPath("$.[*].freePeriod").value(hasItem(DEFAULT_FREE_PERIOD.toString())));
    }
}
