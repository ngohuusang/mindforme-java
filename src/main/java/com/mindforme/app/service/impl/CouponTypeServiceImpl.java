package com.mindforme.app.service.impl;

import static org.elasticsearch.index.query.QueryBuilders.*;

import com.mindforme.app.domain.CouponType;
import com.mindforme.app.repository.CouponTypeRepository;
import com.mindforme.app.repository.search.CouponTypeSearchRepository;
import com.mindforme.app.service.CouponTypeService;
import com.mindforme.app.service.dto.CouponTypeDTO;
import com.mindforme.app.service.mapper.CouponTypeMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link CouponType}.
 */
@Service
@Transactional
public class CouponTypeServiceImpl implements CouponTypeService {
    private final Logger log = LoggerFactory.getLogger(CouponTypeServiceImpl.class);

    private final CouponTypeRepository couponTypeRepository;

    private final CouponTypeMapper couponTypeMapper;

    private final CouponTypeSearchRepository couponTypeSearchRepository;

    public CouponTypeServiceImpl(
        CouponTypeRepository couponTypeRepository,
        CouponTypeMapper couponTypeMapper,
        CouponTypeSearchRepository couponTypeSearchRepository
    ) {
        this.couponTypeRepository = couponTypeRepository;
        this.couponTypeMapper = couponTypeMapper;
        this.couponTypeSearchRepository = couponTypeSearchRepository;
    }

    @Override
    public CouponTypeDTO save(CouponTypeDTO couponTypeDTO) {
        log.debug("Request to save CouponType : {}", couponTypeDTO);
        CouponType couponType = couponTypeMapper.toEntity(couponTypeDTO);
        couponType = couponTypeRepository.save(couponType);
        CouponTypeDTO result = couponTypeMapper.toDto(couponType);
        couponTypeSearchRepository.save(couponType);
        return result;
    }

    @Override
    @Transactional(readOnly = true)
    public Page<CouponTypeDTO> findAll(Pageable pageable) {
        log.debug("Request to get all CouponTypes");
        return couponTypeRepository.findAll(pageable).map(couponTypeMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<CouponTypeDTO> findOne(Long id) {
        log.debug("Request to get CouponType : {}", id);
        return couponTypeRepository.findById(id).map(couponTypeMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete CouponType : {}", id);
        couponTypeRepository.deleteById(id);
        couponTypeSearchRepository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<CouponTypeDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of CouponTypes for query {}", query);
        return couponTypeSearchRepository.search(queryStringQuery(query), pageable).map(couponTypeMapper::toDto);
    }
}
