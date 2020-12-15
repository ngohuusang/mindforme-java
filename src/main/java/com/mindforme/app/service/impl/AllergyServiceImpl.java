package com.mindforme.app.service.impl;

import static org.elasticsearch.index.query.QueryBuilders.*;

import com.mindforme.app.domain.Allergy;
import com.mindforme.app.repository.AllergyRepository;
import com.mindforme.app.repository.search.AllergySearchRepository;
import com.mindforme.app.service.AllergyService;
import com.mindforme.app.service.dto.AllergyDTO;
import com.mindforme.app.service.mapper.AllergyMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Allergy}.
 */
@Service
@Transactional
public class AllergyServiceImpl implements AllergyService {
    private final Logger log = LoggerFactory.getLogger(AllergyServiceImpl.class);

    private final AllergyRepository allergyRepository;

    private final AllergyMapper allergyMapper;

    private final AllergySearchRepository allergySearchRepository;

    public AllergyServiceImpl(
        AllergyRepository allergyRepository,
        AllergyMapper allergyMapper,
        AllergySearchRepository allergySearchRepository
    ) {
        this.allergyRepository = allergyRepository;
        this.allergyMapper = allergyMapper;
        this.allergySearchRepository = allergySearchRepository;
    }

    @Override
    public AllergyDTO save(AllergyDTO allergyDTO) {
        log.debug("Request to save Allergy : {}", allergyDTO);
        Allergy allergy = allergyMapper.toEntity(allergyDTO);
        allergy = allergyRepository.save(allergy);
        AllergyDTO result = allergyMapper.toDto(allergy);
        allergySearchRepository.save(allergy);
        return result;
    }

    @Override
    @Transactional(readOnly = true)
    public Page<AllergyDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Allergies");
        return allergyRepository.findAll(pageable).map(allergyMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<AllergyDTO> findOne(Long id) {
        log.debug("Request to get Allergy : {}", id);
        return allergyRepository.findById(id).map(allergyMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Allergy : {}", id);
        allergyRepository.deleteById(id);
        allergySearchRepository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<AllergyDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of Allergies for query {}", query);
        return allergySearchRepository.search(queryStringQuery(query), pageable).map(allergyMapper::toDto);
    }
}
