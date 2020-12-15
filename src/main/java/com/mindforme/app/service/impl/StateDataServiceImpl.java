package com.mindforme.app.service.impl;

import static org.elasticsearch.index.query.QueryBuilders.*;

import com.mindforme.app.domain.StateData;
import com.mindforme.app.repository.StateDataRepository;
import com.mindforme.app.repository.search.StateDataSearchRepository;
import com.mindforme.app.service.StateDataService;
import com.mindforme.app.service.dto.StateDataDTO;
import com.mindforme.app.service.mapper.StateDataMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link StateData}.
 */
@Service
@Transactional
public class StateDataServiceImpl implements StateDataService {
    private final Logger log = LoggerFactory.getLogger(StateDataServiceImpl.class);

    private final StateDataRepository stateDataRepository;

    private final StateDataMapper stateDataMapper;

    private final StateDataSearchRepository stateDataSearchRepository;

    public StateDataServiceImpl(
        StateDataRepository stateDataRepository,
        StateDataMapper stateDataMapper,
        StateDataSearchRepository stateDataSearchRepository
    ) {
        this.stateDataRepository = stateDataRepository;
        this.stateDataMapper = stateDataMapper;
        this.stateDataSearchRepository = stateDataSearchRepository;
    }

    @Override
    public StateDataDTO save(StateDataDTO stateDataDTO) {
        log.debug("Request to save StateData : {}", stateDataDTO);
        StateData stateData = stateDataMapper.toEntity(stateDataDTO);
        stateData = stateDataRepository.save(stateData);
        StateDataDTO result = stateDataMapper.toDto(stateData);
        stateDataSearchRepository.save(stateData);
        return result;
    }

    @Override
    @Transactional(readOnly = true)
    public Page<StateDataDTO> findAll(Pageable pageable) {
        log.debug("Request to get all StateData");
        return stateDataRepository.findAll(pageable).map(stateDataMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<StateDataDTO> findOne(Long id) {
        log.debug("Request to get StateData : {}", id);
        return stateDataRepository.findById(id).map(stateDataMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete StateData : {}", id);
        stateDataRepository.deleteById(id);
        stateDataSearchRepository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<StateDataDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of StateData for query {}", query);
        return stateDataSearchRepository.search(queryStringQuery(query), pageable).map(stateDataMapper::toDto);
    }
}
