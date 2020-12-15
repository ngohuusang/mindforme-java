package com.mindforme.app.service.impl;

import static org.elasticsearch.index.query.QueryBuilders.*;

import com.mindforme.app.domain.Coupon;
import com.mindforme.app.repository.CouponRepository;
import com.mindforme.app.repository.search.CouponSearchRepository;
import com.mindforme.app.service.CouponService;
import com.mindforme.app.service.dto.CouponDTO;
import com.mindforme.app.service.mapper.CouponMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Coupon}.
 */
@Service
@Transactional
public class CouponServiceImpl implements CouponService {
    private final Logger log = LoggerFactory.getLogger(CouponServiceImpl.class);

    private final CouponRepository couponRepository;

    private final CouponMapper couponMapper;

    private final CouponSearchRepository couponSearchRepository;

    public CouponServiceImpl(CouponRepository couponRepository, CouponMapper couponMapper, CouponSearchRepository couponSearchRepository) {
        this.couponRepository = couponRepository;
        this.couponMapper = couponMapper;
        this.couponSearchRepository = couponSearchRepository;
    }

    @Override
    public CouponDTO save(CouponDTO couponDTO) {
        log.debug("Request to save Coupon : {}", couponDTO);
        Coupon coupon = couponMapper.toEntity(couponDTO);
        coupon = couponRepository.save(coupon);
        CouponDTO result = couponMapper.toDto(coupon);
        couponSearchRepository.save(coupon);
        return result;
    }

    @Override
    @Transactional(readOnly = true)
    public Page<CouponDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Coupons");
        return couponRepository.findAll(pageable).map(couponMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<CouponDTO> findOne(Long id) {
        log.debug("Request to get Coupon : {}", id);
        return couponRepository.findById(id).map(couponMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Coupon : {}", id);
        couponRepository.deleteById(id);
        couponSearchRepository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<CouponDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of Coupons for query {}", query);
        return couponSearchRepository.search(queryStringQuery(query), pageable).map(couponMapper::toDto);
    }
}
