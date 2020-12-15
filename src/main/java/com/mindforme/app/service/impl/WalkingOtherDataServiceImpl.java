package com.mindforme.app.service.impl;

import static org.elasticsearch.index.query.QueryBuilders.*;

import com.mindforme.app.domain.WalkingOtherData;
import com.mindforme.app.repository.WalkingOtherDataRepository;
import com.mindforme.app.repository.search.WalkingOtherDataSearchRepository;
import com.mindforme.app.service.WalkingOtherDataService;
import com.mindforme.app.service.dto.WalkingOtherDataDTO;
import com.mindforme.app.service.mapper.WalkingOtherDataMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link WalkingOtherData}.
 */
@Service
@Transactional
public class WalkingOtherDataServiceImpl implements WalkingOtherDataService {
    private final Logger log = LoggerFactory.getLogger(WalkingOtherDataServiceImpl.class);

    private final WalkingOtherDataRepository walkingOtherDataRepository;

    private final WalkingOtherDataMapper walkingOtherDataMapper;

    private final WalkingOtherDataSearchRepository walkingOtherDataSearchRepository;

    public WalkingOtherDataServiceImpl(
        WalkingOtherDataRepository walkingOtherDataRepository,
        WalkingOtherDataMapper walkingOtherDataMapper,
        WalkingOtherDataSearchRepository walkingOtherDataSearchRepository
    ) {
        this.walkingOtherDataRepository = walkingOtherDataRepository;
        this.walkingOtherDataMapper = walkingOtherDataMapper;
        this.walkingOtherDataSearchRepository = walkingOtherDataSearchRepository;
    }

    @Override
    public WalkingOtherDataDTO save(WalkingOtherDataDTO walkingOtherDataDTO) {
        log.debug("Request to save WalkingOtherData : {}", walkingOtherDataDTO);
        WalkingOtherData walkingOtherData = walkingOtherDataMapper.toEntity(walkingOtherDataDTO);
        walkingOtherData = walkingOtherDataRepository.save(walkingOtherData);
        WalkingOtherDataDTO result = walkingOtherDataMapper.toDto(walkingOtherData);
        walkingOtherDataSearchRepository.save(walkingOtherData);
        return result;
    }

    @Override
    @Transactional(readOnly = true)
    public Page<WalkingOtherDataDTO> findAll(Pageable pageable) {
        log.debug("Request to get all WalkingOtherData");
        return walkingOtherDataRepository.findAll(pageable).map(walkingOtherDataMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<WalkingOtherDataDTO> findOne(Long id) {
        log.debug("Request to get WalkingOtherData : {}", id);
        return walkingOtherDataRepository.findById(id).map(walkingOtherDataMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete WalkingOtherData : {}", id);
        walkingOtherDataRepository.deleteById(id);
        walkingOtherDataSearchRepository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<WalkingOtherDataDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of WalkingOtherData for query {}", query);
        return walkingOtherDataSearchRepository.search(queryStringQuery(query), pageable).map(walkingOtherDataMapper::toDto);
    }
}
