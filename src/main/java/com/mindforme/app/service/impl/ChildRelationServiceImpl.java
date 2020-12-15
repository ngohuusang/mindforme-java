package com.mindforme.app.service.impl;

import static org.elasticsearch.index.query.QueryBuilders.*;

import com.mindforme.app.domain.ChildRelation;
import com.mindforme.app.repository.ChildRelationRepository;
import com.mindforme.app.repository.search.ChildRelationSearchRepository;
import com.mindforme.app.service.ChildRelationService;
import com.mindforme.app.service.dto.ChildRelationDTO;
import com.mindforme.app.service.mapper.ChildRelationMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link ChildRelation}.
 */
@Service
@Transactional
public class ChildRelationServiceImpl implements ChildRelationService {
    private final Logger log = LoggerFactory.getLogger(ChildRelationServiceImpl.class);

    private final ChildRelationRepository childRelationRepository;

    private final ChildRelationMapper childRelationMapper;

    private final ChildRelationSearchRepository childRelationSearchRepository;

    public ChildRelationServiceImpl(
        ChildRelationRepository childRelationRepository,
        ChildRelationMapper childRelationMapper,
        ChildRelationSearchRepository childRelationSearchRepository
    ) {
        this.childRelationRepository = childRelationRepository;
        this.childRelationMapper = childRelationMapper;
        this.childRelationSearchRepository = childRelationSearchRepository;
    }

    @Override
    public ChildRelationDTO save(ChildRelationDTO childRelationDTO) {
        log.debug("Request to save ChildRelation : {}", childRelationDTO);
        ChildRelation childRelation = childRelationMapper.toEntity(childRelationDTO);
        childRelation = childRelationRepository.save(childRelation);
        ChildRelationDTO result = childRelationMapper.toDto(childRelation);
        childRelationSearchRepository.save(childRelation);
        return result;
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ChildRelationDTO> findAll(Pageable pageable) {
        log.debug("Request to get all ChildRelations");
        return childRelationRepository.findAll(pageable).map(childRelationMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<ChildRelationDTO> findOne(Long id) {
        log.debug("Request to get ChildRelation : {}", id);
        return childRelationRepository.findById(id).map(childRelationMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete ChildRelation : {}", id);
        childRelationRepository.deleteById(id);
        childRelationSearchRepository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ChildRelationDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of ChildRelations for query {}", query);
        return childRelationSearchRepository.search(queryStringQuery(query), pageable).map(childRelationMapper::toDto);
    }
}
