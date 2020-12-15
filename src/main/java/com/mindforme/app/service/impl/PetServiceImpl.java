package com.mindforme.app.service.impl;

import static org.elasticsearch.index.query.QueryBuilders.*;

import com.mindforme.app.domain.Pet;
import com.mindforme.app.repository.PetRepository;
import com.mindforme.app.repository.search.PetSearchRepository;
import com.mindforme.app.service.PetService;
import com.mindforme.app.service.dto.PetDTO;
import com.mindforme.app.service.mapper.PetMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Pet}.
 */
@Service
@Transactional
public class PetServiceImpl implements PetService {
    private final Logger log = LoggerFactory.getLogger(PetServiceImpl.class);

    private final PetRepository petRepository;

    private final PetMapper petMapper;

    private final PetSearchRepository petSearchRepository;

    public PetServiceImpl(PetRepository petRepository, PetMapper petMapper, PetSearchRepository petSearchRepository) {
        this.petRepository = petRepository;
        this.petMapper = petMapper;
        this.petSearchRepository = petSearchRepository;
    }

    @Override
    public PetDTO save(PetDTO petDTO) {
        log.debug("Request to save Pet : {}", petDTO);
        Pet pet = petMapper.toEntity(petDTO);
        pet = petRepository.save(pet);
        PetDTO result = petMapper.toDto(pet);
        petSearchRepository.save(pet);
        return result;
    }

    @Override
    @Transactional(readOnly = true)
    public Page<PetDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Pets");
        return petRepository.findAll(pageable).map(petMapper::toDto);
    }

    public Page<PetDTO> findAllWithEagerRelationships(Pageable pageable) {
        return petRepository.findAllWithEagerRelationships(pageable).map(petMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<PetDTO> findOne(Long id) {
        log.debug("Request to get Pet : {}", id);
        return petRepository.findOneWithEagerRelationships(id).map(petMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Pet : {}", id);
        petRepository.deleteById(id);
        petSearchRepository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<PetDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of Pets for query {}", query);
        return petSearchRepository.search(queryStringQuery(query), pageable).map(petMapper::toDto);
    }
}
