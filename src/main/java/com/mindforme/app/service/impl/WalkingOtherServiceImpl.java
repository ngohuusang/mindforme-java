package com.mindforme.app.service.impl;

import static org.elasticsearch.index.query.QueryBuilders.*;

import com.mindforme.app.domain.WalkingOther;
import com.mindforme.app.repository.WalkingOtherRepository;
import com.mindforme.app.repository.search.WalkingOtherSearchRepository;
import com.mindforme.app.service.WalkingOtherService;
import com.mindforme.app.service.dto.WalkingOtherDTO;
import com.mindforme.app.service.mapper.WalkingOtherMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link WalkingOther}.
 */
@Service
@Transactional
public class WalkingOtherServiceImpl implements WalkingOtherService {
    private final Logger log = LoggerFactory.getLogger(WalkingOtherServiceImpl.class);

    private final WalkingOtherRepository walkingOtherRepository;

    private final WalkingOtherMapper walkingOtherMapper;

    private final WalkingOtherSearchRepository walkingOtherSearchRepository;

    public WalkingOtherServiceImpl(
        WalkingOtherRepository walkingOtherRepository,
        WalkingOtherMapper walkingOtherMapper,
        WalkingOtherSearchRepository walkingOtherSearchRepository
    ) {
        this.walkingOtherRepository = walkingOtherRepository;
        this.walkingOtherMapper = walkingOtherMapper;
        this.walkingOtherSearchRepository = walkingOtherSearchRepository;
    }

    @Override
    public WalkingOtherDTO save(WalkingOtherDTO walkingOtherDTO) {
        log.debug("Request to save WalkingOther : {}", walkingOtherDTO);
        WalkingOther walkingOther = walkingOtherMapper.toEntity(walkingOtherDTO);
        walkingOther = walkingOtherRepository.save(walkingOther);
        WalkingOtherDTO result = walkingOtherMapper.toDto(walkingOther);
        walkingOtherSearchRepository.save(walkingOther);
        return result;
    }

    @Override
    @Transactional(readOnly = true)
    public Page<WalkingOtherDTO> findAll(Pageable pageable) {
        log.debug("Request to get all WalkingOthers");
        return walkingOtherRepository.findAll(pageable).map(walkingOtherMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<WalkingOtherDTO> findOne(Long id) {
        log.debug("Request to get WalkingOther : {}", id);
        return walkingOtherRepository.findById(id).map(walkingOtherMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete WalkingOther : {}", id);
        walkingOtherRepository.deleteById(id);
        walkingOtherSearchRepository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<WalkingOtherDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of WalkingOthers for query {}", query);
        return walkingOtherSearchRepository.search(queryStringQuery(query), pageable).map(walkingOtherMapper::toDto);
    }
}
