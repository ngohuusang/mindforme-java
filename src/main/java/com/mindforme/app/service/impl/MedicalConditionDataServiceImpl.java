package com.mindforme.app.service.impl;

import static org.elasticsearch.index.query.QueryBuilders.*;

import com.mindforme.app.domain.MedicalConditionData;
import com.mindforme.app.repository.MedicalConditionDataRepository;
import com.mindforme.app.repository.search.MedicalConditionDataSearchRepository;
import com.mindforme.app.service.MedicalConditionDataService;
import com.mindforme.app.service.dto.MedicalConditionDataDTO;
import com.mindforme.app.service.mapper.MedicalConditionDataMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link MedicalConditionData}.
 */
@Service
@Transactional
public class MedicalConditionDataServiceImpl implements MedicalConditionDataService {
    private final Logger log = LoggerFactory.getLogger(MedicalConditionDataServiceImpl.class);

    private final MedicalConditionDataRepository medicalConditionDataRepository;

    private final MedicalConditionDataMapper medicalConditionDataMapper;

    private final MedicalConditionDataSearchRepository medicalConditionDataSearchRepository;

    public MedicalConditionDataServiceImpl(
        MedicalConditionDataRepository medicalConditionDataRepository,
        MedicalConditionDataMapper medicalConditionDataMapper,
        MedicalConditionDataSearchRepository medicalConditionDataSearchRepository
    ) {
        this.medicalConditionDataRepository = medicalConditionDataRepository;
        this.medicalConditionDataMapper = medicalConditionDataMapper;
        this.medicalConditionDataSearchRepository = medicalConditionDataSearchRepository;
    }

    @Override
    public MedicalConditionDataDTO save(MedicalConditionDataDTO medicalConditionDataDTO) {
        log.debug("Request to save MedicalConditionData : {}", medicalConditionDataDTO);
        MedicalConditionData medicalConditionData = medicalConditionDataMapper.toEntity(medicalConditionDataDTO);
        medicalConditionData = medicalConditionDataRepository.save(medicalConditionData);
        MedicalConditionDataDTO result = medicalConditionDataMapper.toDto(medicalConditionData);
        medicalConditionDataSearchRepository.save(medicalConditionData);
        return result;
    }

    @Override
    @Transactional(readOnly = true)
    public Page<MedicalConditionDataDTO> findAll(Pageable pageable) {
        log.debug("Request to get all MedicalConditionData");
        return medicalConditionDataRepository.findAll(pageable).map(medicalConditionDataMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<MedicalConditionDataDTO> findOne(Long id) {
        log.debug("Request to get MedicalConditionData : {}", id);
        return medicalConditionDataRepository.findById(id).map(medicalConditionDataMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete MedicalConditionData : {}", id);
        medicalConditionDataRepository.deleteById(id);
        medicalConditionDataSearchRepository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<MedicalConditionDataDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of MedicalConditionData for query {}", query);
        return medicalConditionDataSearchRepository.search(queryStringQuery(query), pageable).map(medicalConditionDataMapper::toDto);
    }
}
