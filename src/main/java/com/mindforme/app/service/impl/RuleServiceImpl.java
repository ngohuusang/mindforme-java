package com.mindforme.app.service.impl;

import static org.elasticsearch.index.query.QueryBuilders.*;

import com.mindforme.app.domain.Rule;
import com.mindforme.app.repository.RuleRepository;
import com.mindforme.app.repository.search.RuleSearchRepository;
import com.mindforme.app.service.RuleService;
import com.mindforme.app.service.dto.RuleDTO;
import com.mindforme.app.service.mapper.RuleMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Rule}.
 */
@Service
@Transactional
public class RuleServiceImpl implements RuleService {
    private final Logger log = LoggerFactory.getLogger(RuleServiceImpl.class);

    private final RuleRepository ruleRepository;

    private final RuleMapper ruleMapper;

    private final RuleSearchRepository ruleSearchRepository;

    public RuleServiceImpl(RuleRepository ruleRepository, RuleMapper ruleMapper, RuleSearchRepository ruleSearchRepository) {
        this.ruleRepository = ruleRepository;
        this.ruleMapper = ruleMapper;
        this.ruleSearchRepository = ruleSearchRepository;
    }

    @Override
    public RuleDTO save(RuleDTO ruleDTO) {
        log.debug("Request to save Rule : {}", ruleDTO);
        Rule rule = ruleMapper.toEntity(ruleDTO);
        rule = ruleRepository.save(rule);
        RuleDTO result = ruleMapper.toDto(rule);
        ruleSearchRepository.save(rule);
        return result;
    }

    @Override
    @Transactional(readOnly = true)
    public Page<RuleDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Rules");
        return ruleRepository.findAll(pageable).map(ruleMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<RuleDTO> findOne(Long id) {
        log.debug("Request to get Rule : {}", id);
        return ruleRepository.findById(id).map(ruleMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Rule : {}", id);
        ruleRepository.deleteById(id);
        ruleSearchRepository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<RuleDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of Rules for query {}", query);
        return ruleSearchRepository.search(queryStringQuery(query), pageable).map(ruleMapper::toDto);
    }
}
