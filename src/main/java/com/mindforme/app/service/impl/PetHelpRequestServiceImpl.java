package com.mindforme.app.service.impl;

import static org.elasticsearch.index.query.QueryBuilders.*;

import com.mindforme.app.domain.PetHelpRequest;
import com.mindforme.app.repository.PetHelpRequestRepository;
import com.mindforme.app.repository.search.PetHelpRequestSearchRepository;
import com.mindforme.app.service.PetHelpRequestService;
import com.mindforme.app.service.dto.PetHelpRequestDTO;
import com.mindforme.app.service.mapper.PetHelpRequestMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link PetHelpRequest}.
 */
@Service
@Transactional
public class PetHelpRequestServiceImpl implements PetHelpRequestService {
    private final Logger log = LoggerFactory.getLogger(PetHelpRequestServiceImpl.class);

    private final PetHelpRequestRepository petHelpRequestRepository;

    private final PetHelpRequestMapper petHelpRequestMapper;

    private final PetHelpRequestSearchRepository petHelpRequestSearchRepository;

    public PetHelpRequestServiceImpl(
        PetHelpRequestRepository petHelpRequestRepository,
        PetHelpRequestMapper petHelpRequestMapper,
        PetHelpRequestSearchRepository petHelpRequestSearchRepository
    ) {
        this.petHelpRequestRepository = petHelpRequestRepository;
        this.petHelpRequestMapper = petHelpRequestMapper;
        this.petHelpRequestSearchRepository = petHelpRequestSearchRepository;
    }

    @Override
    public PetHelpRequestDTO save(PetHelpRequestDTO petHelpRequestDTO) {
        log.debug("Request to save PetHelpRequest : {}", petHelpRequestDTO);
        PetHelpRequest petHelpRequest = petHelpRequestMapper.toEntity(petHelpRequestDTO);
        petHelpRequest = petHelpRequestRepository.save(petHelpRequest);
        PetHelpRequestDTO result = petHelpRequestMapper.toDto(petHelpRequest);
        petHelpRequestSearchRepository.save(petHelpRequest);
        return result;
    }

    @Override
    @Transactional(readOnly = true)
    public Page<PetHelpRequestDTO> findAll(Pageable pageable) {
        log.debug("Request to get all PetHelpRequests");
        return petHelpRequestRepository.findAll(pageable).map(petHelpRequestMapper::toDto);
    }

    public Page<PetHelpRequestDTO> findAllWithEagerRelationships(Pageable pageable) {
        return petHelpRequestRepository.findAllWithEagerRelationships(pageable).map(petHelpRequestMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<PetHelpRequestDTO> findOne(Long id) {
        log.debug("Request to get PetHelpRequest : {}", id);
        return petHelpRequestRepository.findOneWithEagerRelationships(id).map(petHelpRequestMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete PetHelpRequest : {}", id);
        petHelpRequestRepository.deleteById(id);
        petHelpRequestSearchRepository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<PetHelpRequestDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of PetHelpRequests for query {}", query);
        return petHelpRequestSearchRepository.search(queryStringQuery(query), pageable).map(petHelpRequestMapper::toDto);
    }
}
