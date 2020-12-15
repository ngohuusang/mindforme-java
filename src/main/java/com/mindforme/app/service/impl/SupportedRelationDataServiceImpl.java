package com.mindforme.app.service.impl;

import static org.elasticsearch.index.query.QueryBuilders.*;

import com.mindforme.app.domain.SupportedRelationData;
import com.mindforme.app.repository.SupportedRelationDataRepository;
import com.mindforme.app.repository.search.SupportedRelationDataSearchRepository;
import com.mindforme.app.service.SupportedRelationDataService;
import com.mindforme.app.service.dto.SupportedRelationDataDTO;
import com.mindforme.app.service.mapper.SupportedRelationDataMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link SupportedRelationData}.
 */
@Service
@Transactional
public class SupportedRelationDataServiceImpl implements SupportedRelationDataService {
    private final Logger log = LoggerFactory.getLogger(SupportedRelationDataServiceImpl.class);

    private final SupportedRelationDataRepository supportedRelationDataRepository;

    private final SupportedRelationDataMapper supportedRelationDataMapper;

    private final SupportedRelationDataSearchRepository supportedRelationDataSearchRepository;

    public SupportedRelationDataServiceImpl(
        SupportedRelationDataRepository supportedRelationDataRepository,
        SupportedRelationDataMapper supportedRelationDataMapper,
        SupportedRelationDataSearchRepository supportedRelationDataSearchRepository
    ) {
        this.supportedRelationDataRepository = supportedRelationDataRepository;
        this.supportedRelationDataMapper = supportedRelationDataMapper;
        this.supportedRelationDataSearchRepository = supportedRelationDataSearchRepository;
    }

    @Override
    public SupportedRelationDataDTO save(SupportedRelationDataDTO supportedRelationDataDTO) {
        log.debug("Request to save SupportedRelationData : {}", supportedRelationDataDTO);
        SupportedRelationData supportedRelationData = supportedRelationDataMapper.toEntity(supportedRelationDataDTO);
        supportedRelationData = supportedRelationDataRepository.save(supportedRelationData);
        SupportedRelationDataDTO result = supportedRelationDataMapper.toDto(supportedRelationData);
        supportedRelationDataSearchRepository.save(supportedRelationData);
        return result;
    }

    @Override
    @Transactional(readOnly = true)
    public Page<SupportedRelationDataDTO> findAll(Pageable pageable) {
        log.debug("Request to get all SupportedRelationData");
        return supportedRelationDataRepository.findAll(pageable).map(supportedRelationDataMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<SupportedRelationDataDTO> findOne(Long id) {
        log.debug("Request to get SupportedRelationData : {}", id);
        return supportedRelationDataRepository.findById(id).map(supportedRelationDataMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete SupportedRelationData : {}", id);
        supportedRelationDataRepository.deleteById(id);
        supportedRelationDataSearchRepository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<SupportedRelationDataDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of SupportedRelationData for query {}", query);
        return supportedRelationDataSearchRepository.search(queryStringQuery(query), pageable).map(supportedRelationDataMapper::toDto);
    }
}
