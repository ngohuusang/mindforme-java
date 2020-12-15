package com.mindforme.app.service.impl;

import static org.elasticsearch.index.query.QueryBuilders.*;

import com.mindforme.app.domain.Family;
import com.mindforme.app.repository.FamilyRepository;
import com.mindforme.app.repository.search.FamilySearchRepository;
import com.mindforme.app.service.FamilyService;
import com.mindforme.app.service.dto.FamilyDTO;
import com.mindforme.app.service.mapper.FamilyMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Family}.
 */
@Service
@Transactional
public class FamilyServiceImpl implements FamilyService {
    private final Logger log = LoggerFactory.getLogger(FamilyServiceImpl.class);

    private final FamilyRepository familyRepository;

    private final FamilyMapper familyMapper;

    private final FamilySearchRepository familySearchRepository;

    public FamilyServiceImpl(FamilyRepository familyRepository, FamilyMapper familyMapper, FamilySearchRepository familySearchRepository) {
        this.familyRepository = familyRepository;
        this.familyMapper = familyMapper;
        this.familySearchRepository = familySearchRepository;
    }

    @Override
    public FamilyDTO save(FamilyDTO familyDTO) {
        log.debug("Request to save Family : {}", familyDTO);
        Family family = familyMapper.toEntity(familyDTO);
        family = familyRepository.save(family);
        FamilyDTO result = familyMapper.toDto(family);
        familySearchRepository.save(family);
        return result;
    }

    @Override
    @Transactional(readOnly = true)
    public Page<FamilyDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Families");
        return familyRepository.findAll(pageable).map(familyMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<FamilyDTO> findOne(Long id) {
        log.debug("Request to get Family : {}", id);
        return familyRepository.findById(id).map(familyMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Family : {}", id);
        familyRepository.deleteById(id);
        familySearchRepository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<FamilyDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of Families for query {}", query);
        return familySearchRepository.search(queryStringQuery(query), pageable).map(familyMapper::toDto);
    }
}
