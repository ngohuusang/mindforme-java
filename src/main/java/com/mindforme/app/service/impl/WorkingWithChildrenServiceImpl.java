package com.mindforme.app.service.impl;

import static org.elasticsearch.index.query.QueryBuilders.*;

import com.mindforme.app.domain.WorkingWithChildren;
import com.mindforme.app.repository.WorkingWithChildrenRepository;
import com.mindforme.app.repository.search.WorkingWithChildrenSearchRepository;
import com.mindforme.app.service.WorkingWithChildrenService;
import com.mindforme.app.service.dto.WorkingWithChildrenDTO;
import com.mindforme.app.service.mapper.WorkingWithChildrenMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link WorkingWithChildren}.
 */
@Service
@Transactional
public class WorkingWithChildrenServiceImpl implements WorkingWithChildrenService {
    private final Logger log = LoggerFactory.getLogger(WorkingWithChildrenServiceImpl.class);

    private final WorkingWithChildrenRepository workingWithChildrenRepository;

    private final WorkingWithChildrenMapper workingWithChildrenMapper;

    private final WorkingWithChildrenSearchRepository workingWithChildrenSearchRepository;

    public WorkingWithChildrenServiceImpl(
        WorkingWithChildrenRepository workingWithChildrenRepository,
        WorkingWithChildrenMapper workingWithChildrenMapper,
        WorkingWithChildrenSearchRepository workingWithChildrenSearchRepository
    ) {
        this.workingWithChildrenRepository = workingWithChildrenRepository;
        this.workingWithChildrenMapper = workingWithChildrenMapper;
        this.workingWithChildrenSearchRepository = workingWithChildrenSearchRepository;
    }

    @Override
    public WorkingWithChildrenDTO save(WorkingWithChildrenDTO workingWithChildrenDTO) {
        log.debug("Request to save WorkingWithChildren : {}", workingWithChildrenDTO);
        WorkingWithChildren workingWithChildren = workingWithChildrenMapper.toEntity(workingWithChildrenDTO);
        workingWithChildren = workingWithChildrenRepository.save(workingWithChildren);
        WorkingWithChildrenDTO result = workingWithChildrenMapper.toDto(workingWithChildren);
        workingWithChildrenSearchRepository.save(workingWithChildren);
        return result;
    }

    @Override
    @Transactional(readOnly = true)
    public Page<WorkingWithChildrenDTO> findAll(Pageable pageable) {
        log.debug("Request to get all WorkingWithChildren");
        return workingWithChildrenRepository.findAll(pageable).map(workingWithChildrenMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<WorkingWithChildrenDTO> findOne(Long id) {
        log.debug("Request to get WorkingWithChildren : {}", id);
        return workingWithChildrenRepository.findById(id).map(workingWithChildrenMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete WorkingWithChildren : {}", id);
        workingWithChildrenRepository.deleteById(id);
        workingWithChildrenSearchRepository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<WorkingWithChildrenDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of WorkingWithChildren for query {}", query);
        return workingWithChildrenSearchRepository.search(queryStringQuery(query), pageable).map(workingWithChildrenMapper::toDto);
    }
}
