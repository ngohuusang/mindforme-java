package com.mindforme.app.service.impl;

import static org.elasticsearch.index.query.QueryBuilders.*;

import com.mindforme.app.domain.ChildHelpRequest;
import com.mindforme.app.repository.ChildHelpRequestRepository;
import com.mindforme.app.repository.search.ChildHelpRequestSearchRepository;
import com.mindforme.app.service.ChildHelpRequestService;
import com.mindforme.app.service.dto.ChildHelpRequestDTO;
import com.mindforme.app.service.mapper.ChildHelpRequestMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link ChildHelpRequest}.
 */
@Service
@Transactional
public class ChildHelpRequestServiceImpl implements ChildHelpRequestService {
    private final Logger log = LoggerFactory.getLogger(ChildHelpRequestServiceImpl.class);

    private final ChildHelpRequestRepository childHelpRequestRepository;

    private final ChildHelpRequestMapper childHelpRequestMapper;

    private final ChildHelpRequestSearchRepository childHelpRequestSearchRepository;

    public ChildHelpRequestServiceImpl(
        ChildHelpRequestRepository childHelpRequestRepository,
        ChildHelpRequestMapper childHelpRequestMapper,
        ChildHelpRequestSearchRepository childHelpRequestSearchRepository
    ) {
        this.childHelpRequestRepository = childHelpRequestRepository;
        this.childHelpRequestMapper = childHelpRequestMapper;
        this.childHelpRequestSearchRepository = childHelpRequestSearchRepository;
    }

    @Override
    public ChildHelpRequestDTO save(ChildHelpRequestDTO childHelpRequestDTO) {
        log.debug("Request to save ChildHelpRequest : {}", childHelpRequestDTO);
        ChildHelpRequest childHelpRequest = childHelpRequestMapper.toEntity(childHelpRequestDTO);
        childHelpRequest = childHelpRequestRepository.save(childHelpRequest);
        ChildHelpRequestDTO result = childHelpRequestMapper.toDto(childHelpRequest);
        childHelpRequestSearchRepository.save(childHelpRequest);
        return result;
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ChildHelpRequestDTO> findAll(Pageable pageable) {
        log.debug("Request to get all ChildHelpRequests");
        return childHelpRequestRepository.findAll(pageable).map(childHelpRequestMapper::toDto);
    }

    public Page<ChildHelpRequestDTO> findAllWithEagerRelationships(Pageable pageable) {
        return childHelpRequestRepository.findAllWithEagerRelationships(pageable).map(childHelpRequestMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<ChildHelpRequestDTO> findOne(Long id) {
        log.debug("Request to get ChildHelpRequest : {}", id);
        return childHelpRequestRepository.findOneWithEagerRelationships(id).map(childHelpRequestMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete ChildHelpRequest : {}", id);
        childHelpRequestRepository.deleteById(id);
        childHelpRequestSearchRepository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ChildHelpRequestDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of ChildHelpRequests for query {}", query);
        return childHelpRequestSearchRepository.search(queryStringQuery(query), pageable).map(childHelpRequestMapper::toDto);
    }
}
