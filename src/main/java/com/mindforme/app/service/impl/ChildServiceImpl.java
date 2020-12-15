package com.mindforme.app.service.impl;

import static org.elasticsearch.index.query.QueryBuilders.*;

import com.mindforme.app.domain.Child;
import com.mindforme.app.repository.ChildRepository;
import com.mindforme.app.repository.search.ChildSearchRepository;
import com.mindforme.app.service.ChildService;
import com.mindforme.app.service.dto.ChildDTO;
import com.mindforme.app.service.mapper.ChildMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Child}.
 */
@Service
@Transactional
public class ChildServiceImpl implements ChildService {
    private final Logger log = LoggerFactory.getLogger(ChildServiceImpl.class);

    private final ChildRepository childRepository;

    private final ChildMapper childMapper;

    private final ChildSearchRepository childSearchRepository;

    public ChildServiceImpl(ChildRepository childRepository, ChildMapper childMapper, ChildSearchRepository childSearchRepository) {
        this.childRepository = childRepository;
        this.childMapper = childMapper;
        this.childSearchRepository = childSearchRepository;
    }

    @Override
    public ChildDTO save(ChildDTO childDTO) {
        log.debug("Request to save Child : {}", childDTO);
        Child child = childMapper.toEntity(childDTO);
        child = childRepository.save(child);
        ChildDTO result = childMapper.toDto(child);
        childSearchRepository.save(child);
        return result;
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ChildDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Children");
        return childRepository.findAll(pageable).map(childMapper::toDto);
    }

    public Page<ChildDTO> findAllWithEagerRelationships(Pageable pageable) {
        return childRepository.findAllWithEagerRelationships(pageable).map(childMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<ChildDTO> findOne(Long id) {
        log.debug("Request to get Child : {}", id);
        return childRepository.findOneWithEagerRelationships(id).map(childMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Child : {}", id);
        childRepository.deleteById(id);
        childSearchRepository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ChildDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of Children for query {}", query);
        return childSearchRepository.search(queryStringQuery(query), pageable).map(childMapper::toDto);
    }
}
