package com.mindforme.app.service.impl;

import static org.elasticsearch.index.query.QueryBuilders.*;

import com.mindforme.app.domain.EmergencyContact;
import com.mindforme.app.repository.EmergencyContactRepository;
import com.mindforme.app.repository.search.EmergencyContactSearchRepository;
import com.mindforme.app.service.EmergencyContactService;
import com.mindforme.app.service.dto.EmergencyContactDTO;
import com.mindforme.app.service.mapper.EmergencyContactMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link EmergencyContact}.
 */
@Service
@Transactional
public class EmergencyContactServiceImpl implements EmergencyContactService {
    private final Logger log = LoggerFactory.getLogger(EmergencyContactServiceImpl.class);

    private final EmergencyContactRepository emergencyContactRepository;

    private final EmergencyContactMapper emergencyContactMapper;

    private final EmergencyContactSearchRepository emergencyContactSearchRepository;

    public EmergencyContactServiceImpl(
        EmergencyContactRepository emergencyContactRepository,
        EmergencyContactMapper emergencyContactMapper,
        EmergencyContactSearchRepository emergencyContactSearchRepository
    ) {
        this.emergencyContactRepository = emergencyContactRepository;
        this.emergencyContactMapper = emergencyContactMapper;
        this.emergencyContactSearchRepository = emergencyContactSearchRepository;
    }

    @Override
    public EmergencyContactDTO save(EmergencyContactDTO emergencyContactDTO) {
        log.debug("Request to save EmergencyContact : {}", emergencyContactDTO);
        EmergencyContact emergencyContact = emergencyContactMapper.toEntity(emergencyContactDTO);
        emergencyContact = emergencyContactRepository.save(emergencyContact);
        EmergencyContactDTO result = emergencyContactMapper.toDto(emergencyContact);
        emergencyContactSearchRepository.save(emergencyContact);
        return result;
    }

    @Override
    @Transactional(readOnly = true)
    public Page<EmergencyContactDTO> findAll(Pageable pageable) {
        log.debug("Request to get all EmergencyContacts");
        return emergencyContactRepository.findAll(pageable).map(emergencyContactMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<EmergencyContactDTO> findOne(Long id) {
        log.debug("Request to get EmergencyContact : {}", id);
        return emergencyContactRepository.findById(id).map(emergencyContactMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete EmergencyContact : {}", id);
        emergencyContactRepository.deleteById(id);
        emergencyContactSearchRepository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<EmergencyContactDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of EmergencyContacts for query {}", query);
        return emergencyContactSearchRepository.search(queryStringQuery(query), pageable).map(emergencyContactMapper::toDto);
    }
}
