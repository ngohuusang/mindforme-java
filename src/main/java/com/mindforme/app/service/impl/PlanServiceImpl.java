package com.mindforme.app.service.impl;

import static org.elasticsearch.index.query.QueryBuilders.*;

import com.mindforme.app.domain.Plan;
import com.mindforme.app.repository.PlanRepository;
import com.mindforme.app.repository.search.PlanSearchRepository;
import com.mindforme.app.service.PlanService;
import com.mindforme.app.service.dto.PlanDTO;
import com.mindforme.app.service.mapper.PlanMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Plan}.
 */
@Service
@Transactional
public class PlanServiceImpl implements PlanService {
    private final Logger log = LoggerFactory.getLogger(PlanServiceImpl.class);

    private final PlanRepository planRepository;

    private final PlanMapper planMapper;

    private final PlanSearchRepository planSearchRepository;

    public PlanServiceImpl(PlanRepository planRepository, PlanMapper planMapper, PlanSearchRepository planSearchRepository) {
        this.planRepository = planRepository;
        this.planMapper = planMapper;
        this.planSearchRepository = planSearchRepository;
    }

    @Override
    public PlanDTO save(PlanDTO planDTO) {
        log.debug("Request to save Plan : {}", planDTO);
        Plan plan = planMapper.toEntity(planDTO);
        plan = planRepository.save(plan);
        PlanDTO result = planMapper.toDto(plan);
        planSearchRepository.save(plan);
        return result;
    }

    @Override
    @Transactional(readOnly = true)
    public Page<PlanDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Plans");
        return planRepository.findAll(pageable).map(planMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<PlanDTO> findOne(Long id) {
        log.debug("Request to get Plan : {}", id);
        return planRepository.findById(id).map(planMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Plan : {}", id);
        planRepository.deleteById(id);
        planSearchRepository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<PlanDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of Plans for query {}", query);
        return planSearchRepository.search(queryStringQuery(query), pageable).map(planMapper::toDto);
    }
}
