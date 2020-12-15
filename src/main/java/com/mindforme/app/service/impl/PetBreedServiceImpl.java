package com.mindforme.app.service.impl;

import static org.elasticsearch.index.query.QueryBuilders.*;

import com.mindforme.app.domain.PetBreed;
import com.mindforme.app.repository.PetBreedRepository;
import com.mindforme.app.repository.search.PetBreedSearchRepository;
import com.mindforme.app.service.PetBreedService;
import com.mindforme.app.service.dto.PetBreedDTO;
import com.mindforme.app.service.mapper.PetBreedMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link PetBreed}.
 */
@Service
@Transactional
public class PetBreedServiceImpl implements PetBreedService {
    private final Logger log = LoggerFactory.getLogger(PetBreedServiceImpl.class);

    private final PetBreedRepository petBreedRepository;

    private final PetBreedMapper petBreedMapper;

    private final PetBreedSearchRepository petBreedSearchRepository;

    public PetBreedServiceImpl(
        PetBreedRepository petBreedRepository,
        PetBreedMapper petBreedMapper,
        PetBreedSearchRepository petBreedSearchRepository
    ) {
        this.petBreedRepository = petBreedRepository;
        this.petBreedMapper = petBreedMapper;
        this.petBreedSearchRepository = petBreedSearchRepository;
    }

    @Override
    public PetBreedDTO save(PetBreedDTO petBreedDTO) {
        log.debug("Request to save PetBreed : {}", petBreedDTO);
        PetBreed petBreed = petBreedMapper.toEntity(petBreedDTO);
        petBreed = petBreedRepository.save(petBreed);
        PetBreedDTO result = petBreedMapper.toDto(petBreed);
        petBreedSearchRepository.save(petBreed);
        return result;
    }

    @Override
    @Transactional(readOnly = true)
    public Page<PetBreedDTO> findAll(Pageable pageable) {
        log.debug("Request to get all PetBreeds");
        return petBreedRepository.findAll(pageable).map(petBreedMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<PetBreedDTO> findOne(Long id) {
        log.debug("Request to get PetBreed : {}", id);
        return petBreedRepository.findById(id).map(petBreedMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete PetBreed : {}", id);
        petBreedRepository.deleteById(id);
        petBreedSearchRepository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<PetBreedDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of PetBreeds for query {}", query);
        return petBreedSearchRepository.search(queryStringQuery(query), pageable).map(petBreedMapper::toDto);
    }
}
