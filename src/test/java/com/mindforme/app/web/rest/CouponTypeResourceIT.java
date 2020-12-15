package com.mindforme.app.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.elasticsearch.index.query.QueryBuilders.queryStringQuery;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.mindforme.app.MindformeApp;
import com.mindforme.app.domain.CouponType;
import com.mindforme.app.repository.CouponTypeRepository;
import com.mindforme.app.repository.search.CouponTypeSearchRepository;
import com.mindforme.app.service.CouponTypeService;
import com.mindforme.app.service.dto.CouponTypeDTO;
import com.mindforme.app.service.mapper.CouponTypeMapper;
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
 * Integration tests for the {@link CouponTypeResource} REST controller.
 */
@SpringBootTest(classes = MindformeApp.class)
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
public class CouponTypeResourceIT {
    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final Integer DEFAULT_VALUE = 1;
    private static final Integer UPDATED_VALUE = 2;

    @Autowired
    private CouponTypeRepository couponTypeRepository;

    @Autowired
    private CouponTypeMapper couponTypeMapper;

    @Autowired
    private CouponTypeService couponTypeService;

    /**
     * This repository is mocked in the com.mindforme.app.repository.search test package.
     *
     * @see com.mindforme.app.repository.search.CouponTypeSearchRepositoryMockConfiguration
     */
    @Autowired
    private CouponTypeSearchRepository mockCouponTypeSearchRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restCouponTypeMockMvc;

    private CouponType couponType;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CouponType createEntity(EntityManager em) {
        CouponType couponType = new CouponType().name(DEFAULT_NAME).value(DEFAULT_VALUE);
        return couponType;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CouponType createUpdatedEntity(EntityManager em) {
        CouponType couponType = new CouponType().name(UPDATED_NAME).value(UPDATED_VALUE);
        return couponType;
    }

    @BeforeEach
    public void initTest() {
        couponType = createEntity(em);
    }

    @Test
    @Transactional
    public void createCouponType() throws Exception {
        int databaseSizeBeforeCreate = couponTypeRepository.findAll().size();
        // Create the CouponType
        CouponTypeDTO couponTypeDTO = couponTypeMapper.toDto(couponType);
        restCouponTypeMockMvc
            .perform(
                post("/api/coupon-types").contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(couponTypeDTO))
            )
            .andExpect(status().isCreated());

        // Validate the CouponType in the database
        List<CouponType> couponTypeList = couponTypeRepository.findAll();
        assertThat(couponTypeList).hasSize(databaseSizeBeforeCreate + 1);
        CouponType testCouponType = couponTypeList.get(couponTypeList.size() - 1);
        assertThat(testCouponType.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testCouponType.getValue()).isEqualTo(DEFAULT_VALUE);

        // Validate the CouponType in Elasticsearch
        verify(mockCouponTypeSearchRepository, times(1)).save(testCouponType);
    }

    @Test
    @Transactional
    public void createCouponTypeWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = couponTypeRepository.findAll().size();

        // Create the CouponType with an existing ID
        couponType.setId(1L);
        CouponTypeDTO couponTypeDTO = couponTypeMapper.toDto(couponType);

        // An entity with an existing ID cannot be created, so this API call must fail
        restCouponTypeMockMvc
            .perform(
                post("/api/coupon-types").contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(couponTypeDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the CouponType in the database
        List<CouponType> couponTypeList = couponTypeRepository.findAll();
        assertThat(couponTypeList).hasSize(databaseSizeBeforeCreate);

        // Validate the CouponType in Elasticsearch
        verify(mockCouponTypeSearchRepository, times(0)).save(couponType);
    }

    @Test
    @Transactional
    public void getAllCouponTypes() throws Exception {
        // Initialize the database
        couponTypeRepository.saveAndFlush(couponType);

        // Get all the couponTypeList
        restCouponTypeMockMvc
            .perform(get("/api/coupon-types?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(couponType.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].value").value(hasItem(DEFAULT_VALUE)));
    }

    @Test
    @Transactional
    public void getCouponType() throws Exception {
        // Initialize the database
        couponTypeRepository.saveAndFlush(couponType);

        // Get the couponType
        restCouponTypeMockMvc
            .perform(get("/api/coupon-types/{id}", couponType.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(couponType.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.value").value(DEFAULT_VALUE));
    }

    @Test
    @Transactional
    public void getNonExistingCouponType() throws Exception {
        // Get the couponType
        restCouponTypeMockMvc.perform(get("/api/coupon-types/{id}", Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCouponType() throws Exception {
        // Initialize the database
        couponTypeRepository.saveAndFlush(couponType);

        int databaseSizeBeforeUpdate = couponTypeRepository.findAll().size();

        // Update the couponType
        CouponType updatedCouponType = couponTypeRepository.findById(couponType.getId()).get();
        // Disconnect from session so that the updates on updatedCouponType are not directly saved in db
        em.detach(updatedCouponType);
        updatedCouponType.name(UPDATED_NAME).value(UPDATED_VALUE);
        CouponTypeDTO couponTypeDTO = couponTypeMapper.toDto(updatedCouponType);

        restCouponTypeMockMvc
            .perform(
                put("/api/coupon-types").contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(couponTypeDTO))
            )
            .andExpect(status().isOk());

        // Validate the CouponType in the database
        List<CouponType> couponTypeList = couponTypeRepository.findAll();
        assertThat(couponTypeList).hasSize(databaseSizeBeforeUpdate);
        CouponType testCouponType = couponTypeList.get(couponTypeList.size() - 1);
        assertThat(testCouponType.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testCouponType.getValue()).isEqualTo(UPDATED_VALUE);

        // Validate the CouponType in Elasticsearch
        verify(mockCouponTypeSearchRepository, times(1)).save(testCouponType);
    }

    @Test
    @Transactional
    public void updateNonExistingCouponType() throws Exception {
        int databaseSizeBeforeUpdate = couponTypeRepository.findAll().size();

        // Create the CouponType
        CouponTypeDTO couponTypeDTO = couponTypeMapper.toDto(couponType);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCouponTypeMockMvc
            .perform(
                put("/api/coupon-types").contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(couponTypeDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the CouponType in the database
        List<CouponType> couponTypeList = couponTypeRepository.findAll();
        assertThat(couponTypeList).hasSize(databaseSizeBeforeUpdate);

        // Validate the CouponType in Elasticsearch
        verify(mockCouponTypeSearchRepository, times(0)).save(couponType);
    }

    @Test
    @Transactional
    public void deleteCouponType() throws Exception {
        // Initialize the database
        couponTypeRepository.saveAndFlush(couponType);

        int databaseSizeBeforeDelete = couponTypeRepository.findAll().size();

        // Delete the couponType
        restCouponTypeMockMvc
            .perform(delete("/api/coupon-types/{id}", couponType.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<CouponType> couponTypeList = couponTypeRepository.findAll();
        assertThat(couponTypeList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the CouponType in Elasticsearch
        verify(mockCouponTypeSearchRepository, times(1)).deleteById(couponType.getId());
    }

    @Test
    @Transactional
    public void searchCouponType() throws Exception {
        // Configure the mock search repository
        // Initialize the database
        couponTypeRepository.saveAndFlush(couponType);
        when(mockCouponTypeSearchRepository.search(queryStringQuery("id:" + couponType.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(couponType), PageRequest.of(0, 1), 1));

        // Search the couponType
        restCouponTypeMockMvc
            .perform(get("/api/_search/coupon-types?query=id:" + couponType.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(couponType.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].value").value(hasItem(DEFAULT_VALUE)));
    }
}
