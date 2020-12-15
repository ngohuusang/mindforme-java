package com.mindforme.app.service.impl;

import static org.elasticsearch.index.query.QueryBuilders.*;

import com.mindforme.app.domain.PetBreedData;
import com.mindforme.app.repository.PetBreedDataRepository;
import com.mindforme.app.repository.search.PetBreedDataSearchRepository;
import com.mindforme.app.service.PetBreedDataService;
import com.mindforme.app.service.dto.PetBreedDataDTO;
import com.mindforme.app.service.mapper.PetBreedDataMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link PetBreedData}.
 */
@Service
@Transactional
public class PetBreedDataServiceImpl implements PetBreedDataService {
    private final Logger log = LoggerFactory.getLogger(PetBreedDataServiceImpl.class);

    private final PetBreedDataRepository petBreedDataRepository;

    private final PetBreedDataMapper petBreedDataMapper;

    private final PetBreedDataSearchRepository petBreedDataSearchRepository;

    public PetBreedDataServiceImpl(
        PetBreedDataRepository petBreedDataRepository,
        PetBreedDataMapper petBreedDataMapper,
        PetBreedDataSearchRepository petBreedDataSearchRepository
    ) {
        this.petBreedDataRepository = petBreedDataRepository;
        this.petBreedDataMapper = petBreedDataMapper;
        this.petBreedDataSearchRepository = petBreedDataSearchRepository;
    }

    @Override
    public PetBreedDataDTO save(PetBreedDataDTO petBreedDataDTO) {
        log.debug("Request to save PetBreedData : {}", petBreedDataDTO);
        PetBreedData petBreedData = petBreedDataMapper.toEntity(petBreedDataDTO);
        petBreedData = petBreedDataRepository.save(petBreedData);
        PetBreedDataDTO result = petBreedDataMapper.toDto(petBreedData);
        petBreedDataSearchRepository.save(petBreedData);
        return result;
    }

    @Override
    @Transactional(readOnly = true)
    public Page<PetBreedDataDTO> findAll(Pageable pageable) {
        log.debug("Request to get all PetBreedData");
        return petBreedDataRepository.findAll(pageable).map(petBreedDataMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<PetBreedDataDTO> findOne(Long id) {
        log.debug("Request to get PetBreedData : {}", id);
        return petBreedDataRepository.findById(id).map(petBreedDataMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete PetBreedData : {}", id);
        petBreedDataRepository.deleteById(id);
        petBreedDataSearchRepository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<PetBreedDataDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of PetBreedData for query {}", query);
        return petBreedDataSearchRepository.search(queryStringQuery(query), pageable).map(petBreedDataMapper::toDto);
    }
}
