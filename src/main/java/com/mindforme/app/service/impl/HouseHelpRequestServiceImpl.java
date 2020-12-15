package com.mindforme.app.service.impl;

import static org.elasticsearch.index.query.QueryBuilders.*;

import com.mindforme.app.domain.HouseHelpRequest;
import com.mindforme.app.repository.HouseHelpRequestRepository;
import com.mindforme.app.repository.search.HouseHelpRequestSearchRepository;
import com.mindforme.app.service.HouseHelpRequestService;
import com.mindforme.app.service.dto.HouseHelpRequestDTO;
import com.mindforme.app.service.mapper.HouseHelpRequestMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link HouseHelpRequest}.
 */
@Service
@Transactional
public class HouseHelpRequestServiceImpl implements HouseHelpRequestService {
    private final Logger log = LoggerFactory.getLogger(HouseHelpRequestServiceImpl.class);

    private final HouseHelpRequestRepository houseHelpRequestRepository;

    private final HouseHelpRequestMapper houseHelpRequestMapper;

    private final HouseHelpRequestSearchRepository houseHelpRequestSearchRepository;

    public HouseHelpRequestServiceImpl(
        HouseHelpRequestRepository houseHelpRequestRepository,
        HouseHelpRequestMapper houseHelpRequestMapper,
        HouseHelpRequestSearchRepository houseHelpRequestSearchRepository
    ) {
        this.houseHelpRequestRepository = houseHelpRequestRepository;
        this.houseHelpRequestMapper = houseHelpRequestMapper;
        this.houseHelpRequestSearchRepository = houseHelpRequestSearchRepository;
    }

    @Override
    public HouseHelpRequestDTO save(HouseHelpRequestDTO houseHelpRequestDTO) {
        log.debug("Request to save HouseHelpRequest : {}", houseHelpRequestDTO);
        HouseHelpRequest houseHelpRequest = houseHelpRequestMapper.toEntity(houseHelpRequestDTO);
        houseHelpRequest = houseHelpRequestRepository.save(houseHelpRequest);
        HouseHelpRequestDTO result = houseHelpRequestMapper.toDto(houseHelpRequest);
        houseHelpRequestSearchRepository.save(houseHelpRequest);
        return result;
    }

    @Override
    @Transactional(readOnly = true)
    public Page<HouseHelpRequestDTO> findAll(Pageable pageable) {
        log.debug("Request to get all HouseHelpRequests");
        return houseHelpRequestRepository.findAll(pageable).map(houseHelpRequestMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<HouseHelpRequestDTO> findOne(Long id) {
        log.debug("Request to get HouseHelpRequest : {}", id);
        return houseHelpRequestRepository.findById(id).map(houseHelpRequestMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete HouseHelpRequest : {}", id);
        houseHelpRequestRepository.deleteById(id);
        houseHelpRequestSearchRepository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<HouseHelpRequestDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of HouseHelpRequests for query {}", query);
        return houseHelpRequestSearchRepository.search(queryStringQuery(query), pageable).map(houseHelpRequestMapper::toDto);
    }
}
