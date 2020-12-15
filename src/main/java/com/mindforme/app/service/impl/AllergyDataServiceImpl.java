package com.mindforme.app.service.impl;

import static org.elasticsearch.index.query.QueryBuilders.*;

import com.mindforme.app.domain.AllergyData;
import com.mindforme.app.repository.AllergyDataRepository;
import com.mindforme.app.repository.search.AllergyDataSearchRepository;
import com.mindforme.app.service.AllergyDataService;
import com.mindforme.app.service.dto.AllergyDataDTO;
import com.mindforme.app.service.mapper.AllergyDataMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link AllergyData}.
 */
@Service
@Transactional
public class AllergyDataServiceImpl implements AllergyDataService {
    private final Logger log = LoggerFactory.getLogger(AllergyDataServiceImpl.class);

    private final AllergyDataRepository allergyDataRepository;

    private final AllergyDataMapper allergyDataMapper;

    private final AllergyDataSearchRepository allergyDataSearchRepository;

    public AllergyDataServiceImpl(
        AllergyDataRepository allergyDataRepository,
        AllergyDataMapper allergyDataMapper,
        AllergyDataSearchRepository allergyDataSearchRepository
    ) {
        this.allergyDataRepository = allergyDataRepository;
        this.allergyDataMapper = allergyDataMapper;
        this.allergyDataSearchRepository = allergyDataSearchRepository;
    }

    @Override
    public AllergyDataDTO save(AllergyDataDTO allergyDataDTO) {
        log.debug("Request to save AllergyData : {}", allergyDataDTO);
        AllergyData allergyData = allergyDataMapper.toEntity(allergyDataDTO);
        allergyData = allergyDataRepository.save(allergyData);
        AllergyDataDTO result = allergyDataMapper.toDto(allergyData);
        allergyDataSearchRepository.save(allergyData);
        return result;
    }

    @Override
    @Transactional(readOnly = true)
    public Page<AllergyDataDTO> findAll(Pageable pageable) {
        log.debug("Request to get all AllergyData");
        return allergyDataRepository.findAll(pageable).map(allergyDataMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<AllergyDataDTO> findOne(Long id) {
        log.debug("Request to get AllergyData : {}", id);
        return allergyDataRepository.findById(id).map(allergyDataMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete AllergyData : {}", id);
        allergyDataRepository.deleteById(id);
        allergyDataSearchRepository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<AllergyDataDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of AllergyData for query {}", query);
        return allergyDataSearchRepository.search(queryStringQuery(query), pageable).map(allergyDataMapper::toDto);
    }
}
