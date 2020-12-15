package com.mindforme.app.service.impl;

import static org.elasticsearch.index.query.QueryBuilders.*;

import com.mindforme.app.domain.PetType;
import com.mindforme.app.repository.PetTypeRepository;
import com.mindforme.app.repository.search.PetTypeSearchRepository;
import com.mindforme.app.service.PetTypeService;
import com.mindforme.app.service.dto.PetTypeDTO;
import com.mindforme.app.service.mapper.PetTypeMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link PetType}.
 */
@Service
@Transactional
public class PetTypeServiceImpl implements PetTypeService {
    private final Logger log = LoggerFactory.getLogger(PetTypeServiceImpl.class);

    private final PetTypeRepository petTypeRepository;

    private final PetTypeMapper petTypeMapper;

    private final PetTypeSearchRepository petTypeSearchRepository;

    public PetTypeServiceImpl(
        PetTypeRepository petTypeRepository,
        PetTypeMapper petTypeMapper,
        PetTypeSearchRepository petTypeSearchRepository
    ) {
        this.petTypeRepository = petTypeRepository;
        this.petTypeMapper = petTypeMapper;
        this.petTypeSearchRepository = petTypeSearchRepository;
    }

    @Override
    public PetTypeDTO save(PetTypeDTO petTypeDTO) {
        log.debug("Request to save PetType : {}", petTypeDTO);
        PetType petType = petTypeMapper.toEntity(petTypeDTO);
        petType = petTypeRepository.save(petType);
        PetTypeDTO result = petTypeMapper.toDto(petType);
        petTypeSearchRepository.save(petType);
        return result;
    }

    @Override
    @Transactional(readOnly = true)
    public Page<PetTypeDTO> findAll(Pageable pageable) {
        log.debug("Request to get all PetTypes");
        return petTypeRepository.findAll(pageable).map(petTypeMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<PetTypeDTO> findOne(Long id) {
        log.debug("Request to get PetType : {}", id);
        return petTypeRepository.findById(id).map(petTypeMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete PetType : {}", id);
        petTypeRepository.deleteById(id);
        petTypeSearchRepository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<PetTypeDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of PetTypes for query {}", query);
        return petTypeSearchRepository.search(queryStringQuery(query), pageable).map(petTypeMapper::toDto);
    }
}
