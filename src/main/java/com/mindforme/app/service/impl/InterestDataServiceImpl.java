package com.mindforme.app.service.impl;

import static org.elasticsearch.index.query.QueryBuilders.*;

import com.mindforme.app.domain.InterestData;
import com.mindforme.app.repository.InterestDataRepository;
import com.mindforme.app.repository.search.InterestDataSearchRepository;
import com.mindforme.app.service.InterestDataService;
import com.mindforme.app.service.dto.InterestDataDTO;
import com.mindforme.app.service.mapper.InterestDataMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link InterestData}.
 */
@Service
@Transactional
public class InterestDataServiceImpl implements InterestDataService {
    private final Logger log = LoggerFactory.getLogger(InterestDataServiceImpl.class);

    private final InterestDataRepository interestDataRepository;

    private final InterestDataMapper interestDataMapper;

    private final InterestDataSearchRepository interestDataSearchRepository;

    public InterestDataServiceImpl(
        InterestDataRepository interestDataRepository,
        InterestDataMapper interestDataMapper,
        InterestDataSearchRepository interestDataSearchRepository
    ) {
        this.interestDataRepository = interestDataRepository;
        this.interestDataMapper = interestDataMapper;
        this.interestDataSearchRepository = interestDataSearchRepository;
    }

    @Override
    public InterestDataDTO save(InterestDataDTO interestDataDTO) {
        log.debug("Request to save InterestData : {}", interestDataDTO);
        InterestData interestData = interestDataMapper.toEntity(interestDataDTO);
        interestData = interestDataRepository.save(interestData);
        InterestDataDTO result = interestDataMapper.toDto(interestData);
        interestDataSearchRepository.save(interestData);
        return result;
    }

    @Override
    @Transactional(readOnly = true)
    public Page<InterestDataDTO> findAll(Pageable pageable) {
        log.debug("Request to get all InterestData");
        return interestDataRepository.findAll(pageable).map(interestDataMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<InterestDataDTO> findOne(Long id) {
        log.debug("Request to get InterestData : {}", id);
        return interestDataRepository.findById(id).map(interestDataMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete InterestData : {}", id);
        interestDataRepository.deleteById(id);
        interestDataSearchRepository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<InterestDataDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of InterestData for query {}", query);
        return interestDataSearchRepository.search(queryStringQuery(query), pageable).map(interestDataMapper::toDto);
    }
}
