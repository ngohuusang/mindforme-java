package com.mindforme.app.service.impl;

import static org.elasticsearch.index.query.QueryBuilders.*;

import com.mindforme.app.domain.ChildRelationData;
import com.mindforme.app.repository.ChildRelationDataRepository;
import com.mindforme.app.repository.search.ChildRelationDataSearchRepository;
import com.mindforme.app.service.ChildRelationDataService;
import com.mindforme.app.service.dto.ChildRelationDataDTO;
import com.mindforme.app.service.mapper.ChildRelationDataMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link ChildRelationData}.
 */
@Service
@Transactional
public class ChildRelationDataServiceImpl implements ChildRelationDataService {
    private final Logger log = LoggerFactory.getLogger(ChildRelationDataServiceImpl.class);

    private final ChildRelationDataRepository childRelationDataRepository;

    private final ChildRelationDataMapper childRelationDataMapper;

    private final ChildRelationDataSearchRepository childRelationDataSearchRepository;

    public ChildRelationDataServiceImpl(
        ChildRelationDataRepository childRelationDataRepository,
        ChildRelationDataMapper childRelationDataMapper,
        ChildRelationDataSearchRepository childRelationDataSearchRepository
    ) {
        this.childRelationDataRepository = childRelationDataRepository;
        this.childRelationDataMapper = childRelationDataMapper;
        this.childRelationDataSearchRepository = childRelationDataSearchRepository;
    }

    @Override
    public ChildRelationDataDTO save(ChildRelationDataDTO childRelationDataDTO) {
        log.debug("Request to save ChildRelationData : {}", childRelationDataDTO);
        ChildRelationData childRelationData = childRelationDataMapper.toEntity(childRelationDataDTO);
        childRelationData = childRelationDataRepository.save(childRelationData);
        ChildRelationDataDTO result = childRelationDataMapper.toDto(childRelationData);
        childRelationDataSearchRepository.save(childRelationData);
        return result;
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ChildRelationDataDTO> findAll(Pageable pageable) {
        log.debug("Request to get all ChildRelationData");
        return childRelationDataRepository.findAll(pageable).map(childRelationDataMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<ChildRelationDataDTO> findOne(Long id) {
        log.debug("Request to get ChildRelationData : {}", id);
        return childRelationDataRepository.findById(id).map(childRelationDataMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete ChildRelationData : {}", id);
        childRelationDataRepository.deleteById(id);
        childRelationDataSearchRepository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ChildRelationDataDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of ChildRelationData for query {}", query);
        return childRelationDataSearchRepository.search(queryStringQuery(query), pageable).map(childRelationDataMapper::toDto);
    }
}
