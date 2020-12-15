package com.mindforme.app.service.impl;

import static org.elasticsearch.index.query.QueryBuilders.*;

import com.mindforme.app.domain.Interest;
import com.mindforme.app.repository.InterestRepository;
import com.mindforme.app.repository.search.InterestSearchRepository;
import com.mindforme.app.service.InterestService;
import com.mindforme.app.service.dto.InterestDTO;
import com.mindforme.app.service.mapper.InterestMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Interest}.
 */
@Service
@Transactional
public class InterestServiceImpl implements InterestService {
    private final Logger log = LoggerFactory.getLogger(InterestServiceImpl.class);

    private final InterestRepository interestRepository;

    private final InterestMapper interestMapper;

    private final InterestSearchRepository interestSearchRepository;

    public InterestServiceImpl(
        InterestRepository interestRepository,
        InterestMapper interestMapper,
        InterestSearchRepository interestSearchRepository
    ) {
        this.interestRepository = interestRepository;
        this.interestMapper = interestMapper;
        this.interestSearchRepository = interestSearchRepository;
    }

    @Override
    public InterestDTO save(InterestDTO interestDTO) {
        log.debug("Request to save Interest : {}", interestDTO);
        Interest interest = interestMapper.toEntity(interestDTO);
        interest = interestRepository.save(interest);
        InterestDTO result = interestMapper.toDto(interest);
        interestSearchRepository.save(interest);
        return result;
    }

    @Override
    @Transactional(readOnly = true)
    public Page<InterestDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Interests");
        return interestRepository.findAll(pageable).map(interestMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<InterestDTO> findOne(Long id) {
        log.debug("Request to get Interest : {}", id);
        return interestRepository.findById(id).map(interestMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Interest : {}", id);
        interestRepository.deleteById(id);
        interestSearchRepository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<InterestDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of Interests for query {}", query);
        return interestSearchRepository.search(queryStringQuery(query), pageable).map(interestMapper::toDto);
    }
}
