package com.mindforme.app.service.impl;

import static org.elasticsearch.index.query.QueryBuilders.*;

import com.mindforme.app.domain.PlanData;
import com.mindforme.app.repository.PlanDataRepository;
import com.mindforme.app.repository.search.PlanDataSearchRepository;
import com.mindforme.app.service.PlanDataService;
import com.mindforme.app.service.dto.PlanDataDTO;
import com.mindforme.app.service.mapper.PlanDataMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link PlanData}.
 */
@Service
@Transactional
public class PlanDataServiceImpl implements PlanDataService {
    private final Logger log = LoggerFactory.getLogger(PlanDataServiceImpl.class);

    private final PlanDataRepository planDataRepository;

    private final PlanDataMapper planDataMapper;

    private final PlanDataSearchRepository planDataSearchRepository;

    public PlanDataServiceImpl(
        PlanDataRepository planDataRepository,
        PlanDataMapper planDataMapper,
        PlanDataSearchRepository planDataSearchRepository
    ) {
        this.planDataRepository = planDataRepository;
        this.planDataMapper = planDataMapper;
        this.planDataSearchRepository = planDataSearchRepository;
    }

    @Override
    public PlanDataDTO save(PlanDataDTO planDataDTO) {
        log.debug("Request to save PlanData : {}", planDataDTO);
        PlanData planData = planDataMapper.toEntity(planDataDTO);
        planData = planDataRepository.save(planData);
        PlanDataDTO result = planDataMapper.toDto(planData);
        planDataSearchRepository.save(planData);
        return result;
    }

    @Override
    @Transactional(readOnly = true)
    public Page<PlanDataDTO> findAll(Pageable pageable) {
        log.debug("Request to get all PlanData");
        return planDataRepository.findAll(pageable).map(planDataMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<PlanDataDTO> findOne(Long id) {
        log.debug("Request to get PlanData : {}", id);
        return planDataRepository.findById(id).map(planDataMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete PlanData : {}", id);
        planDataRepository.deleteById(id);
        planDataSearchRepository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<PlanDataDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of PlanData for query {}", query);
        return planDataSearchRepository.search(queryStringQuery(query), pageable).map(planDataMapper::toDto);
    }
}
