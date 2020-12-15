package com.mindforme.app.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.elasticsearch.index.query.QueryBuilders.queryStringQuery;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.mindforme.app.MindformeApp;
import com.mindforme.app.domain.HouseHelpRequest;
import com.mindforme.app.domain.enumeration.Equipment;
import com.mindforme.app.domain.enumeration.Equipment;
import com.mindforme.app.domain.enumeration.HouseProvided;
import com.mindforme.app.repository.HouseHelpRequestRepository;
import com.mindforme.app.repository.search.HouseHelpRequestSearchRepository;
import com.mindforme.app.service.HouseHelpRequestService;
import com.mindforme.app.service.dto.HouseHelpRequestDTO;
import com.mindforme.app.service.mapper.HouseHelpRequestMapper;
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
 * Integration tests for the {@link HouseHelpRequestResource} REST controller.
 */
@SpringBootTest(classes = MindformeApp.class)
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
public class HouseHelpRequestResourceIT {
    private static final String DEFAULT_SERVICES = "AAAAAAAAAA";
    private static final String UPDATED_SERVICES = "BBBBBBBBBB";

    private static final Long DEFAULT_CLEANING_TIME = 1L;
    private static final Long UPDATED_CLEANING_TIME = 2L;

    private static final Instant DEFAULT_CLEANING_FROM_TIME = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_CLEANING_FROM_TIME = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_CLEANING_TO_TIME = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_CLEANING_TO_TIME = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Equipment DEFAULT_CLEANING_EQUIPMENT = Equipment.OURS;
    private static final Equipment UPDATED_CLEANING_EQUIPMENT = Equipment.YOURS;

    private static final String DEFAULT_CLEANING_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_CLEANING_DESCRIPTION = "BBBBBBBBBB";

    private static final Instant DEFAULT_COOKING_FROM_TIME = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_COOKING_FROM_TIME = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_COOKING_TO_TIME = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_COOKING_TO_TIME = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Integer DEFAULT_COOKING_SERVES = 1;
    private static final Integer UPDATED_COOKING_SERVES = 2;

    private static final String DEFAULT_COOKING_DATA = "AAAAAAAAAA";
    private static final String UPDATED_COOKING_DATA = "BBBBBBBBBB";

    private static final Integer DEFAULT_PICKUP_TYPE = 1;
    private static final Integer UPDATED_PICKUP_TYPE = 2;

    private static final String DEFAULT_HOUSE_MINDING_DETAIL = "AAAAAAAAAA";
    private static final String UPDATED_HOUSE_MINDING_DETAIL = "BBBBBBBBBB";

    private static final Instant DEFAULT_MAIL_FROM_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_MAIL_FROM_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_MAIL_TO_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_MAIL_TO_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_MAIL_AFTER = "AAAAAAAAAA";
    private static final String UPDATED_MAIL_AFTER = "BBBBBBBBBB";

    private static final String DEFAULT_MAIL_COLLECTION_DAYS = "AAAAAAAAAA";
    private static final String UPDATED_MAIL_COLLECTION_DAYS = "BBBBBBBBBB";

    private static final String DEFAULT_OTHER_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_OTHER_DESCRIPTION = "BBBBBBBBBB";

    private static final Long DEFAULT_OTHER_HOURS = 1L;
    private static final Long UPDATED_OTHER_HOURS = 2L;

    private static final Instant DEFAULT_OTHER_FROM_TIME = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_OTHER_FROM_TIME = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_OTHER_TO_TIME = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_OTHER_TO_TIME = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Equipment DEFAULT_OTHER_EQUIPMENT = Equipment.OURS;
    private static final Equipment UPDATED_OTHER_EQUIPMENT = Equipment.YOURS;

    private static final Long DEFAULT_PROVIDE_FOR = 1L;
    private static final Long UPDATED_PROVIDE_FOR = 2L;

    private static final HouseProvided DEFAULT_PROVIDE_TYPE = HouseProvided.FAMILY;
    private static final HouseProvided UPDATED_PROVIDE_TYPE = HouseProvided.SUPPORTED;

    @Autowired
    private HouseHelpRequestRepository houseHelpRequestRepository;

    @Autowired
    private HouseHelpRequestMapper houseHelpRequestMapper;

    @Autowired
    private HouseHelpRequestService houseHelpRequestService;

    /**
     * This repository is mocked in the com.mindforme.app.repository.search test package.
     *
     * @see com.mindforme.app.repository.search.HouseHelpRequestSearchRepositoryMockConfiguration
     */
    @Autowired
    private HouseHelpRequestSearchRepository mockHouseHelpRequestSearchRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restHouseHelpRequestMockMvc;

    private HouseHelpRequest houseHelpRequest;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static HouseHelpRequest createEntity(EntityManager em) {
        HouseHelpRequest houseHelpRequest = new HouseHelpRequest()
            .services(DEFAULT_SERVICES)
            .cleaningTime(DEFAULT_CLEANING_TIME)
            .cleaningFromTime(DEFAULT_CLEANING_FROM_TIME)
            .cleaningToTime(DEFAULT_CLEANING_TO_TIME)
            .cleaningEquipment(DEFAULT_CLEANING_EQUIPMENT)
            .cleaningDescription(DEFAULT_CLEANING_DESCRIPTION)
            .cookingFromTime(DEFAULT_COOKING_FROM_TIME)
            .cookingToTime(DEFAULT_COOKING_TO_TIME)
            .cookingServes(DEFAULT_COOKING_SERVES)
            .cookingData(DEFAULT_COOKING_DATA)
            .pickupType(DEFAULT_PICKUP_TYPE)
            .houseMindingDetail(DEFAULT_HOUSE_MINDING_DETAIL)
            .mailFromDate(DEFAULT_MAIL_FROM_DATE)
            .mailToDate(DEFAULT_MAIL_TO_DATE)
            .mailAfter(DEFAULT_MAIL_AFTER)
            .mailCollectionDays(DEFAULT_MAIL_COLLECTION_DAYS)
            .otherDescription(DEFAULT_OTHER_DESCRIPTION)
            .otherHours(DEFAULT_OTHER_HOURS)
            .otherFromTime(DEFAULT_OTHER_FROM_TIME)
            .otherToTime(DEFAULT_OTHER_TO_TIME)
            .otherEquipment(DEFAULT_OTHER_EQUIPMENT)
            .provideFor(DEFAULT_PROVIDE_FOR)
            .provideType(DEFAULT_PROVIDE_TYPE);
        return houseHelpRequest;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static HouseHelpRequest createUpdatedEntity(EntityManager em) {
        HouseHelpRequest houseHelpRequest = new HouseHelpRequest()
            .services(UPDATED_SERVICES)
            .cleaningTime(UPDATED_CLEANING_TIME)
            .cleaningFromTime(UPDATED_CLEANING_FROM_TIME)
            .cleaningToTime(UPDATED_CLEANING_TO_TIME)
            .cleaningEquipment(UPDATED_CLEANING_EQUIPMENT)
            .cleaningDescription(UPDATED_CLEANING_DESCRIPTION)
            .cookingFromTime(UPDATED_COOKING_FROM_TIME)
            .cookingToTime(UPDATED_COOKING_TO_TIME)
            .cookingServes(UPDATED_COOKING_SERVES)
            .cookingData(UPDATED_COOKING_DATA)
            .pickupType(UPDATED_PICKUP_TYPE)
            .houseMindingDetail(UPDATED_HOUSE_MINDING_DETAIL)
            .mailFromDate(UPDATED_MAIL_FROM_DATE)
            .mailToDate(UPDATED_MAIL_TO_DATE)
            .mailAfter(UPDATED_MAIL_AFTER)
            .mailCollectionDays(UPDATED_MAIL_COLLECTION_DAYS)
            .otherDescription(UPDATED_OTHER_DESCRIPTION)
            .otherHours(UPDATED_OTHER_HOURS)
            .otherFromTime(UPDATED_OTHER_FROM_TIME)
            .otherToTime(UPDATED_OTHER_TO_TIME)
            .otherEquipment(UPDATED_OTHER_EQUIPMENT)
            .provideFor(UPDATED_PROVIDE_FOR)
            .provideType(UPDATED_PROVIDE_TYPE);
        return houseHelpRequest;
    }

    @BeforeEach
    public void initTest() {
        houseHelpRequest = createEntity(em);
    }

    @Test
    @Transactional
    public void createHouseHelpRequest() throws Exception {
        int databaseSizeBeforeCreate = houseHelpRequestRepository.findAll().size();
        // Create the HouseHelpRequest
        HouseHelpRequestDTO houseHelpRequestDTO = houseHelpRequestMapper.toDto(houseHelpRequest);
        restHouseHelpRequestMockMvc
            .perform(
                post("/api/house-help-requests")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(houseHelpRequestDTO))
            )
            .andExpect(status().isCreated());

        // Validate the HouseHelpRequest in the database
        List<HouseHelpRequest> houseHelpRequestList = houseHelpRequestRepository.findAll();
        assertThat(houseHelpRequestList).hasSize(databaseSizeBeforeCreate + 1);
        HouseHelpRequest testHouseHelpRequest = houseHelpRequestList.get(houseHelpRequestList.size() - 1);
        assertThat(testHouseHelpRequest.getServices()).isEqualTo(DEFAULT_SERVICES);
        assertThat(testHouseHelpRequest.getCleaningTime()).isEqualTo(DEFAULT_CLEANING_TIME);
        assertThat(testHouseHelpRequest.getCleaningFromTime()).isEqualTo(DEFAULT_CLEANING_FROM_TIME);
        assertThat(testHouseHelpRequest.getCleaningToTime()).isEqualTo(DEFAULT_CLEANING_TO_TIME);
        assertThat(testHouseHelpRequest.getCleaningEquipment()).isEqualTo(DEFAULT_CLEANING_EQUIPMENT);
        assertThat(testHouseHelpRequest.getCleaningDescription()).isEqualTo(DEFAULT_CLEANING_DESCRIPTION);
        assertThat(testHouseHelpRequest.getCookingFromTime()).isEqualTo(DEFAULT_COOKING_FROM_TIME);
        assertThat(testHouseHelpRequest.getCookingToTime()).isEqualTo(DEFAULT_COOKING_TO_TIME);
        assertThat(testHouseHelpRequest.getCookingServes()).isEqualTo(DEFAULT_COOKING_SERVES);
        assertThat(testHouseHelpRequest.getCookingData()).isEqualTo(DEFAULT_COOKING_DATA);
        assertThat(testHouseHelpRequest.getPickupType()).isEqualTo(DEFAULT_PICKUP_TYPE);
        assertThat(testHouseHelpRequest.getHouseMindingDetail()).isEqualTo(DEFAULT_HOUSE_MINDING_DETAIL);
        assertThat(testHouseHelpRequest.getMailFromDate()).isEqualTo(DEFAULT_MAIL_FROM_DATE);
        assertThat(testHouseHelpRequest.getMailToDate()).isEqualTo(DEFAULT_MAIL_TO_DATE);
        assertThat(testHouseHelpRequest.getMailAfter()).isEqualTo(DEFAULT_MAIL_AFTER);
        assertThat(testHouseHelpRequest.getMailCollectionDays()).isEqualTo(DEFAULT_MAIL_COLLECTION_DAYS);
        assertThat(testHouseHelpRequest.getOtherDescription()).isEqualTo(DEFAULT_OTHER_DESCRIPTION);
        assertThat(testHouseHelpRequest.getOtherHours()).isEqualTo(DEFAULT_OTHER_HOURS);
        assertThat(testHouseHelpRequest.getOtherFromTime()).isEqualTo(DEFAULT_OTHER_FROM_TIME);
        assertThat(testHouseHelpRequest.getOtherToTime()).isEqualTo(DEFAULT_OTHER_TO_TIME);
        assertThat(testHouseHelpRequest.getOtherEquipment()).isEqualTo(DEFAULT_OTHER_EQUIPMENT);
        assertThat(testHouseHelpRequest.getProvideFor()).isEqualTo(DEFAULT_PROVIDE_FOR);
        assertThat(testHouseHelpRequest.getProvideType()).isEqualTo(DEFAULT_PROVIDE_TYPE);

        // Validate the HouseHelpRequest in Elasticsearch
        verify(mockHouseHelpRequestSearchRepository, times(1)).save(testHouseHelpRequest);
    }

    @Test
    @Transactional
    public void createHouseHelpRequestWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = houseHelpRequestRepository.findAll().size();

        // Create the HouseHelpRequest with an existing ID
        houseHelpRequest.setId(1L);
        HouseHelpRequestDTO houseHelpRequestDTO = houseHelpRequestMapper.toDto(houseHelpRequest);

        // An entity with an existing ID cannot be created, so this API call must fail
        restHouseHelpRequestMockMvc
            .perform(
                post("/api/house-help-requests")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(houseHelpRequestDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the HouseHelpRequest in the database
        List<HouseHelpRequest> houseHelpRequestList = houseHelpRequestRepository.findAll();
        assertThat(houseHelpRequestList).hasSize(databaseSizeBeforeCreate);

        // Validate the HouseHelpRequest in Elasticsearch
        verify(mockHouseHelpRequestSearchRepository, times(0)).save(houseHelpRequest);
    }

    @Test
    @Transactional
    public void checkPickupTypeIsRequired() throws Exception {
        int databaseSizeBeforeTest = houseHelpRequestRepository.findAll().size();
        // set the field null
        houseHelpRequest.setPickupType(null);

        // Create the HouseHelpRequest, which fails.
        HouseHelpRequestDTO houseHelpRequestDTO = houseHelpRequestMapper.toDto(houseHelpRequest);

        restHouseHelpRequestMockMvc
            .perform(
                post("/api/house-help-requests")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(houseHelpRequestDTO))
            )
            .andExpect(status().isBadRequest());

        List<HouseHelpRequest> houseHelpRequestList = houseHelpRequestRepository.findAll();
        assertThat(houseHelpRequestList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllHouseHelpRequests() throws Exception {
        // Initialize the database
        houseHelpRequestRepository.saveAndFlush(houseHelpRequest);

        // Get all the houseHelpRequestList
        restHouseHelpRequestMockMvc
            .perform(get("/api/house-help-requests?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(houseHelpRequest.getId().intValue())))
            .andExpect(jsonPath("$.[*].services").value(hasItem(DEFAULT_SERVICES.toString())))
            .andExpect(jsonPath("$.[*].cleaningTime").value(hasItem(DEFAULT_CLEANING_TIME.intValue())))
            .andExpect(jsonPath("$.[*].cleaningFromTime").value(hasItem(DEFAULT_CLEANING_FROM_TIME.toString())))
            .andExpect(jsonPath("$.[*].cleaningToTime").value(hasItem(DEFAULT_CLEANING_TO_TIME.toString())))
            .andExpect(jsonPath("$.[*].cleaningEquipment").value(hasItem(DEFAULT_CLEANING_EQUIPMENT.toString())))
            .andExpect(jsonPath("$.[*].cleaningDescription").value(hasItem(DEFAULT_CLEANING_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].cookingFromTime").value(hasItem(DEFAULT_COOKING_FROM_TIME.toString())))
            .andExpect(jsonPath("$.[*].cookingToTime").value(hasItem(DEFAULT_COOKING_TO_TIME.toString())))
            .andExpect(jsonPath("$.[*].cookingServes").value(hasItem(DEFAULT_COOKING_SERVES)))
            .andExpect(jsonPath("$.[*].cookingData").value(hasItem(DEFAULT_COOKING_DATA)))
            .andExpect(jsonPath("$.[*].pickupType").value(hasItem(DEFAULT_PICKUP_TYPE)))
            .andExpect(jsonPath("$.[*].houseMindingDetail").value(hasItem(DEFAULT_HOUSE_MINDING_DETAIL.toString())))
            .andExpect(jsonPath("$.[*].mailFromDate").value(hasItem(DEFAULT_MAIL_FROM_DATE.toString())))
            .andExpect(jsonPath("$.[*].mailToDate").value(hasItem(DEFAULT_MAIL_TO_DATE.toString())))
            .andExpect(jsonPath("$.[*].mailAfter").value(hasItem(DEFAULT_MAIL_AFTER)))
            .andExpect(jsonPath("$.[*].mailCollectionDays").value(hasItem(DEFAULT_MAIL_COLLECTION_DAYS)))
            .andExpect(jsonPath("$.[*].otherDescription").value(hasItem(DEFAULT_OTHER_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].otherHours").value(hasItem(DEFAULT_OTHER_HOURS.intValue())))
            .andExpect(jsonPath("$.[*].otherFromTime").value(hasItem(DEFAULT_OTHER_FROM_TIME.toString())))
            .andExpect(jsonPath("$.[*].otherToTime").value(hasItem(DEFAULT_OTHER_TO_TIME.toString())))
            .andExpect(jsonPath("$.[*].otherEquipment").value(hasItem(DEFAULT_OTHER_EQUIPMENT.toString())))
            .andExpect(jsonPath("$.[*].provideFor").value(hasItem(DEFAULT_PROVIDE_FOR.intValue())))
            .andExpect(jsonPath("$.[*].provideType").value(hasItem(DEFAULT_PROVIDE_TYPE.toString())));
    }

    @Test
    @Transactional
    public void getHouseHelpRequest() throws Exception {
        // Initialize the database
        houseHelpRequestRepository.saveAndFlush(houseHelpRequest);

        // Get the houseHelpRequest
        restHouseHelpRequestMockMvc
            .perform(get("/api/house-help-requests/{id}", houseHelpRequest.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(houseHelpRequest.getId().intValue()))
            .andExpect(jsonPath("$.services").value(DEFAULT_SERVICES.toString()))
            .andExpect(jsonPath("$.cleaningTime").value(DEFAULT_CLEANING_TIME.intValue()))
            .andExpect(jsonPath("$.cleaningFromTime").value(DEFAULT_CLEANING_FROM_TIME.toString()))
            .andExpect(jsonPath("$.cleaningToTime").value(DEFAULT_CLEANING_TO_TIME.toString()))
            .andExpect(jsonPath("$.cleaningEquipment").value(DEFAULT_CLEANING_EQUIPMENT.toString()))
            .andExpect(jsonPath("$.cleaningDescription").value(DEFAULT_CLEANING_DESCRIPTION))
            .andExpect(jsonPath("$.cookingFromTime").value(DEFAULT_COOKING_FROM_TIME.toString()))
            .andExpect(jsonPath("$.cookingToTime").value(DEFAULT_COOKING_TO_TIME.toString()))
            .andExpect(jsonPath("$.cookingServes").value(DEFAULT_COOKING_SERVES))
            .andExpect(jsonPath("$.cookingData").value(DEFAULT_COOKING_DATA))
            .andExpect(jsonPath("$.pickupType").value(DEFAULT_PICKUP_TYPE))
            .andExpect(jsonPath("$.houseMindingDetail").value(DEFAULT_HOUSE_MINDING_DETAIL.toString()))
            .andExpect(jsonPath("$.mailFromDate").value(DEFAULT_MAIL_FROM_DATE.toString()))
            .andExpect(jsonPath("$.mailToDate").value(DEFAULT_MAIL_TO_DATE.toString()))
            .andExpect(jsonPath("$.mailAfter").value(DEFAULT_MAIL_AFTER))
            .andExpect(jsonPath("$.mailCollectionDays").value(DEFAULT_MAIL_COLLECTION_DAYS))
            .andExpect(jsonPath("$.otherDescription").value(DEFAULT_OTHER_DESCRIPTION))
            .andExpect(jsonPath("$.otherHours").value(DEFAULT_OTHER_HOURS.intValue()))
            .andExpect(jsonPath("$.otherFromTime").value(DEFAULT_OTHER_FROM_TIME.toString()))
            .andExpect(jsonPath("$.otherToTime").value(DEFAULT_OTHER_TO_TIME.toString()))
            .andExpect(jsonPath("$.otherEquipment").value(DEFAULT_OTHER_EQUIPMENT.toString()))
            .andExpect(jsonPath("$.provideFor").value(DEFAULT_PROVIDE_FOR.intValue()))
            .andExpect(jsonPath("$.provideType").value(DEFAULT_PROVIDE_TYPE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingHouseHelpRequest() throws Exception {
        // Get the houseHelpRequest
        restHouseHelpRequestMockMvc.perform(get("/api/house-help-requests/{id}", Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateHouseHelpRequest() throws Exception {
        // Initialize the database
        houseHelpRequestRepository.saveAndFlush(houseHelpRequest);

        int databaseSizeBeforeUpdate = houseHelpRequestRepository.findAll().size();

        // Update the houseHelpRequest
        HouseHelpRequest updatedHouseHelpRequest = houseHelpRequestRepository.findById(houseHelpRequest.getId()).get();
        // Disconnect from session so that the updates on updatedHouseHelpRequest are not directly saved in db
        em.detach(updatedHouseHelpRequest);
        updatedHouseHelpRequest
            .services(UPDATED_SERVICES)
            .cleaningTime(UPDATED_CLEANING_TIME)
            .cleaningFromTime(UPDATED_CLEANING_FROM_TIME)
            .cleaningToTime(UPDATED_CLEANING_TO_TIME)
            .cleaningEquipment(UPDATED_CLEANING_EQUIPMENT)
            .cleaningDescription(UPDATED_CLEANING_DESCRIPTION)
            .cookingFromTime(UPDATED_COOKING_FROM_TIME)
            .cookingToTime(UPDATED_COOKING_TO_TIME)
            .cookingServes(UPDATED_COOKING_SERVES)
            .cookingData(UPDATED_COOKING_DATA)
            .pickupType(UPDATED_PICKUP_TYPE)
            .houseMindingDetail(UPDATED_HOUSE_MINDING_DETAIL)
            .mailFromDate(UPDATED_MAIL_FROM_DATE)
            .mailToDate(UPDATED_MAIL_TO_DATE)
            .mailAfter(UPDATED_MAIL_AFTER)
            .mailCollectionDays(UPDATED_MAIL_COLLECTION_DAYS)
            .otherDescription(UPDATED_OTHER_DESCRIPTION)
            .otherHours(UPDATED_OTHER_HOURS)
            .otherFromTime(UPDATED_OTHER_FROM_TIME)
            .otherToTime(UPDATED_OTHER_TO_TIME)
            .otherEquipment(UPDATED_OTHER_EQUIPMENT)
            .provideFor(UPDATED_PROVIDE_FOR)
            .provideType(UPDATED_PROVIDE_TYPE);
        HouseHelpRequestDTO houseHelpRequestDTO = houseHelpRequestMapper.toDto(updatedHouseHelpRequest);

        restHouseHelpRequestMockMvc
            .perform(
                put("/api/house-help-requests")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(houseHelpRequestDTO))
            )
            .andExpect(status().isOk());

        // Validate the HouseHelpRequest in the database
        List<HouseHelpRequest> houseHelpRequestList = houseHelpRequestRepository.findAll();
        assertThat(houseHelpRequestList).hasSize(databaseSizeBeforeUpdate);
        HouseHelpRequest testHouseHelpRequest = houseHelpRequestList.get(houseHelpRequestList.size() - 1);
        assertThat(testHouseHelpRequest.getServices()).isEqualTo(UPDATED_SERVICES);
        assertThat(testHouseHelpRequest.getCleaningTime()).isEqualTo(UPDATED_CLEANING_TIME);
        assertThat(testHouseHelpRequest.getCleaningFromTime()).isEqualTo(UPDATED_CLEANING_FROM_TIME);
        assertThat(testHouseHelpRequest.getCleaningToTime()).isEqualTo(UPDATED_CLEANING_TO_TIME);
        assertThat(testHouseHelpRequest.getCleaningEquipment()).isEqualTo(UPDATED_CLEANING_EQUIPMENT);
        assertThat(testHouseHelpRequest.getCleaningDescription()).isEqualTo(UPDATED_CLEANING_DESCRIPTION);
        assertThat(testHouseHelpRequest.getCookingFromTime()).isEqualTo(UPDATED_COOKING_FROM_TIME);
        assertThat(testHouseHelpRequest.getCookingToTime()).isEqualTo(UPDATED_COOKING_TO_TIME);
        assertThat(testHouseHelpRequest.getCookingServes()).isEqualTo(UPDATED_COOKING_SERVES);
        assertThat(testHouseHelpRequest.getCookingData()).isEqualTo(UPDATED_COOKING_DATA);
        assertThat(testHouseHelpRequest.getPickupType()).isEqualTo(UPDATED_PICKUP_TYPE);
        assertThat(testHouseHelpRequest.getHouseMindingDetail()).isEqualTo(UPDATED_HOUSE_MINDING_DETAIL);
        assertThat(testHouseHelpRequest.getMailFromDate()).isEqualTo(UPDATED_MAIL_FROM_DATE);
        assertThat(testHouseHelpRequest.getMailToDate()).isEqualTo(UPDATED_MAIL_TO_DATE);
        assertThat(testHouseHelpRequest.getMailAfter()).isEqualTo(UPDATED_MAIL_AFTER);
        assertThat(testHouseHelpRequest.getMailCollectionDays()).isEqualTo(UPDATED_MAIL_COLLECTION_DAYS);
        assertThat(testHouseHelpRequest.getOtherDescription()).isEqualTo(UPDATED_OTHER_DESCRIPTION);
        assertThat(testHouseHelpRequest.getOtherHours()).isEqualTo(UPDATED_OTHER_HOURS);
        assertThat(testHouseHelpRequest.getOtherFromTime()).isEqualTo(UPDATED_OTHER_FROM_TIME);
        assertThat(testHouseHelpRequest.getOtherToTime()).isEqualTo(UPDATED_OTHER_TO_TIME);
        assertThat(testHouseHelpRequest.getOtherEquipment()).isEqualTo(UPDATED_OTHER_EQUIPMENT);
        assertThat(testHouseHelpRequest.getProvideFor()).isEqualTo(UPDATED_PROVIDE_FOR);
        assertThat(testHouseHelpRequest.getProvideType()).isEqualTo(UPDATED_PROVIDE_TYPE);

        // Validate the HouseHelpRequest in Elasticsearch
        verify(mockHouseHelpRequestSearchRepository, times(1)).save(testHouseHelpRequest);
    }

    @Test
    @Transactional
    public void updateNonExistingHouseHelpRequest() throws Exception {
        int databaseSizeBeforeUpdate = houseHelpRequestRepository.findAll().size();

        // Create the HouseHelpRequest
        HouseHelpRequestDTO houseHelpRequestDTO = houseHelpRequestMapper.toDto(houseHelpRequest);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restHouseHelpRequestMockMvc
            .perform(
                put("/api/house-help-requests")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(houseHelpRequestDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the HouseHelpRequest in the database
        List<HouseHelpRequest> houseHelpRequestList = houseHelpRequestRepository.findAll();
        assertThat(houseHelpRequestList).hasSize(databaseSizeBeforeUpdate);

        // Validate the HouseHelpRequest in Elasticsearch
        verify(mockHouseHelpRequestSearchRepository, times(0)).save(houseHelpRequest);
    }

    @Test
    @Transactional
    public void deleteHouseHelpRequest() throws Exception {
        // Initialize the database
        houseHelpRequestRepository.saveAndFlush(houseHelpRequest);

        int databaseSizeBeforeDelete = houseHelpRequestRepository.findAll().size();

        // Delete the houseHelpRequest
        restHouseHelpRequestMockMvc
            .perform(delete("/api/house-help-requests/{id}", houseHelpRequest.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<HouseHelpRequest> houseHelpRequestList = houseHelpRequestRepository.findAll();
        assertThat(houseHelpRequestList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the HouseHelpRequest in Elasticsearch
        verify(mockHouseHelpRequestSearchRepository, times(1)).deleteById(houseHelpRequest.getId());
    }

    @Test
    @Transactional
    public void searchHouseHelpRequest() throws Exception {
        // Configure the mock search repository
        // Initialize the database
        houseHelpRequestRepository.saveAndFlush(houseHelpRequest);
        when(mockHouseHelpRequestSearchRepository.search(queryStringQuery("id:" + houseHelpRequest.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(houseHelpRequest), PageRequest.of(0, 1), 1));

        // Search the houseHelpRequest
        restHouseHelpRequestMockMvc
            .perform(get("/api/_search/house-help-requests?query=id:" + houseHelpRequest.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(houseHelpRequest.getId().intValue())))
            .andExpect(jsonPath("$.[*].services").value(hasItem(DEFAULT_SERVICES.toString())))
            .andExpect(jsonPath("$.[*].cleaningTime").value(hasItem(DEFAULT_CLEANING_TIME.intValue())))
            .andExpect(jsonPath("$.[*].cleaningFromTime").value(hasItem(DEFAULT_CLEANING_FROM_TIME.toString())))
            .andExpect(jsonPath("$.[*].cleaningToTime").value(hasItem(DEFAULT_CLEANING_TO_TIME.toString())))
            .andExpect(jsonPath("$.[*].cleaningEquipment").value(hasItem(DEFAULT_CLEANING_EQUIPMENT.toString())))
            .andExpect(jsonPath("$.[*].cleaningDescription").value(hasItem(DEFAULT_CLEANING_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].cookingFromTime").value(hasItem(DEFAULT_COOKING_FROM_TIME.toString())))
            .andExpect(jsonPath("$.[*].cookingToTime").value(hasItem(DEFAULT_COOKING_TO_TIME.toString())))
            .andExpect(jsonPath("$.[*].cookingServes").value(hasItem(DEFAULT_COOKING_SERVES)))
            .andExpect(jsonPath("$.[*].cookingData").value(hasItem(DEFAULT_COOKING_DATA)))
            .andExpect(jsonPath("$.[*].pickupType").value(hasItem(DEFAULT_PICKUP_TYPE)))
            .andExpect(jsonPath("$.[*].houseMindingDetail").value(hasItem(DEFAULT_HOUSE_MINDING_DETAIL.toString())))
            .andExpect(jsonPath("$.[*].mailFromDate").value(hasItem(DEFAULT_MAIL_FROM_DATE.toString())))
            .andExpect(jsonPath("$.[*].mailToDate").value(hasItem(DEFAULT_MAIL_TO_DATE.toString())))
            .andExpect(jsonPath("$.[*].mailAfter").value(hasItem(DEFAULT_MAIL_AFTER)))
            .andExpect(jsonPath("$.[*].mailCollectionDays").value(hasItem(DEFAULT_MAIL_COLLECTION_DAYS)))
            .andExpect(jsonPath("$.[*].otherDescription").value(hasItem(DEFAULT_OTHER_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].otherHours").value(hasItem(DEFAULT_OTHER_HOURS.intValue())))
            .andExpect(jsonPath("$.[*].otherFromTime").value(hasItem(DEFAULT_OTHER_FROM_TIME.toString())))
            .andExpect(jsonPath("$.[*].otherToTime").value(hasItem(DEFAULT_OTHER_TO_TIME.toString())))
            .andExpect(jsonPath("$.[*].otherEquipment").value(hasItem(DEFAULT_OTHER_EQUIPMENT.toString())))
            .andExpect(jsonPath("$.[*].provideFor").value(hasItem(DEFAULT_PROVIDE_FOR.intValue())))
            .andExpect(jsonPath("$.[*].provideType").value(hasItem(DEFAULT_PROVIDE_TYPE.toString())));
    }
}
