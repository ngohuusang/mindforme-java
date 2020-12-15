package com.mindforme.app.service.impl;

import static org.elasticsearch.index.query.QueryBuilders.*;

import com.mindforme.app.domain.GardenHelpRequest;
import com.mindforme.app.repository.GardenHelpRequestRepository;
import com.mindforme.app.repository.search.GardenHelpRequestSearchRepository;
import com.mindforme.app.service.GardenHelpRequestService;
import com.mindforme.app.service.dto.GardenHelpRequestDTO;
import com.mindforme.app.service.mapper.GardenHelpRequestMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link GardenHelpRequest}.
 */
@Service
@Transactional
public class GardenHelpRequestServiceImpl implements GardenHelpRequestService {
    private final Logger log = LoggerFactory.getLogger(GardenHelpRequestServiceImpl.class);

    private final GardenHelpRequestRepository gardenHelpRequestRepository;

    private final GardenHelpRequestMapper gardenHelpRequestMapper;

    private final GardenHelpRequestSearchRepository gardenHelpRequestSearchRepository;

    public GardenHelpRequestServiceImpl(
        GardenHelpRequestRepository gardenHelpRequestRepository,
        GardenHelpRequestMapper gardenHelpRequestMapper,
        GardenHelpRequestSearchRepository gardenHelpRequestSearchRepository
    ) {
        this.gardenHelpRequestRepository = gardenHelpRequestRepository;
        this.gardenHelpRequestMapper = gardenHelpRequestMapper;
        this.gardenHelpRequestSearchRepository = gardenHelpRequestSearchRepository;
    }

    @Override
    public GardenHelpRequestDTO save(GardenHelpRequestDTO gardenHelpRequestDTO) {
        log.debug("Request to save GardenHelpRequest : {}", gardenHelpRequestDTO);
        GardenHelpRequest gardenHelpRequest = gardenHelpRequestMapper.toEntity(gardenHelpRequestDTO);
        gardenHelpRequest = gardenHelpRequestRepository.save(gardenHelpRequest);
        GardenHelpRequestDTO result = gardenHelpRequestMapper.toDto(gardenHelpRequest);
        gardenHelpRequestSearchRepository.save(gardenHelpRequest);
        return result;
    }

    @Override
    @Transactional(readOnly = true)
    public Page<GardenHelpRequestDTO> findAll(Pageable pageable) {
        log.debug("Request to get all GardenHelpRequests");
        return gardenHelpRequestRepository.findAll(pageable).map(gardenHelpRequestMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<GardenHelpRequestDTO> findOne(Long id) {
        log.debug("Request to get GardenHelpRequest : {}", id);
        return gardenHelpRequestRepository.findById(id).map(gardenHelpRequestMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete GardenHelpRequest : {}", id);
        gardenHelpRequestRepository.deleteById(id);
        gardenHelpRequestSearchRepository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<GardenHelpRequestDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of GardenHelpRequests for query {}", query);
        return gardenHelpRequestSearchRepository.search(queryStringQuery(query), pageable).map(gardenHelpRequestMapper::toDto);
    }
}
