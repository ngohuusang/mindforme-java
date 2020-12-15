package com.mindforme.app.service.impl;

import static org.elasticsearch.index.query.QueryBuilders.*;

import com.mindforme.app.domain.Supported;
import com.mindforme.app.repository.SupportedRepository;
import com.mindforme.app.repository.search.SupportedSearchRepository;
import com.mindforme.app.service.SupportedService;
import com.mindforme.app.service.dto.SupportedDTO;
import com.mindforme.app.service.mapper.SupportedMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Supported}.
 */
@Service
@Transactional
public class SupportedServiceImpl implements SupportedService {
    private final Logger log = LoggerFactory.getLogger(SupportedServiceImpl.class);

    private final SupportedRepository supportedRepository;

    private final SupportedMapper supportedMapper;

    private final SupportedSearchRepository supportedSearchRepository;

    public SupportedServiceImpl(
        SupportedRepository supportedRepository,
        SupportedMapper supportedMapper,
        SupportedSearchRepository supportedSearchRepository
    ) {
        this.supportedRepository = supportedRepository;
        this.supportedMapper = supportedMapper;
        this.supportedSearchRepository = supportedSearchRepository;
    }

    @Override
    public SupportedDTO save(SupportedDTO supportedDTO) {
        log.debug("Request to save Supported : {}", supportedDTO);
        Supported supported = supportedMapper.toEntity(supportedDTO);
        supported = supportedRepository.save(supported);
        SupportedDTO result = supportedMapper.toDto(supported);
        supportedSearchRepository.save(supported);
        return result;
    }

    @Override
    @Transactional(readOnly = true)
    public Page<SupportedDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Supporteds");
        return supportedRepository.findAll(pageable).map(supportedMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<SupportedDTO> findOne(Long id) {
        log.debug("Request to get Supported : {}", id);
        return supportedRepository.findById(id).map(supportedMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Supported : {}", id);
        supportedRepository.deleteById(id);
        supportedSearchRepository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<SupportedDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of Supporteds for query {}", query);
        return supportedSearchRepository.search(queryStringQuery(query), pageable).map(supportedMapper::toDto);
    }
}
