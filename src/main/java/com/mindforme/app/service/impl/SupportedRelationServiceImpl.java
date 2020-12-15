package com.mindforme.app.service.impl;

import static org.elasticsearch.index.query.QueryBuilders.*;

import com.mindforme.app.domain.SupportedRelation;
import com.mindforme.app.repository.SupportedRelationRepository;
import com.mindforme.app.repository.search.SupportedRelationSearchRepository;
import com.mindforme.app.service.SupportedRelationService;
import com.mindforme.app.service.dto.SupportedRelationDTO;
import com.mindforme.app.service.mapper.SupportedRelationMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link SupportedRelation}.
 */
@Service
@Transactional
public class SupportedRelationServiceImpl implements SupportedRelationService {
    private final Logger log = LoggerFactory.getLogger(SupportedRelationServiceImpl.class);

    private final SupportedRelationRepository supportedRelationRepository;

    private final SupportedRelationMapper supportedRelationMapper;

    private final SupportedRelationSearchRepository supportedRelationSearchRepository;

    public SupportedRelationServiceImpl(
        SupportedRelationRepository supportedRelationRepository,
        SupportedRelationMapper supportedRelationMapper,
        SupportedRelationSearchRepository supportedRelationSearchRepository
    ) {
        this.supportedRelationRepository = supportedRelationRepository;
        this.supportedRelationMapper = supportedRelationMapper;
        this.supportedRelationSearchRepository = supportedRelationSearchRepository;
    }

    @Override
    public SupportedRelationDTO save(SupportedRelationDTO supportedRelationDTO) {
        log.debug("Request to save SupportedRelation : {}", supportedRelationDTO);
        SupportedRelation supportedRelation = supportedRelationMapper.toEntity(supportedRelationDTO);
        supportedRelation = supportedRelationRepository.save(supportedRelation);
        SupportedRelationDTO result = supportedRelationMapper.toDto(supportedRelation);
        supportedRelationSearchRepository.save(supportedRelation);
        return result;
    }

    @Override
    @Transactional(readOnly = true)
    public Page<SupportedRelationDTO> findAll(Pageable pageable) {
        log.debug("Request to get all SupportedRelations");
        return supportedRelationRepository.findAll(pageable).map(supportedRelationMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<SupportedRelationDTO> findOne(Long id) {
        log.debug("Request to get SupportedRelation : {}", id);
        return supportedRelationRepository.findById(id).map(supportedRelationMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete SupportedRelation : {}", id);
        supportedRelationRepository.deleteById(id);
        supportedRelationSearchRepository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<SupportedRelationDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of SupportedRelations for query {}", query);
        return supportedRelationSearchRepository.search(queryStringQuery(query), pageable).map(supportedRelationMapper::toDto);
    }
}
