package com.mindforme.app.service.impl;

import static org.elasticsearch.index.query.QueryBuilders.*;

import com.mindforme.app.domain.SupportedHelpRequest;
import com.mindforme.app.repository.SupportedHelpRequestRepository;
import com.mindforme.app.repository.search.SupportedHelpRequestSearchRepository;
import com.mindforme.app.service.SupportedHelpRequestService;
import com.mindforme.app.service.dto.SupportedHelpRequestDTO;
import com.mindforme.app.service.mapper.SupportedHelpRequestMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link SupportedHelpRequest}.
 */
@Service
@Transactional
public class SupportedHelpRequestServiceImpl implements SupportedHelpRequestService {
    private final Logger log = LoggerFactory.getLogger(SupportedHelpRequestServiceImpl.class);

    private final SupportedHelpRequestRepository supportedHelpRequestRepository;

    private final SupportedHelpRequestMapper supportedHelpRequestMapper;

    private final SupportedHelpRequestSearchRepository supportedHelpRequestSearchRepository;

    public SupportedHelpRequestServiceImpl(
        SupportedHelpRequestRepository supportedHelpRequestRepository,
        SupportedHelpRequestMapper supportedHelpRequestMapper,
        SupportedHelpRequestSearchRepository supportedHelpRequestSearchRepository
    ) {
        this.supportedHelpRequestRepository = supportedHelpRequestRepository;
        this.supportedHelpRequestMapper = supportedHelpRequestMapper;
        this.supportedHelpRequestSearchRepository = supportedHelpRequestSearchRepository;
    }

    @Override
    public SupportedHelpRequestDTO save(SupportedHelpRequestDTO supportedHelpRequestDTO) {
        log.debug("Request to save SupportedHelpRequest : {}", supportedHelpRequestDTO);
        SupportedHelpRequest supportedHelpRequest = supportedHelpRequestMapper.toEntity(supportedHelpRequestDTO);
        supportedHelpRequest = supportedHelpRequestRepository.save(supportedHelpRequest);
        SupportedHelpRequestDTO result = supportedHelpRequestMapper.toDto(supportedHelpRequest);
        supportedHelpRequestSearchRepository.save(supportedHelpRequest);
        return result;
    }

    @Override
    @Transactional(readOnly = true)
    public Page<SupportedHelpRequestDTO> findAll(Pageable pageable) {
        log.debug("Request to get all SupportedHelpRequests");
        return supportedHelpRequestRepository.findAll(pageable).map(supportedHelpRequestMapper::toDto);
    }

    public Page<SupportedHelpRequestDTO> findAllWithEagerRelationships(Pageable pageable) {
        return supportedHelpRequestRepository.findAllWithEagerRelationships(pageable).map(supportedHelpRequestMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<SupportedHelpRequestDTO> findOne(Long id) {
        log.debug("Request to get SupportedHelpRequest : {}", id);
        return supportedHelpRequestRepository.findOneWithEagerRelationships(id).map(supportedHelpRequestMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete SupportedHelpRequest : {}", id);
        supportedHelpRequestRepository.deleteById(id);
        supportedHelpRequestSearchRepository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<SupportedHelpRequestDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of SupportedHelpRequests for query {}", query);
        return supportedHelpRequestSearchRepository.search(queryStringQuery(query), pageable).map(supportedHelpRequestMapper::toDto);
    }
}
