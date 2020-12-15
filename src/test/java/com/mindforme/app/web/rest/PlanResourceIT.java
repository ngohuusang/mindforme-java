package com.mindforme.app.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.elasticsearch.index.query.QueryBuilders.queryStringQuery;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.mindforme.app.MindformeApp;
import com.mindforme.app.domain.Plan;
import com.mindforme.app.domain.enumeration.PlanType;
import com.mindforme.app.domain.enumeration.Status;
import com.mindforme.app.repository.PlanRepository;
import com.mindforme.app.repository.search.PlanSearchRepository;
import com.mindforme.app.service.PlanService;
import com.mindforme.app.service.dto.PlanDTO;
import com.mindforme.app.service.mapper.PlanMapper;
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
 * Integration tests for the {@link PlanResource} REST controller.
 */
@SpringBootTest(classes = MindformeApp.class)
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
public class PlanResourceIT {
    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_AMOUNT = "AAAAAAAAAA";
    private static final String UPDATED_AMOUNT = "BBBBBBBBBB";

    private static final Integer DEFAULT_MONTHS = 1;
    private static final Integer UPDATED_MONTHS = 2;

    private static final Status DEFAULT_STATUS = Status.Active;
    private static final Status UPDATED_STATUS = Status.Inactive;

    private static final PlanType DEFAULT_TYPE = PlanType.FREE;
    private static final PlanType UPDATED_TYPE = PlanType.PAID;

    @Autowired
    private PlanRepository planRepository;

    @Autowired
    private PlanMapper planMapper;

    @Autowired
    private PlanService planService;

    /**
     * This repository is mocked in the com.mindforme.app.repository.search test package.
     *
     * @see com.mindforme.app.repository.search.PlanSearchRepositoryMockConfiguration
     */
    @Autowired
    private PlanSearchRepository mockPlanSearchRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restPlanMockMvc;

    private Plan plan;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Plan createEntity(EntityManager em) {
        Plan plan = new Plan().name(DEFAULT_NAME).amount(DEFAULT_AMOUNT).months(DEFAULT_MONTHS).status(DEFAULT_STATUS).type(DEFAULT_TYPE);
        return plan;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Plan createUpdatedEntity(EntityManager em) {
        Plan plan = new Plan().name(UPDATED_NAME).amount(UPDATED_AMOUNT).months(UPDATED_MONTHS).status(UPDATED_STATUS).type(UPDATED_TYPE);
        return plan;
    }

    @BeforeEach
    public void initTest() {
        plan = createEntity(em);
    }

    @Test
    @Transactional
    public void createPlan() throws Exception {
        int databaseSizeBeforeCreate = planRepository.findAll().size();
        // Create the Plan
        PlanDTO planDTO = planMapper.toDto(plan);
        restPlanMockMvc
            .perform(post("/api/plans").contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(planDTO)))
            .andExpect(status().isCreated());

        // Validate the Plan in the database
        List<Plan> planList = planRepository.findAll();
        assertThat(planList).hasSize(databaseSizeBeforeCreate + 1);
        Plan testPlan = planList.get(planList.size() - 1);
        assertThat(testPlan.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testPlan.getAmount()).isEqualTo(DEFAULT_AMOUNT);
        assertThat(testPlan.getMonths()).isEqualTo(DEFAULT_MONTHS);
        assertThat(testPlan.getStatus()).isEqualTo(DEFAULT_STATUS);
        assertThat(testPlan.getType()).isEqualTo(DEFAULT_TYPE);

        // Validate the Plan in Elasticsearch
        verify(mockPlanSearchRepository, times(1)).save(testPlan);
    }

    @Test
    @Transactional
    public void createPlanWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = planRepository.findAll().size();

        // Create the Plan with an existing ID
        plan.setId(1L);
        PlanDTO planDTO = planMapper.toDto(plan);

        // An entity with an existing ID cannot be created, so this API call must fail
        restPlanMockMvc
            .perform(post("/api/plans").contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(planDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Plan in the database
        List<Plan> planList = planRepository.findAll();
        assertThat(planList).hasSize(databaseSizeBeforeCreate);

        // Validate the Plan in Elasticsearch
        verify(mockPlanSearchRepository, times(0)).save(plan);
    }

    @Test
    @Transactional
    public void getAllPlans() throws Exception {
        // Initialize the database
        planRepository.saveAndFlush(plan);

        // Get all the planList
        restPlanMockMvc
            .perform(get("/api/plans?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(plan.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].amount").value(hasItem(DEFAULT_AMOUNT)))
            .andExpect(jsonPath("$.[*].months").value(hasItem(DEFAULT_MONTHS)))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.toString())))
            .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE.toString())));
    }

    @Test
    @Transactional
    public void getPlan() throws Exception {
        // Initialize the database
        planRepository.saveAndFlush(plan);

        // Get the plan
        restPlanMockMvc
            .perform(get("/api/plans/{id}", plan.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(plan.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.amount").value(DEFAULT_AMOUNT))
            .andExpect(jsonPath("$.months").value(DEFAULT_MONTHS))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS.toString()))
            .andExpect(jsonPath("$.type").value(DEFAULT_TYPE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingPlan() throws Exception {
        // Get the plan
        restPlanMockMvc.perform(get("/api/plans/{id}", Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePlan() throws Exception {
        // Initialize the database
        planRepository.saveAndFlush(plan);

        int databaseSizeBeforeUpdate = planRepository.findAll().size();

        // Update the plan
        Plan updatedPlan = planRepository.findById(plan.getId()).get();
        // Disconnect from session so that the updates on updatedPlan are not directly saved in db
        em.detach(updatedPlan);
        updatedPlan.name(UPDATED_NAME).amount(UPDATED_AMOUNT).months(UPDATED_MONTHS).status(UPDATED_STATUS).type(UPDATED_TYPE);
        PlanDTO planDTO = planMapper.toDto(updatedPlan);

        restPlanMockMvc
            .perform(put("/api/plans").contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(planDTO)))
            .andExpect(status().isOk());

        // Validate the Plan in the database
        List<Plan> planList = planRepository.findAll();
        assertThat(planList).hasSize(databaseSizeBeforeUpdate);
        Plan testPlan = planList.get(planList.size() - 1);
        assertThat(testPlan.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testPlan.getAmount()).isEqualTo(UPDATED_AMOUNT);
        assertThat(testPlan.getMonths()).isEqualTo(UPDATED_MONTHS);
        assertThat(testPlan.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testPlan.getType()).isEqualTo(UPDATED_TYPE);

        // Validate the Plan in Elasticsearch
        verify(mockPlanSearchRepository, times(1)).save(testPlan);
    }

    @Test
    @Transactional
    public void updateNonExistingPlan() throws Exception {
        int databaseSizeBeforeUpdate = planRepository.findAll().size();

        // Create the Plan
        PlanDTO planDTO = planMapper.toDto(plan);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPlanMockMvc
            .perform(put("/api/plans").contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(planDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Plan in the database
        List<Plan> planList = planRepository.findAll();
        assertThat(planList).hasSize(databaseSizeBeforeUpdate);

        // Validate the Plan in Elasticsearch
        verify(mockPlanSearchRepository, times(0)).save(plan);
    }

    @Test
    @Transactional
    public void deletePlan() throws Exception {
        // Initialize the database
        planRepository.saveAndFlush(plan);

        int databaseSizeBeforeDelete = planRepository.findAll().size();

        // Delete the plan
        restPlanMockMvc
            .perform(delete("/api/plans/{id}", plan.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Plan> planList = planRepository.findAll();
        assertThat(planList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the Plan in Elasticsearch
        verify(mockPlanSearchRepository, times(1)).deleteById(plan.getId());
    }

    @Test
    @Transactional
    public void searchPlan() throws Exception {
        // Configure the mock search repository
        // Initialize the database
        planRepository.saveAndFlush(plan);
        when(mockPlanSearchRepository.search(queryStringQuery("id:" + plan.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(plan), PageRequest.of(0, 1), 1));

        // Search the plan
        restPlanMockMvc
            .perform(get("/api/_search/plans?query=id:" + plan.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(plan.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].amount").value(hasItem(DEFAULT_AMOUNT)))
            .andExpect(jsonPath("$.[*].months").value(hasItem(DEFAULT_MONTHS)))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.toString())))
            .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE.toString())));
    }
}
