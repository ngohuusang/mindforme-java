package com.mindforme.app.service.impl;

import static org.elasticsearch.index.query.QueryBuilders.*;

import com.mindforme.app.domain.MedicalCondition;
import com.mindforme.app.repository.MedicalConditionRepository;
import com.mindforme.app.repository.search.MedicalConditionSearchRepository;
import com.mindforme.app.service.MedicalConditionService;
import com.mindforme.app.service.dto.MedicalConditionDTO;
import com.mindforme.app.service.mapper.MedicalConditionMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link MedicalCondition}.
 */
@Service
@Transactional
public class MedicalConditionServiceImpl implements MedicalConditionService {
    private final Logger log = LoggerFactory.getLogger(MedicalConditionServiceImpl.class);

    private final MedicalConditionRepository medicalConditionRepository;

    private final MedicalConditionMapper medicalConditionMapper;

    private final MedicalConditionSearchRepository medicalConditionSearchRepository;

    public MedicalConditionServiceImpl(
        MedicalConditionRepository medicalConditionRepository,
        MedicalConditionMapper medicalConditionMapper,
        MedicalConditionSearchRepository medicalConditionSearchRepository
    ) {
        this.medicalConditionRepository = medicalConditionRepository;
        this.medicalConditionMapper = medicalConditionMapper;
        this.medicalConditionSearchRepository = medicalConditionSearchRepository;
    }

    @Override
    public MedicalConditionDTO save(MedicalConditionDTO medicalConditionDTO) {
        log.debug("Request to save MedicalCondition : {}", medicalConditionDTO);
        MedicalCondition medicalCondition = medicalConditionMapper.toEntity(medicalConditionDTO);
        medicalCondition = medicalConditionRepository.save(medicalCondition);
        MedicalConditionDTO result = medicalConditionMapper.toDto(medicalCondition);
        medicalConditionSearchRepository.save(medicalCondition);
        return result;
    }

    @Override
    @Transactional(readOnly = true)
    public Page<MedicalConditionDTO> findAll(Pageable pageable) {
        log.debug("Request to get all MedicalConditions");
        return medicalConditionRepository.findAll(pageable).map(medicalConditionMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<MedicalConditionDTO> findOne(Long id) {
        log.debug("Request to get MedicalCondition : {}", id);
        return medicalConditionRepository.findById(id).map(medicalConditionMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete MedicalCondition : {}", id);
        medicalConditionRepository.deleteById(id);
        medicalConditionSearchRepository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<MedicalConditionDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of MedicalConditions for query {}", query);
        return medicalConditionSearchRepository.search(queryStringQuery(query), pageable).map(medicalConditionMapper::toDto);
    }
}
