package com.mindforme.app.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.elasticsearch.index.query.QueryBuilders.queryStringQuery;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.mindforme.app.MindformeApp;
import com.mindforme.app.domain.Coupon;
import com.mindforme.app.repository.CouponRepository;
import com.mindforme.app.repository.search.CouponSearchRepository;
import com.mindforme.app.service.CouponService;
import com.mindforme.app.service.dto.CouponDTO;
import com.mindforme.app.service.mapper.CouponMapper;
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
 * Integration tests for the {@link CouponResource} REST controller.
 */
@SpringBootTest(classes = MindformeApp.class)
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
public class CouponResourceIT {
    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final Instant DEFAULT_EXPIRE_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_EXPIRE_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_EMAIL = "AAAAAAAAAA";
    private static final String UPDATED_EMAIL = "BBBBBBBBBB";

    private static final String DEFAULT_MESSAGE = "AAAAAAAAAA";
    private static final String UPDATED_MESSAGE = "BBBBBBBBBB";

    private static final String DEFAULT_CODE = "AAAAAAAAAA";
    private static final String UPDATED_CODE = "BBBBBBBBBB";

    private static final Long DEFAULT_USED_BY = 1L;
    private static final Long UPDATED_USED_BY = 2L;

    @Autowired
    private CouponRepository couponRepository;

    @Autowired
    private CouponMapper couponMapper;

    @Autowired
    private CouponService couponService;

    /**
     * This repository is mocked in the com.mindforme.app.repository.search test package.
     *
     * @see com.mindforme.app.repository.search.CouponSearchRepositoryMockConfiguration
     */
    @Autowired
    private CouponSearchRepository mockCouponSearchRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restCouponMockMvc;

    private Coupon coupon;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Coupon createEntity(EntityManager em) {
        Coupon coupon = new Coupon()
            .name(DEFAULT_NAME)
            .expireDate(DEFAULT_EXPIRE_DATE)
            .email(DEFAULT_EMAIL)
            .message(DEFAULT_MESSAGE)
            .code(DEFAULT_CODE)
            .usedBy(DEFAULT_USED_BY);
        return coupon;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Coupon createUpdatedEntity(EntityManager em) {
        Coupon coupon = new Coupon()
            .name(UPDATED_NAME)
            .expireDate(UPDATED_EXPIRE_DATE)
            .email(UPDATED_EMAIL)
            .message(UPDATED_MESSAGE)
            .code(UPDATED_CODE)
            .usedBy(UPDATED_USED_BY);
        return coupon;
    }

    @BeforeEach
    public void initTest() {
        coupon = createEntity(em);
    }

    @Test
    @Transactional
    public void createCoupon() throws Exception {
        int databaseSizeBeforeCreate = couponRepository.findAll().size();
        // Create the Coupon
        CouponDTO couponDTO = couponMapper.toDto(coupon);
        restCouponMockMvc
            .perform(post("/api/coupons").contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(couponDTO)))
            .andExpect(status().isCreated());

        // Validate the Coupon in the database
        List<Coupon> couponList = couponRepository.findAll();
        assertThat(couponList).hasSize(databaseSizeBeforeCreate + 1);
        Coupon testCoupon = couponList.get(couponList.size() - 1);
        assertThat(testCoupon.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testCoupon.getExpireDate()).isEqualTo(DEFAULT_EXPIRE_DATE);
        assertThat(testCoupon.getEmail()).isEqualTo(DEFAULT_EMAIL);
        assertThat(testCoupon.getMessage()).isEqualTo(DEFAULT_MESSAGE);
        assertThat(testCoupon.getCode()).isEqualTo(DEFAULT_CODE);
        assertThat(testCoupon.getUsedBy()).isEqualTo(DEFAULT_USED_BY);

        // Validate the Coupon in Elasticsearch
        verify(mockCouponSearchRepository, times(1)).save(testCoupon);
    }

    @Test
    @Transactional
    public void createCouponWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = couponRepository.findAll().size();

        // Create the Coupon with an existing ID
        coupon.setId(1L);
        CouponDTO couponDTO = couponMapper.toDto(coupon);

        // An entity with an existing ID cannot be created, so this API call must fail
        restCouponMockMvc
            .perform(post("/api/coupons").contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(couponDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Coupon in the database
        List<Coupon> couponList = couponRepository.findAll();
        assertThat(couponList).hasSize(databaseSizeBeforeCreate);

        // Validate the Coupon in Elasticsearch
        verify(mockCouponSearchRepository, times(0)).save(coupon);
    }

    @Test
    @Transactional
    public void getAllCoupons() throws Exception {
        // Initialize the database
        couponRepository.saveAndFlush(coupon);

        // Get all the couponList
        restCouponMockMvc
            .perform(get("/api/coupons?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(coupon.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].expireDate").value(hasItem(DEFAULT_EXPIRE_DATE.toString())))
            .andExpect(jsonPath("$.[*].email").value(hasItem(DEFAULT_EMAIL)))
            .andExpect(jsonPath("$.[*].message").value(hasItem(DEFAULT_MESSAGE.toString())))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE)))
            .andExpect(jsonPath("$.[*].usedBy").value(hasItem(DEFAULT_USED_BY.intValue())));
    }

    @Test
    @Transactional
    public void getCoupon() throws Exception {
        // Initialize the database
        couponRepository.saveAndFlush(coupon);

        // Get the coupon
        restCouponMockMvc
            .perform(get("/api/coupons/{id}", coupon.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(coupon.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.expireDate").value(DEFAULT_EXPIRE_DATE.toString()))
            .andExpect(jsonPath("$.email").value(DEFAULT_EMAIL))
            .andExpect(jsonPath("$.message").value(DEFAULT_MESSAGE.toString()))
            .andExpect(jsonPath("$.code").value(DEFAULT_CODE))
            .andExpect(jsonPath("$.usedBy").value(DEFAULT_USED_BY.intValue()));
    }

    @Test
    @Transactional
    public void getNonExistingCoupon() throws Exception {
        // Get the coupon
        restCouponMockMvc.perform(get("/api/coupons/{id}", Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCoupon() throws Exception {
        // Initialize the database
        couponRepository.saveAndFlush(coupon);

        int databaseSizeBeforeUpdate = couponRepository.findAll().size();

        // Update the coupon
        Coupon updatedCoupon = couponRepository.findById(coupon.getId()).get();
        // Disconnect from session so that the updates on updatedCoupon are not directly saved in db
        em.detach(updatedCoupon);
        updatedCoupon
            .name(UPDATED_NAME)
            .expireDate(UPDATED_EXPIRE_DATE)
            .email(UPDATED_EMAIL)
            .message(UPDATED_MESSAGE)
            .code(UPDATED_CODE)
            .usedBy(UPDATED_USED_BY);
        CouponDTO couponDTO = couponMapper.toDto(updatedCoupon);

        restCouponMockMvc
            .perform(put("/api/coupons").contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(couponDTO)))
            .andExpect(status().isOk());

        // Validate the Coupon in the database
        List<Coupon> couponList = couponRepository.findAll();
        assertThat(couponList).hasSize(databaseSizeBeforeUpdate);
        Coupon testCoupon = couponList.get(couponList.size() - 1);
        assertThat(testCoupon.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testCoupon.getExpireDate()).isEqualTo(UPDATED_EXPIRE_DATE);
        assertThat(testCoupon.getEmail()).isEqualTo(UPDATED_EMAIL);
        assertThat(testCoupon.getMessage()).isEqualTo(UPDATED_MESSAGE);
        assertThat(testCoupon.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testCoupon.getUsedBy()).isEqualTo(UPDATED_USED_BY);

        // Validate the Coupon in Elasticsearch
        verify(mockCouponSearchRepository, times(1)).save(testCoupon);
    }

    @Test
    @Transactional
    public void updateNonExistingCoupon() throws Exception {
        int databaseSizeBeforeUpdate = couponRepository.findAll().size();

        // Create the Coupon
        CouponDTO couponDTO = couponMapper.toDto(coupon);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCouponMockMvc
            .perform(put("/api/coupons").contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(couponDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Coupon in the database
        List<Coupon> couponList = couponRepository.findAll();
        assertThat(couponList).hasSize(databaseSizeBeforeUpdate);

        // Validate the Coupon in Elasticsearch
        verify(mockCouponSearchRepository, times(0)).save(coupon);
    }

    @Test
    @Transactional
    public void deleteCoupon() throws Exception {
        // Initialize the database
        couponRepository.saveAndFlush(coupon);

        int databaseSizeBeforeDelete = couponRepository.findAll().size();

        // Delete the coupon
        restCouponMockMvc
            .perform(delete("/api/coupons/{id}", coupon.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Coupon> couponList = couponRepository.findAll();
        assertThat(couponList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the Coupon in Elasticsearch
        verify(mockCouponSearchRepository, times(1)).deleteById(coupon.getId());
    }

    @Test
    @Transactional
    public void searchCoupon() throws Exception {
        // Configure the mock search repository
        // Initialize the database
        couponRepository.saveAndFlush(coupon);
        when(mockCouponSearchRepository.search(queryStringQuery("id:" + coupon.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(coupon), PageRequest.of(0, 1), 1));

        // Search the coupon
        restCouponMockMvc
            .perform(get("/api/_search/coupons?query=id:" + coupon.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(coupon.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].expireDate").value(hasItem(DEFAULT_EXPIRE_DATE.toString())))
            .andExpect(jsonPath("$.[*].email").value(hasItem(DEFAULT_EMAIL)))
            .andExpect(jsonPath("$.[*].message").value(hasItem(DEFAULT_MESSAGE.toString())))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE)))
            .andExpect(jsonPath("$.[*].usedBy").value(hasItem(DEFAULT_USED_BY.intValue())));
    }
}
