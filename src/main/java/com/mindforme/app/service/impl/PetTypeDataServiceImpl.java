package com.mindforme.app.service.impl;

import static org.elasticsearch.index.query.QueryBuilders.*;

import com.mindforme.app.domain.PetTypeData;
import com.mindforme.app.repository.PetTypeDataRepository;
import com.mindforme.app.repository.search.PetTypeDataSearchRepository;
import com.mindforme.app.service.PetTypeDataService;
import com.mindforme.app.service.dto.PetTypeDataDTO;
import com.mindforme.app.service.mapper.PetTypeDataMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link PetTypeData}.
 */
@Service
@Transactional
public class PetTypeDataServiceImpl implements PetTypeDataService {
    private final Logger log = LoggerFactory.getLogger(PetTypeDataServiceImpl.class);

    private final PetTypeDataRepository petTypeDataRepository;

    private final PetTypeDataMapper petTypeDataMapper;

    private final PetTypeDataSearchRepository petTypeDataSearchRepository;

    public PetTypeDataServiceImpl(
        PetTypeDataRepository petTypeDataRepository,
        PetTypeDataMapper petTypeDataMapper,
        PetTypeDataSearchRepository petTypeDataSearchRepository
    ) {
        this.petTypeDataRepository = petTypeDataRepository;
        this.petTypeDataMapper = petTypeDataMapper;
        this.petTypeDataSearchRepository = petTypeDataSearchRepository;
    }

    @Override
    public PetTypeDataDTO save(PetTypeDataDTO petTypeDataDTO) {
        log.debug("Request to save PetTypeData : {}", petTypeDataDTO);
        PetTypeData petTypeData = petTypeDataMapper.toEntity(petTypeDataDTO);
        petTypeData = petTypeDataRepository.save(petTypeData);
        PetTypeDataDTO result = petTypeDataMapper.toDto(petTypeData);
        petTypeDataSearchRepository.save(petTypeData);
        return result;
    }

    @Override
    @Transactional(readOnly = true)
    public Page<PetTypeDataDTO> findAll(Pageable pageable) {
        log.debug("Request to get all PetTypeData");
        return petTypeDataRepository.findAll(pageable).map(petTypeDataMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<PetTypeDataDTO> findOne(Long id) {
        log.debug("Request to get PetTypeData : {}", id);
        return petTypeDataRepository.findById(id).map(petTypeDataMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete PetTypeData : {}", id);
        petTypeDataRepository.deleteById(id);
        petTypeDataSearchRepository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<PetTypeDataDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of PetTypeData for query {}", query);
        return petTypeDataSearchRepository.search(queryStringQuery(query), pageable).map(petTypeDataMapper::toDto);
    }
}
