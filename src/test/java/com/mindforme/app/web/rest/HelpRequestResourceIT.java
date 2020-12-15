package com.mindforme.app.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.elasticsearch.index.query.QueryBuilders.queryStringQuery;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.mindforme.app.MindformeApp;
import com.mindforme.app.domain.HelpRequest;
import com.mindforme.app.domain.enumeration.HelpLocation;
import com.mindforme.app.domain.enumeration.HelpRequestStatus;
import com.mindforme.app.domain.enumeration.HelpType;
import com.mindforme.app.domain.enumeration.Privacy;
import com.mindforme.app.domain.enumeration.Status;
import com.mindforme.app.repository.HelpRequestRepository;
import com.mindforme.app.repository.search.HelpRequestSearchRepository;
import com.mindforme.app.service.HelpRequestService;
import com.mindforme.app.service.dto.HelpRequestDTO;
import com.mindforme.app.service.mapper.HelpRequestMapper;
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
import org.springframework.util.Base64Utils;

/**
 * Integration tests for the {@link HelpRequestResource} REST controller.
 */
@SpringBootTest(classes = MindformeApp.class)
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
public class HelpRequestResourceIT {
    private static final String DEFAULT_TITLE = "AAAAAAAAAA";
    private static final String UPDATED_TITLE = "BBBBBBBBBB";

    private static final HelpType DEFAULT_TYPE = HelpType.CHILD;
    private static final HelpType UPDATED_TYPE = HelpType.PET;

    private static final String DEFAULT_ACCEPTANCE = "AAAAAAAAAA";
    private static final String UPDATED_ACCEPTANCE = "BBBBBBBBBB";

    private static final HelpRequestStatus DEFAULT_REQUEST_STATUS = HelpRequestStatus.NEW;
    private static final HelpRequestStatus UPDATED_REQUEST_STATUS = HelpRequestStatus.ACCEPTED;

    private static final Boolean DEFAULT_IS_OFFER = false;
    private static final Boolean UPDATED_IS_OFFER = true;

    private static final Privacy DEFAULT_OFFER_TO = Privacy.PUBLIC;
    private static final Privacy UPDATED_OFFER_TO = Privacy.FRIENDS;

    private static final String DEFAULT_MESSAGE = "AAAAAAAAAA";
    private static final String UPDATED_MESSAGE = "BBBBBBBBBB";

    private static final String DEFAULT_INSTRUCTION = "AAAAAAAAAA";
    private static final String UPDATED_INSTRUCTION = "BBBBBBBBBB";

    private static final Status DEFAULT_STATUS = Status.Active;
    private static final Status UPDATED_STATUS = Status.Inactive;

    private static final HelpLocation DEFAULT_LOCATION_TYPE = HelpLocation.OUR_OR_YOUR_HOME;
    private static final HelpLocation UPDATED_LOCATION_TYPE = HelpLocation.OUR_HOME;

    private static final Instant DEFAULT_REAL_START = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_REAL_START = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_REAL_END = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_REAL_END = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Integer DEFAULT_RATING = 1;
    private static final Integer UPDATED_RATING = 2;

    private static final String DEFAULT_REQUESTER_COMMENT = "AAAAAAAAAA";
    private static final String UPDATED_REQUESTER_COMMENT = "BBBBBBBBBB";

    private static final String DEFAULT_HELPER_COMMENT = "AAAAAAAAAA";
    private static final String UPDATED_HELPER_COMMENT = "BBBBBBBBBB";

    private static final Integer DEFAULT_FLAGGED = 1;
    private static final Integer UPDATED_FLAGGED = 2;

    private static final Float DEFAULT_COINS = 1F;
    private static final Float UPDATED_COINS = 2F;

    private static final Float DEFAULT_BONUS = 1F;
    private static final Float UPDATED_BONUS = 2F;

    @Autowired
    private HelpRequestRepository helpRequestRepository;

    @Autowired
    private HelpRequestMapper helpRequestMapper;

    @Autowired
    private HelpRequestService helpRequestService;

    /**
     * This repository is mocked in the com.mindforme.app.repository.search test package.
     *
     * @see com.mindforme.app.repository.search.HelpRequestSearchRepositoryMockConfiguration
     */
    @Autowired
    private HelpRequestSearchRepository mockHelpRequestSearchRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restHelpRequestMockMvc;

    private HelpRequest helpRequest;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static HelpRequest createEntity(EntityManager em) {
        HelpRequest helpRequest = new HelpRequest()
            .title(DEFAULT_TITLE)
            .type(DEFAULT_TYPE)
            .acceptance(DEFAULT_ACCEPTANCE)
            .requestStatus(DEFAULT_REQUEST_STATUS)
            .isOffer(DEFAULT_IS_OFFER)
            .offerTo(DEFAULT_OFFER_TO)
            .message(DEFAULT_MESSAGE)
            .instruction(DEFAULT_INSTRUCTION)
            .status(DEFAULT_STATUS)
            .locationType(DEFAULT_LOCATION_TYPE)
            .realStart(DEFAULT_REAL_START)
            .realEnd(DEFAULT_REAL_END)
            .rating(DEFAULT_RATING)
            .requesterComment(DEFAULT_REQUESTER_COMMENT)
            .helperComment(DEFAULT_HELPER_COMMENT)
            .flagged(DEFAULT_FLAGGED)
            .coins(DEFAULT_COINS)
            .bonus(DEFAULT_BONUS);
        return helpRequest;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static HelpRequest createUpdatedEntity(EntityManager em) {
        HelpRequest helpRequest = new HelpRequest()
            .title(UPDATED_TITLE)
            .type(UPDATED_TYPE)
            .acceptance(UPDATED_ACCEPTANCE)
            .requestStatus(UPDATED_REQUEST_STATUS)
            .isOffer(UPDATED_IS_OFFER)
            .offerTo(UPDATED_OFFER_TO)
            .message(UPDATED_MESSAGE)
            .instruction(UPDATED_INSTRUCTION)
            .status(UPDATED_STATUS)
            .locationType(UPDATED_LOCATION_TYPE)
            .realStart(UPDATED_REAL_START)
            .realEnd(UPDATED_REAL_END)
            .rating(UPDATED_RATING)
            .requesterComment(UPDATED_REQUESTER_COMMENT)
            .helperComment(UPDATED_HELPER_COMMENT)
            .flagged(UPDATED_FLAGGED)
            .coins(UPDATED_COINS)
            .bonus(UPDATED_BONUS);
        return helpRequest;
    }

    @BeforeEach
    public void initTest() {
        helpRequest = createEntity(em);
    }

    @Test
    @Transactional
    public void createHelpRequest() throws Exception {
        int databaseSizeBeforeCreate = helpRequestRepository.findAll().size();
        // Create the HelpRequest
        HelpRequestDTO helpRequestDTO = helpRequestMapper.toDto(helpRequest);
        restHelpRequestMockMvc
            .perform(
                post("/api/help-requests")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(helpRequestDTO))
            )
            .andExpect(status().isCreated());

        // Validate the HelpRequest in the database
        List<HelpRequest> helpRequestList = helpRequestRepository.findAll();
        assertThat(helpRequestList).hasSize(databaseSizeBeforeCreate + 1);
        HelpRequest testHelpRequest = helpRequestList.get(helpRequestList.size() - 1);
        assertThat(testHelpRequest.getTitle()).isEqualTo(DEFAULT_TITLE);
        assertThat(testHelpRequest.getType()).isEqualTo(DEFAULT_TYPE);
        assertThat(testHelpRequest.getAcceptance()).isEqualTo(DEFAULT_ACCEPTANCE);
        assertThat(testHelpRequest.getRequestStatus()).isEqualTo(DEFAULT_REQUEST_STATUS);
        assertThat(testHelpRequest.isIsOffer()).isEqualTo(DEFAULT_IS_OFFER);
        assertThat(testHelpRequest.getOfferTo()).isEqualTo(DEFAULT_OFFER_TO);
        assertThat(testHelpRequest.getMessage()).isEqualTo(DEFAULT_MESSAGE);
        assertThat(testHelpRequest.getInstruction()).isEqualTo(DEFAULT_INSTRUCTION);
        assertThat(testHelpRequest.getStatus()).isEqualTo(DEFAULT_STATUS);
        assertThat(testHelpRequest.getLocationType()).isEqualTo(DEFAULT_LOCATION_TYPE);
        assertThat(testHelpRequest.getRealStart()).isEqualTo(DEFAULT_REAL_START);
        assertThat(testHelpRequest.getRealEnd()).isEqualTo(DEFAULT_REAL_END);
        assertThat(testHelpRequest.getRating()).isEqualTo(DEFAULT_RATING);
        assertThat(testHelpRequest.getRequesterComment()).isEqualTo(DEFAULT_REQUESTER_COMMENT);
        assertThat(testHelpRequest.getHelperComment()).isEqualTo(DEFAULT_HELPER_COMMENT);
        assertThat(testHelpRequest.getFlagged()).isEqualTo(DEFAULT_FLAGGED);
        assertThat(testHelpRequest.getCoins()).isEqualTo(DEFAULT_COINS);
        assertThat(testHelpRequest.getBonus()).isEqualTo(DEFAULT_BONUS);

        // Validate the HelpRequest in Elasticsearch
        verify(mockHelpRequestSearchRepository, times(1)).save(testHelpRequest);
    }

    @Test
    @Transactional
    public void createHelpRequestWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = helpRequestRepository.findAll().size();

        // Create the HelpRequest with an existing ID
        helpRequest.setId(1L);
        HelpRequestDTO helpRequestDTO = helpRequestMapper.toDto(helpRequest);

        // An entity with an existing ID cannot be created, so this API call must fail
        restHelpRequestMockMvc
            .perform(
                post("/api/help-requests")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(helpRequestDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the HelpRequest in the database
        List<HelpRequest> helpRequestList = helpRequestRepository.findAll();
        assertThat(helpRequestList).hasSize(databaseSizeBeforeCreate);

        // Validate the HelpRequest in Elasticsearch
        verify(mockHelpRequestSearchRepository, times(0)).save(helpRequest);
    }

    @Test
    @Transactional
    public void getAllHelpRequests() throws Exception {
        // Initialize the database
        helpRequestRepository.saveAndFlush(helpRequest);

        // Get all the helpRequestList
        restHelpRequestMockMvc
            .perform(get("/api/help-requests?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(helpRequest.getId().intValue())))
            .andExpect(jsonPath("$.[*].title").value(hasItem(DEFAULT_TITLE)))
            .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE.toString())))
            .andExpect(jsonPath("$.[*].acceptance").value(hasItem(DEFAULT_ACCEPTANCE)))
            .andExpect(jsonPath("$.[*].requestStatus").value(hasItem(DEFAULT_REQUEST_STATUS.toString())))
            .andExpect(jsonPath("$.[*].isOffer").value(hasItem(DEFAULT_IS_OFFER.booleanValue())))
            .andExpect(jsonPath("$.[*].offerTo").value(hasItem(DEFAULT_OFFER_TO.toString())))
            .andExpect(jsonPath("$.[*].message").value(hasItem(DEFAULT_MESSAGE.toString())))
            .andExpect(jsonPath("$.[*].instruction").value(hasItem(DEFAULT_INSTRUCTION.toString())))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.toString())))
            .andExpect(jsonPath("$.[*].locationType").value(hasItem(DEFAULT_LOCATION_TYPE.toString())))
            .andExpect(jsonPath("$.[*].realStart").value(hasItem(DEFAULT_REAL_START.toString())))
            .andExpect(jsonPath("$.[*].realEnd").value(hasItem(DEFAULT_REAL_END.toString())))
            .andExpect(jsonPath("$.[*].rating").value(hasItem(DEFAULT_RATING)))
            .andExpect(jsonPath("$.[*].requesterComment").value(hasItem(DEFAULT_REQUESTER_COMMENT)))
            .andExpect(jsonPath("$.[*].helperComment").value(hasItem(DEFAULT_HELPER_COMMENT)))
            .andExpect(jsonPath("$.[*].flagged").value(hasItem(DEFAULT_FLAGGED)))
            .andExpect(jsonPath("$.[*].coins").value(hasItem(DEFAULT_COINS.doubleValue())))
            .andExpect(jsonPath("$.[*].bonus").value(hasItem(DEFAULT_BONUS.doubleValue())));
    }

    @Test
    @Transactional
    public void getHelpRequest() throws Exception {
        // Initialize the database
        helpRequestRepository.saveAndFlush(helpRequest);

        // Get the helpRequest
        restHelpRequestMockMvc
            .perform(get("/api/help-requests/{id}", helpRequest.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(helpRequest.getId().intValue()))
            .andExpect(jsonPath("$.title").value(DEFAULT_TITLE))
            .andExpect(jsonPath("$.type").value(DEFAULT_TYPE.toString()))
            .andExpect(jsonPath("$.acceptance").value(DEFAULT_ACCEPTANCE))
            .andExpect(jsonPath("$.requestStatus").value(DEFAULT_REQUEST_STATUS.toString()))
            .andExpect(jsonPath("$.isOffer").value(DEFAULT_IS_OFFER.booleanValue()))
            .andExpect(jsonPath("$.offerTo").value(DEFAULT_OFFER_TO.toString()))
            .andExpect(jsonPath("$.message").value(DEFAULT_MESSAGE.toString()))
            .andExpect(jsonPath("$.instruction").value(DEFAULT_INSTRUCTION.toString()))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS.toString()))
            .andExpect(jsonPath("$.locationType").value(DEFAULT_LOCATION_TYPE.toString()))
            .andExpect(jsonPath("$.realStart").value(DEFAULT_REAL_START.toString()))
            .andExpect(jsonPath("$.realEnd").value(DEFAULT_REAL_END.toString()))
            .andExpect(jsonPath("$.rating").value(DEFAULT_RATING))
            .andExpect(jsonPath("$.requesterComment").value(DEFAULT_REQUESTER_COMMENT))
            .andExpect(jsonPath("$.helperComment").value(DEFAULT_HELPER_COMMENT))
            .andExpect(jsonPath("$.flagged").value(DEFAULT_FLAGGED))
            .andExpect(jsonPath("$.coins").value(DEFAULT_COINS.doubleValue()))
            .andExpect(jsonPath("$.bonus").value(DEFAULT_BONUS.doubleValue()));
    }

    @Test
    @Transactional
    public void getNonExistingHelpRequest() throws Exception {
        // Get the helpRequest
        restHelpRequestMockMvc.perform(get("/api/help-requests/{id}", Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateHelpRequest() throws Exception {
        // Initialize the database
        helpRequestRepository.saveAndFlush(helpRequest);

        int databaseSizeBeforeUpdate = helpRequestRepository.findAll().size();

        // Update the helpRequest
        HelpRequest updatedHelpRequest = helpRequestRepository.findById(helpRequest.getId()).get();
        // Disconnect from session so that the updates on updatedHelpRequest are not directly saved in db
        em.detach(updatedHelpRequest);
        updatedHelpRequest
            .title(UPDATED_TITLE)
            .type(UPDATED_TYPE)
            .acceptance(UPDATED_ACCEPTANCE)
            .requestStatus(UPDATED_REQUEST_STATUS)
            .isOffer(UPDATED_IS_OFFER)
            .offerTo(UPDATED_OFFER_TO)
            .message(UPDATED_MESSAGE)
            .instruction(UPDATED_INSTRUCTION)
            .status(UPDATED_STATUS)
            .locationType(UPDATED_LOCATION_TYPE)
            .realStart(UPDATED_REAL_START)
            .realEnd(UPDATED_REAL_END)
            .rating(UPDATED_RATING)
            .requesterComment(UPDATED_REQUESTER_COMMENT)
            .helperComment(UPDATED_HELPER_COMMENT)
            .flagged(UPDATED_FLAGGED)
            .coins(UPDATED_COINS)
            .bonus(UPDATED_BONUS);
        HelpRequestDTO helpRequestDTO = helpRequestMapper.toDto(updatedHelpRequest);

        restHelpRequestMockMvc
            .perform(
                put("/api/help-requests").contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(helpRequestDTO))
            )
            .andExpect(status().isOk());

        // Validate the HelpRequest in the database
        List<HelpRequest> helpRequestList = helpRequestRepository.findAll();
        assertThat(helpRequestList).hasSize(databaseSizeBeforeUpdate);
        HelpRequest testHelpRequest = helpRequestList.get(helpRequestList.size() - 1);
        assertThat(testHelpRequest.getTitle()).isEqualTo(UPDATED_TITLE);
        assertThat(testHelpRequest.getType()).isEqualTo(UPDATED_TYPE);
        assertThat(testHelpRequest.getAcceptance()).isEqualTo(UPDATED_ACCEPTANCE);
        assertThat(testHelpRequest.getRequestStatus()).isEqualTo(UPDATED_REQUEST_STATUS);
        assertThat(testHelpRequest.isIsOffer()).isEqualTo(UPDATED_IS_OFFER);
        assertThat(testHelpRequest.getOfferTo()).isEqualTo(UPDATED_OFFER_TO);
        assertThat(testHelpRequest.getMessage()).isEqualTo(UPDATED_MESSAGE);
        assertThat(testHelpRequest.getInstruction()).isEqualTo(UPDATED_INSTRUCTION);
        assertThat(testHelpRequest.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testHelpRequest.getLocationType()).isEqualTo(UPDATED_LOCATION_TYPE);
        assertThat(testHelpRequest.getRealStart()).isEqualTo(UPDATED_REAL_START);
        assertThat(testHelpRequest.getRealEnd()).isEqualTo(UPDATED_REAL_END);
        assertThat(testHelpRequest.getRating()).isEqualTo(UPDATED_RATING);
        assertThat(testHelpRequest.getRequesterComment()).isEqualTo(UPDATED_REQUESTER_COMMENT);
        assertThat(testHelpRequest.getHelperComment()).isEqualTo(UPDATED_HELPER_COMMENT);
        assertThat(testHelpRequest.getFlagged()).isEqualTo(UPDATED_FLAGGED);
        assertThat(testHelpRequest.getCoins()).isEqualTo(UPDATED_COINS);
        assertThat(testHelpRequest.getBonus()).isEqualTo(UPDATED_BONUS);

        // Validate the HelpRequest in Elasticsearch
        verify(mockHelpRequestSearchRepository, times(1)).save(testHelpRequest);
    }

    @Test
    @Transactional
    public void updateNonExistingHelpRequest() throws Exception {
        int databaseSizeBeforeUpdate = helpRequestRepository.findAll().size();

        // Create the HelpRequest
        HelpRequestDTO helpRequestDTO = helpRequestMapper.toDto(helpRequest);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restHelpRequestMockMvc
            .perform(
                put("/api/help-requests").contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(helpRequestDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the HelpRequest in the database
        List<HelpRequest> helpRequestList = helpRequestRepository.findAll();
        assertThat(helpRequestList).hasSize(databaseSizeBeforeUpdate);

        // Validate the HelpRequest in Elasticsearch
        verify(mockHelpRequestSearchRepository, times(0)).save(helpRequest);
    }

    @Test
    @Transactional
    public void deleteHelpRequest() throws Exception {
        // Initialize the database
        helpRequestRepository.saveAndFlush(helpRequest);

        int databaseSizeBeforeDelete = helpRequestRepository.findAll().size();

        // Delete the helpRequest
        restHelpRequestMockMvc
            .perform(delete("/api/help-requests/{id}", helpRequest.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<HelpRequest> helpRequestList = helpRequestRepository.findAll();
        assertThat(helpRequestList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the HelpRequest in Elasticsearch
        verify(mockHelpRequestSearchRepository, times(1)).deleteById(helpRequest.getId());
    }

    @Test
    @Transactional
    public void searchHelpRequest() throws Exception {
        // Configure the mock search repository
        // Initialize the database
        helpRequestRepository.saveAndFlush(helpRequest);
        when(mockHelpRequestSearchRepository.search(queryStringQuery("id:" + helpRequest.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(helpRequest), PageRequest.of(0, 1), 1));

        // Search the helpRequest
        restHelpRequestMockMvc
            .perform(get("/api/_search/help-requests?query=id:" + helpRequest.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(helpRequest.getId().intValue())))
            .andExpect(jsonPath("$.[*].title").value(hasItem(DEFAULT_TITLE)))
            .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE.toString())))
            .andExpect(jsonPath("$.[*].acceptance").value(hasItem(DEFAULT_ACCEPTANCE)))
            .andExpect(jsonPath("$.[*].requestStatus").value(hasItem(DEFAULT_REQUEST_STATUS.toString())))
            .andExpect(jsonPath("$.[*].isOffer").value(hasItem(DEFAULT_IS_OFFER.booleanValue())))
            .andExpect(jsonPath("$.[*].offerTo").value(hasItem(DEFAULT_OFFER_TO.toString())))
            .andExpect(jsonPath("$.[*].message").value(hasItem(DEFAULT_MESSAGE.toString())))
            .andExpect(jsonPath("$.[*].instruction").value(hasItem(DEFAULT_INSTRUCTION.toString())))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.toString())))
            .andExpect(jsonPath("$.[*].locationType").value(hasItem(DEFAULT_LOCATION_TYPE.toString())))
            .andExpect(jsonPath("$.[*].realStart").value(hasItem(DEFAULT_REAL_START.toString())))
            .andExpect(jsonPath("$.[*].realEnd").value(hasItem(DEFAULT_REAL_END.toString())))
            .andExpect(jsonPath("$.[*].rating").value(hasItem(DEFAULT_RATING)))
            .andExpect(jsonPath("$.[*].requesterComment").value(hasItem(DEFAULT_REQUESTER_COMMENT)))
            .andExpect(jsonPath("$.[*].helperComment").value(hasItem(DEFAULT_HELPER_COMMENT)))
            .andExpect(jsonPath("$.[*].flagged").value(hasItem(DEFAULT_FLAGGED)))
            .andExpect(jsonPath("$.[*].coins").value(hasItem(DEFAULT_COINS.doubleValue())))
            .andExpect(jsonPath("$.[*].bonus").value(hasItem(DEFAULT_BONUS.doubleValue())));
    }
}
