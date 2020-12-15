package com.mindforme.app.service.impl;

import static org.elasticsearch.index.query.QueryBuilders.*;

import com.mindforme.app.domain.RuleData;
import com.mindforme.app.repository.RuleDataRepository;
import com.mindforme.app.repository.search.RuleDataSearchRepository;
import com.mindforme.app.service.RuleDataService;
import com.mindforme.app.service.dto.RuleDataDTO;
import com.mindforme.app.service.mapper.RuleDataMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link RuleData}.
 */
@Service
@Transactional
public class RuleDataServiceImpl implements RuleDataService {
    private final Logger log = LoggerFactory.getLogger(RuleDataServiceImpl.class);

    private final RuleDataRepository ruleDataRepository;

    private final RuleDataMapper ruleDataMapper;

    private final RuleDataSearchRepository ruleDataSearchRepository;

    public RuleDataServiceImpl(
        RuleDataRepository ruleDataRepository,
        RuleDataMapper ruleDataMapper,
        RuleDataSearchRepository ruleDataSearchRepository
    ) {
        this.ruleDataRepository = ruleDataRepository;
        this.ruleDataMapper = ruleDataMapper;
        this.ruleDataSearchRepository = ruleDataSearchRepository;
    }

    @Override
    public RuleDataDTO save(RuleDataDTO ruleDataDTO) {
        log.debug("Request to save RuleData : {}", ruleDataDTO);
        RuleData ruleData = ruleDataMapper.toEntity(ruleDataDTO);
        ruleData = ruleDataRepository.save(ruleData);
        RuleDataDTO result = ruleDataMapper.toDto(ruleData);
        ruleDataSearchRepository.save(ruleData);
        return result;
    }

    @Override
    @Transactional(readOnly = true)
    public Page<RuleDataDTO> findAll(Pageable pageable) {
        log.debug("Request to get all RuleData");
        return ruleDataRepository.findAll(pageable).map(ruleDataMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<RuleDataDTO> findOne(Long id) {
        log.debug("Request to get RuleData : {}", id);
        return ruleDataRepository.findById(id).map(ruleDataMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete RuleData : {}", id);
        ruleDataRepository.deleteById(id);
        ruleDataSearchRepository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<RuleDataDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of RuleData for query {}", query);
        return ruleDataSearchRepository.search(queryStringQuery(query), pageable).map(ruleDataMapper::toDto);
    }
}
