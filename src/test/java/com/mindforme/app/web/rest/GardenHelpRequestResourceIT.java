package com.mindforme.app.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.elasticsearch.index.query.QueryBuilders.queryStringQuery;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.mindforme.app.MindformeApp;
import com.mindforme.app.domain.GardenHelpRequest;
import com.mindforme.app.domain.enumeration.Equipment;
import com.mindforme.app.domain.enumeration.Equipment;
import com.mindforme.app.domain.enumeration.Equipment;
import com.mindforme.app.domain.enumeration.HouseProvided;
import com.mindforme.app.domain.enumeration.RubbishLoadType;
import com.mindforme.app.repository.GardenHelpRequestRepository;
import com.mindforme.app.repository.search.GardenHelpRequestSearchRepository;
import com.mindforme.app.service.GardenHelpRequestService;
import com.mindforme.app.service.dto.GardenHelpRequestDTO;
import com.mindforme.app.service.mapper.GardenHelpRequestMapper;
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
 * Integration tests for the {@link GardenHelpRequestResource} REST controller.
 */
@SpringBootTest(classes = MindformeApp.class)
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
public class GardenHelpRequestResourceIT {
    private static final Long DEFAULT_TOTAL_HELP_TIME = 1L;
    private static final Long UPDATED_TOTAL_HELP_TIME = 2L;

    private static final Instant DEFAULT_DATE_FROM = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_DATE_FROM = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_DATE_TO = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_DATE_TO = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_TIME_FROM = "AAAAAAAAAA";
    private static final String UPDATED_TIME_FROM = "BBBBBBBBBB";

    private static final String DEFAULT_TIME_TO = "AAAAAAAAAA";
    private static final String UPDATED_TIME_TO = "BBBBBBBBBB";

    private static final String DEFAULT_SERVICES = "AAAAAAAAAA";
    private static final String UPDATED_SERVICES = "BBBBBBBBBB";

    private static final Boolean DEFAULT_EDGE_TRIMMING = false;
    private static final Boolean UPDATED_EDGE_TRIMMING = true;

    private static final String DEFAULT_MOWING_TIME = "AAAAAAAAAA";
    private static final String UPDATED_MOWING_TIME = "BBBBBBBBBB";

    private static final Equipment DEFAULT_MOWING_EQUIPMENT = Equipment.OURS;
    private static final Equipment UPDATED_MOWING_EQUIPMENT = Equipment.YOURS;

    private static final String DEFAULT_LAWN_TIME = "AAAAAAAAAA";
    private static final String UPDATED_LAWN_TIME = "BBBBBBBBBB";

    private static final Equipment DEFAULT_LAWN_EQUIPMENT = Equipment.OURS;
    private static final Equipment UPDATED_LAWN_EQUIPMENT = Equipment.YOURS;

    private static final Integer DEFAULT_RUBBISH_LOAD = 1;
    private static final Integer UPDATED_RUBBISH_LOAD = 2;

    private static final RubbishLoadType DEFAULT_RUBBISH_LOAD_TYPE = RubbishLoadType.UTE;
    private static final RubbishLoadType UPDATED_RUBBISH_LOAD_TYPE = RubbishLoadType.VAN;

    private static final String DEFAULT_OTHER_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_OTHER_DESCRIPTION = "BBBBBBBBBB";

    private static final String DEFAULT_OTHER_HOURS = "AAAAAAAAAA";
    private static final String UPDATED_OTHER_HOURS = "BBBBBBBBBB";

    private static final Equipment DEFAULT_OTHER_EQUIPMENT = Equipment.OURS;
    private static final Equipment UPDATED_OTHER_EQUIPMENT = Equipment.YOURS;

    private static final HouseProvided DEFAULT_PROVIDE_TYPE = HouseProvided.FAMILY;
    private static final HouseProvided UPDATED_PROVIDE_TYPE = HouseProvided.SUPPORTED;

    private static final Integer DEFAULT_PROVIDE_FOR = 1;
    private static final Integer UPDATED_PROVIDE_FOR = 2;

    @Autowired
    private GardenHelpRequestRepository gardenHelpRequestRepository;

    @Autowired
    private GardenHelpRequestMapper gardenHelpRequestMapper;

    @Autowired
    private GardenHelpRequestService gardenHelpRequestService;

    /**
     * This repository is mocked in the com.mindforme.app.repository.search test package.
     *
     * @see com.mindforme.app.repository.search.GardenHelpRequestSearchRepositoryMockConfiguration
     */
    @Autowired
    private GardenHelpRequestSearchRepository mockGardenHelpRequestSearchRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restGardenHelpRequestMockMvc;

    private GardenHelpRequest gardenHelpRequest;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static GardenHelpRequest createEntity(EntityManager em) {
        GardenHelpRequest gardenHelpRequest = new GardenHelpRequest()
            .totalHelpTime(DEFAULT_TOTAL_HELP_TIME)
            .dateFrom(DEFAULT_DATE_FROM)
            .dateTo(DEFAULT_DATE_TO)
            .timeFrom(DEFAULT_TIME_FROM)
            .timeTo(DEFAULT_TIME_TO)
            .services(DEFAULT_SERVICES)
            .edgeTrimming(DEFAULT_EDGE_TRIMMING)
            .mowingTime(DEFAULT_MOWING_TIME)
            .mowingEquipment(DEFAULT_MOWING_EQUIPMENT)
            .lawnTime(DEFAULT_LAWN_TIME)
            .lawnEquipment(DEFAULT_LAWN_EQUIPMENT)
            .rubbishLoad(DEFAULT_RUBBISH_LOAD)
            .rubbishLoadType(DEFAULT_RUBBISH_LOAD_TYPE)
            .otherDescription(DEFAULT_OTHER_DESCRIPTION)
            .otherHours(DEFAULT_OTHER_HOURS)
            .otherEquipment(DEFAULT_OTHER_EQUIPMENT)
            .provideType(DEFAULT_PROVIDE_TYPE)
            .provideFor(DEFAULT_PROVIDE_FOR);
        return gardenHelpRequest;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static GardenHelpRequest createUpdatedEntity(EntityManager em) {
        GardenHelpRequest gardenHelpRequest = new GardenHelpRequest()
            .totalHelpTime(UPDATED_TOTAL_HELP_TIME)
            .dateFrom(UPDATED_DATE_FROM)
            .dateTo(UPDATED_DATE_TO)
            .timeFrom(UPDATED_TIME_FROM)
            .timeTo(UPDATED_TIME_TO)
            .services(UPDATED_SERVICES)
            .edgeTrimming(UPDATED_EDGE_TRIMMING)
            .mowingTime(UPDATED_MOWING_TIME)
            .mowingEquipment(UPDATED_MOWING_EQUIPMENT)
            .lawnTime(UPDATED_LAWN_TIME)
            .lawnEquipment(UPDATED_LAWN_EQUIPMENT)
            .rubbishLoad(UPDATED_RUBBISH_LOAD)
            .rubbishLoadType(UPDATED_RUBBISH_LOAD_TYPE)
            .otherDescription(UPDATED_OTHER_DESCRIPTION)
            .otherHours(UPDATED_OTHER_HOURS)
            .otherEquipment(UPDATED_OTHER_EQUIPMENT)
            .provideType(UPDATED_PROVIDE_TYPE)
            .provideFor(UPDATED_PROVIDE_FOR);
        return gardenHelpRequest;
    }

    @BeforeEach
    public void initTest() {
        gardenHelpRequest = createEntity(em);
    }

    @Test
    @Transactional
    public void createGardenHelpRequest() throws Exception {
        int databaseSizeBeforeCreate = gardenHelpRequestRepository.findAll().size();
        // Create the GardenHelpRequest
        GardenHelpRequestDTO gardenHelpRequestDTO = gardenHelpRequestMapper.toDto(gardenHelpRequest);
        restGardenHelpRequestMockMvc
            .perform(
                post("/api/garden-help-requests")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(gardenHelpRequestDTO))
            )
            .andExpect(status().isCreated());

        // Validate the GardenHelpRequest in the database
        List<GardenHelpRequest> gardenHelpRequestList = gardenHelpRequestRepository.findAll();
        assertThat(gardenHelpRequestList).hasSize(databaseSizeBeforeCreate + 1);
        GardenHelpRequest testGardenHelpRequest = gardenHelpRequestList.get(gardenHelpRequestList.size() - 1);
        assertThat(testGardenHelpRequest.getTotalHelpTime()).isEqualTo(DEFAULT_TOTAL_HELP_TIME);
        assertThat(testGardenHelpRequest.getDateFrom()).isEqualTo(DEFAULT_DATE_FROM);
        assertThat(testGardenHelpRequest.getDateTo()).isEqualTo(DEFAULT_DATE_TO);
        assertThat(testGardenHelpRequest.getTimeFrom()).isEqualTo(DEFAULT_TIME_FROM);
        assertThat(testGardenHelpRequest.getTimeTo()).isEqualTo(DEFAULT_TIME_TO);
        assertThat(testGardenHelpRequest.getServices()).isEqualTo(DEFAULT_SERVICES);
        assertThat(testGardenHelpRequest.isEdgeTrimming()).isEqualTo(DEFAULT_EDGE_TRIMMING);
        assertThat(testGardenHelpRequest.getMowingTime()).isEqualTo(DEFAULT_MOWING_TIME);
        assertThat(testGardenHelpRequest.getMowingEquipment()).isEqualTo(DEFAULT_MOWING_EQUIPMENT);
        assertThat(testGardenHelpRequest.getLawnTime()).isEqualTo(DEFAULT_LAWN_TIME);
        assertThat(testGardenHelpRequest.getLawnEquipment()).isEqualTo(DEFAULT_LAWN_EQUIPMENT);
        assertThat(testGardenHelpRequest.getRubbishLoad()).isEqualTo(DEFAULT_RUBBISH_LOAD);
        assertThat(testGardenHelpRequest.getRubbishLoadType()).isEqualTo(DEFAULT_RUBBISH_LOAD_TYPE);
        assertThat(testGardenHelpRequest.getOtherDescription()).isEqualTo(DEFAULT_OTHER_DESCRIPTION);
        assertThat(testGardenHelpRequest.getOtherHours()).isEqualTo(DEFAULT_OTHER_HOURS);
        assertThat(testGardenHelpRequest.getOtherEquipment()).isEqualTo(DEFAULT_OTHER_EQUIPMENT);
        assertThat(testGardenHelpRequest.getProvideType()).isEqualTo(DEFAULT_PROVIDE_TYPE);
        assertThat(testGardenHelpRequest.getProvideFor()).isEqualTo(DEFAULT_PROVIDE_FOR);

        // Validate the GardenHelpRequest in Elasticsearch
        verify(mockGardenHelpRequestSearchRepository, times(1)).save(testGardenHelpRequest);
    }

    @Test
    @Transactional
    public void createGardenHelpRequestWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = gardenHelpRequestRepository.findAll().size();

        // Create the GardenHelpRequest with an existing ID
        gardenHelpRequest.setId(1L);
        GardenHelpRequestDTO gardenHelpRequestDTO = gardenHelpRequestMapper.toDto(gardenHelpRequest);

        // An entity with an existing ID cannot be created, so this API call must fail
        restGardenHelpRequestMockMvc
            .perform(
                post("/api/garden-help-requests")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(gardenHelpRequestDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the GardenHelpRequest in the database
        List<GardenHelpRequest> gardenHelpRequestList = gardenHelpRequestRepository.findAll();
        assertThat(gardenHelpRequestList).hasSize(databaseSizeBeforeCreate);

        // Validate the GardenHelpRequest in Elasticsearch
        verify(mockGardenHelpRequestSearchRepository, times(0)).save(gardenHelpRequest);
    }

    @Test
    @Transactional
    public void getAllGardenHelpRequests() throws Exception {
        // Initialize the database
        gardenHelpRequestRepository.saveAndFlush(gardenHelpRequest);

        // Get all the gardenHelpRequestList
        restGardenHelpRequestMockMvc
            .perform(get("/api/garden-help-requests?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(gardenHelpRequest.getId().intValue())))
            .andExpect(jsonPath("$.[*].totalHelpTime").value(hasItem(DEFAULT_TOTAL_HELP_TIME.intValue())))
            .andExpect(jsonPath("$.[*].dateFrom").value(hasItem(DEFAULT_DATE_FROM.toString())))
            .andExpect(jsonPath("$.[*].dateTo").value(hasItem(DEFAULT_DATE_TO.toString())))
            .andExpect(jsonPath("$.[*].timeFrom").value(hasItem(DEFAULT_TIME_FROM)))
            .andExpect(jsonPath("$.[*].timeTo").value(hasItem(DEFAULT_TIME_TO)))
            .andExpect(jsonPath("$.[*].services").value(hasItem(DEFAULT_SERVICES)))
            .andExpect(jsonPath("$.[*].edgeTrimming").value(hasItem(DEFAULT_EDGE_TRIMMING.booleanValue())))
            .andExpect(jsonPath("$.[*].mowingTime").value(hasItem(DEFAULT_MOWING_TIME)))
            .andExpect(jsonPath("$.[*].mowingEquipment").value(hasItem(DEFAULT_MOWING_EQUIPMENT.toString())))
            .andExpect(jsonPath("$.[*].lawnTime").value(hasItem(DEFAULT_LAWN_TIME)))
            .andExpect(jsonPath("$.[*].lawnEquipment").value(hasItem(DEFAULT_LAWN_EQUIPMENT.toString())))
            .andExpect(jsonPath("$.[*].rubbishLoad").value(hasItem(DEFAULT_RUBBISH_LOAD)))
            .andExpect(jsonPath("$.[*].rubbishLoadType").value(hasItem(DEFAULT_RUBBISH_LOAD_TYPE.toString())))
            .andExpect(jsonPath("$.[*].otherDescription").value(hasItem(DEFAULT_OTHER_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].otherHours").value(hasItem(DEFAULT_OTHER_HOURS)))
            .andExpect(jsonPath("$.[*].otherEquipment").value(hasItem(DEFAULT_OTHER_EQUIPMENT.toString())))
            .andExpect(jsonPath("$.[*].provideType").value(hasItem(DEFAULT_PROVIDE_TYPE.toString())))
            .andExpect(jsonPath("$.[*].provideFor").value(hasItem(DEFAULT_PROVIDE_FOR)));
    }

    @Test
    @Transactional
    public void getGardenHelpRequest() throws Exception {
        // Initialize the database
        gardenHelpRequestRepository.saveAndFlush(gardenHelpRequest);

        // Get the gardenHelpRequest
        restGardenHelpRequestMockMvc
            .perform(get("/api/garden-help-requests/{id}", gardenHelpRequest.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(gardenHelpRequest.getId().intValue()))
            .andExpect(jsonPath("$.totalHelpTime").value(DEFAULT_TOTAL_HELP_TIME.intValue()))
            .andExpect(jsonPath("$.dateFrom").value(DEFAULT_DATE_FROM.toString()))
            .andExpect(jsonPath("$.dateTo").value(DEFAULT_DATE_TO.toString()))
            .andExpect(jsonPath("$.timeFrom").value(DEFAULT_TIME_FROM))
            .andExpect(jsonPath("$.timeTo").value(DEFAULT_TIME_TO))
            .andExpect(jsonPath("$.services").value(DEFAULT_SERVICES))
            .andExpect(jsonPath("$.edgeTrimming").value(DEFAULT_EDGE_TRIMMING.booleanValue()))
            .andExpect(jsonPath("$.mowingTime").value(DEFAULT_MOWING_TIME))
            .andExpect(jsonPath("$.mowingEquipment").value(DEFAULT_MOWING_EQUIPMENT.toString()))
            .andExpect(jsonPath("$.lawnTime").value(DEFAULT_LAWN_TIME))
            .andExpect(jsonPath("$.lawnEquipment").value(DEFAULT_LAWN_EQUIPMENT.toString()))
            .andExpect(jsonPath("$.rubbishLoad").value(DEFAULT_RUBBISH_LOAD))
            .andExpect(jsonPath("$.rubbishLoadType").value(DEFAULT_RUBBISH_LOAD_TYPE.toString()))
            .andExpect(jsonPath("$.otherDescription").value(DEFAULT_OTHER_DESCRIPTION))
            .andExpect(jsonPath("$.otherHours").value(DEFAULT_OTHER_HOURS))
            .andExpect(jsonPath("$.otherEquipment").value(DEFAULT_OTHER_EQUIPMENT.toString()))
            .andExpect(jsonPath("$.provideType").value(DEFAULT_PROVIDE_TYPE.toString()))
            .andExpect(jsonPath("$.provideFor").value(DEFAULT_PROVIDE_FOR));
    }

    @Test
    @Transactional
    public void getNonExistingGardenHelpRequest() throws Exception {
        // Get the gardenHelpRequest
        restGardenHelpRequestMockMvc.perform(get("/api/garden-help-requests/{id}", Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateGardenHelpRequest() throws Exception {
        // Initialize the database
        gardenHelpRequestRepository.saveAndFlush(gardenHelpRequest);

        int databaseSizeBeforeUpdate = gardenHelpRequestRepository.findAll().size();

        // Update the gardenHelpRequest
        GardenHelpRequest updatedGardenHelpRequest = gardenHelpRequestRepository.findById(gardenHelpRequest.getId()).get();
        // Disconnect from session so that the updates on updatedGardenHelpRequest are not directly saved in db
        em.detach(updatedGardenHelpRequest);
        updatedGardenHelpRequest
            .totalHelpTime(UPDATED_TOTAL_HELP_TIME)
            .dateFrom(UPDATED_DATE_FROM)
            .dateTo(UPDATED_DATE_TO)
            .timeFrom(UPDATED_TIME_FROM)
            .timeTo(UPDATED_TIME_TO)
            .services(UPDATED_SERVICES)
            .edgeTrimming(UPDATED_EDGE_TRIMMING)
            .mowingTime(UPDATED_MOWING_TIME)
            .mowingEquipment(UPDATED_MOWING_EQUIPMENT)
            .lawnTime(UPDATED_LAWN_TIME)
            .lawnEquipment(UPDATED_LAWN_EQUIPMENT)
            .rubbishLoad(UPDATED_RUBBISH_LOAD)
            .rubbishLoadType(UPDATED_RUBBISH_LOAD_TYPE)
            .otherDescription(UPDATED_OTHER_DESCRIPTION)
            .otherHours(UPDATED_OTHER_HOURS)
            .otherEquipment(UPDATED_OTHER_EQUIPMENT)
            .provideType(UPDATED_PROVIDE_TYPE)
            .provideFor(UPDATED_PROVIDE_FOR);
        GardenHelpRequestDTO gardenHelpRequestDTO = gardenHelpRequestMapper.toDto(updatedGardenHelpRequest);

        restGardenHelpRequestMockMvc
            .perform(
                put("/api/garden-help-requests")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(gardenHelpRequestDTO))
            )
            .andExpect(status().isOk());

        // Validate the GardenHelpRequest in the database
        List<GardenHelpRequest> gardenHelpRequestList = gardenHelpRequestRepository.findAll();
        assertThat(gardenHelpRequestList).hasSize(databaseSizeBeforeUpdate);
        GardenHelpRequest testGardenHelpRequest = gardenHelpRequestList.get(gardenHelpRequestList.size() - 1);
        assertThat(testGardenHelpRequest.getTotalHelpTime()).isEqualTo(UPDATED_TOTAL_HELP_TIME);
        assertThat(testGardenHelpRequest.getDateFrom()).isEqualTo(UPDATED_DATE_FROM);
        assertThat(testGardenHelpRequest.getDateTo()).isEqualTo(UPDATED_DATE_TO);
        assertThat(testGardenHelpRequest.getTimeFrom()).isEqualTo(UPDATED_TIME_FROM);
        assertThat(testGardenHelpRequest.getTimeTo()).isEqualTo(UPDATED_TIME_TO);
        assertThat(testGardenHelpRequest.getServices()).isEqualTo(UPDATED_SERVICES);
        assertThat(testGardenHelpRequest.isEdgeTrimming()).isEqualTo(UPDATED_EDGE_TRIMMING);
        assertThat(testGardenHelpRequest.getMowingTime()).isEqualTo(UPDATED_MOWING_TIME);
        assertThat(testGardenHelpRequest.getMowingEquipment()).isEqualTo(UPDATED_MOWING_EQUIPMENT);
        assertThat(testGardenHelpRequest.getLawnTime()).isEqualTo(UPDATED_LAWN_TIME);
        assertThat(testGardenHelpRequest.getLawnEquipment()).isEqualTo(UPDATED_LAWN_EQUIPMENT);
        assertThat(testGardenHelpRequest.getRubbishLoad()).isEqualTo(UPDATED_RUBBISH_LOAD);
        assertThat(testGardenHelpRequest.getRubbishLoadType()).isEqualTo(UPDATED_RUBBISH_LOAD_TYPE);
        assertThat(testGardenHelpRequest.getOtherDescription()).isEqualTo(UPDATED_OTHER_DESCRIPTION);
        assertThat(testGardenHelpRequest.getOtherHours()).isEqualTo(UPDATED_OTHER_HOURS);
        assertThat(testGardenHelpRequest.getOtherEquipment()).isEqualTo(UPDATED_OTHER_EQUIPMENT);
        assertThat(testGardenHelpRequest.getProvideType()).isEqualTo(UPDATED_PROVIDE_TYPE);
        assertThat(testGardenHelpRequest.getProvideFor()).isEqualTo(UPDATED_PROVIDE_FOR);

        // Validate the GardenHelpRequest in Elasticsearch
        verify(mockGardenHelpRequestSearchRepository, times(1)).save(testGardenHelpRequest);
    }

    @Test
    @Transactional
    public void updateNonExistingGardenHelpRequest() throws Exception {
        int databaseSizeBeforeUpdate = gardenHelpRequestRepository.findAll().size();

        // Create the GardenHelpRequest
        GardenHelpRequestDTO gardenHelpRequestDTO = gardenHelpRequestMapper.toDto(gardenHelpRequest);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restGardenHelpRequestMockMvc
            .perform(
                put("/api/garden-help-requests")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(gardenHelpRequestDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the GardenHelpRequest in the database
        List<GardenHelpRequest> gardenHelpRequestList = gardenHelpRequestRepository.findAll();
        assertThat(gardenHelpRequestList).hasSize(databaseSizeBeforeUpdate);

        // Validate the GardenHelpRequest in Elasticsearch
        verify(mockGardenHelpRequestSearchRepository, times(0)).save(gardenHelpRequest);
    }

    @Test
    @Transactional
    public void deleteGardenHelpRequest() throws Exception {
        // Initialize the database
        gardenHelpRequestRepository.saveAndFlush(gardenHelpRequest);

        int databaseSizeBeforeDelete = gardenHelpRequestRepository.findAll().size();

        // Delete the gardenHelpRequest
        restGardenHelpRequestMockMvc
            .perform(delete("/api/garden-help-requests/{id}", gardenHelpRequest.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<GardenHelpRequest> gardenHelpRequestList = gardenHelpRequestRepository.findAll();
        assertThat(gardenHelpRequestList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the GardenHelpRequest in Elasticsearch
        verify(mockGardenHelpRequestSearchRepository, times(1)).deleteById(gardenHelpRequest.getId());
    }

    @Test
    @Transactional
    public void searchGardenHelpRequest() throws Exception {
        // Configure the mock search repository
        // Initialize the database
        gardenHelpRequestRepository.saveAndFlush(gardenHelpRequest);
        when(mockGardenHelpRequestSearchRepository.search(queryStringQuery("id:" + gardenHelpRequest.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(gardenHelpRequest), PageRequest.of(0, 1), 1));

        // Search the gardenHelpRequest
        restGardenHelpRequestMockMvc
            .perform(get("/api/_search/garden-help-requests?query=id:" + gardenHelpRequest.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(gardenHelpRequest.getId().intValue())))
            .andExpect(jsonPath("$.[*].totalHelpTime").value(hasItem(DEFAULT_TOTAL_HELP_TIME.intValue())))
            .andExpect(jsonPath("$.[*].dateFrom").value(hasItem(DEFAULT_DATE_FROM.toString())))
            .andExpect(jsonPath("$.[*].dateTo").value(hasItem(DEFAULT_DATE_TO.toString())))
            .andExpect(jsonPath("$.[*].timeFrom").value(hasItem(DEFAULT_TIME_FROM)))
            .andExpect(jsonPath("$.[*].timeTo").value(hasItem(DEFAULT_TIME_TO)))
            .andExpect(jsonPath("$.[*].services").value(hasItem(DEFAULT_SERVICES)))
            .andExpect(jsonPath("$.[*].edgeTrimming").value(hasItem(DEFAULT_EDGE_TRIMMING.booleanValue())))
            .andExpect(jsonPath("$.[*].mowingTime").value(hasItem(DEFAULT_MOWING_TIME)))
            .andExpect(jsonPath("$.[*].mowingEquipment").value(hasItem(DEFAULT_MOWING_EQUIPMENT.toString())))
            .andExpect(jsonPath("$.[*].lawnTime").value(hasItem(DEFAULT_LAWN_TIME)))
            .andExpect(jsonPath("$.[*].lawnEquipment").value(hasItem(DEFAULT_LAWN_EQUIPMENT.toString())))
            .andExpect(jsonPath("$.[*].rubbishLoad").value(hasItem(DEFAULT_RUBBISH_LOAD)))
            .andExpect(jsonPath("$.[*].rubbishLoadType").value(hasItem(DEFAULT_RUBBISH_LOAD_TYPE.toString())))
            .andExpect(jsonPath("$.[*].otherDescription").value(hasItem(DEFAULT_OTHER_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].otherHours").value(hasItem(DEFAULT_OTHER_HOURS)))
            .andExpect(jsonPath("$.[*].otherEquipment").value(hasItem(DEFAULT_OTHER_EQUIPMENT.toString())))
            .andExpect(jsonPath("$.[*].provideType").value(hasItem(DEFAULT_PROVIDE_TYPE.toString())))
            .andExpect(jsonPath("$.[*].provideFor").value(hasItem(DEFAULT_PROVIDE_FOR)));
    }
}
