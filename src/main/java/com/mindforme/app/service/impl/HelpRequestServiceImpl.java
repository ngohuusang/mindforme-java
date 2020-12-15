package com.mindforme.app.service.impl;

import static org.elasticsearch.index.query.QueryBuilders.*;

import com.mindforme.app.domain.HelpRequest;
import com.mindforme.app.repository.HelpRequestRepository;
import com.mindforme.app.repository.search.HelpRequestSearchRepository;
import com.mindforme.app.service.HelpRequestService;
import com.mindforme.app.service.dto.HelpRequestDTO;
import com.mindforme.app.service.mapper.HelpRequestMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link HelpRequest}.
 */
@Service
@Transactional
public class HelpRequestServiceImpl implements HelpRequestService {
    private final Logger log = LoggerFactory.getLogger(HelpRequestServiceImpl.class);

    private final HelpRequestRepository helpRequestRepository;

    private final HelpRequestMapper helpRequestMapper;

    private final HelpRequestSearchRepository helpRequestSearchRepository;

    public HelpRequestServiceImpl(
        HelpRequestRepository helpRequestRepository,
        HelpRequestMapper helpRequestMapper,
        HelpRequestSearchRepository helpRequestSearchRepository
    ) {
        this.helpRequestRepository = helpRequestRepository;
        this.helpRequestMapper = helpRequestMapper;
        this.helpRequestSearchRepository = helpRequestSearchRepository;
    }

    @Override
    public HelpRequestDTO save(HelpRequestDTO helpRequestDTO) {
        log.debug("Request to save HelpRequest : {}", helpRequestDTO);
        HelpRequest helpRequest = helpRequestMapper.toEntity(helpRequestDTO);
        helpRequest = helpRequestRepository.save(helpRequest);
        HelpRequestDTO result = helpRequestMapper.toDto(helpRequest);
        helpRequestSearchRepository.save(helpRequest);
        return result;
    }

    @Override
    @Transactional(readOnly = true)
    public Page<HelpRequestDTO> findAll(Pageable pageable) {
        log.debug("Request to get all HelpRequests");
        return helpRequestRepository.findAll(pageable).map(helpRequestMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<HelpRequestDTO> findOne(Long id) {
        log.debug("Request to get HelpRequest : {}", id);
        return helpRequestRepository.findById(id).map(helpRequestMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete HelpRequest : {}", id);
        helpRequestRepository.deleteById(id);
        helpRequestSearchRepository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<HelpRequestDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of HelpRequests for query {}", query);
        return helpRequestSearchRepository.search(queryStringQuery(query), pageable).map(helpRequestMapper::toDto);
    }
}
