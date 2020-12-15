package com.mindforme.app.service.impl;

import static org.elasticsearch.index.query.QueryBuilders.*;

import com.mindforme.app.domain.State;
import com.mindforme.app.repository.StateRepository;
import com.mindforme.app.repository.search.StateSearchRepository;
import com.mindforme.app.service.StateService;
import com.mindforme.app.service.dto.StateDTO;
import com.mindforme.app.service.mapper.StateMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link State}.
 */
@Service
@Transactional
public class StateServiceImpl implements StateService {
    private final Logger log = LoggerFactory.getLogger(StateServiceImpl.class);

    private final StateRepository stateRepository;

    private final StateMapper stateMapper;

    private final StateSearchRepository stateSearchRepository;

    public StateServiceImpl(StateRepository stateRepository, StateMapper stateMapper, StateSearchRepository stateSearchRepository) {
        this.stateRepository = stateRepository;
        this.stateMapper = stateMapper;
        this.stateSearchRepository = stateSearchRepository;
    }

    @Override
    public StateDTO save(StateDTO stateDTO) {
        log.debug("Request to save State : {}", stateDTO);
        State state = stateMapper.toEntity(stateDTO);
        state = stateRepository.save(state);
        StateDTO result = stateMapper.toDto(state);
        stateSearchRepository.save(state);
        return result;
    }

    @Override
    @Transactional(readOnly = true)
    public Page<StateDTO> findAll(Pageable pageable) {
        log.debug("Request to get all States");
        return stateRepository.findAll(pageable).map(stateMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<StateDTO> findOne(Long id) {
        log.debug("Request to get State : {}", id);
        return stateRepository.findById(id).map(stateMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete State : {}", id);
        stateRepository.deleteById(id);
        stateSearchRepository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<StateDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of States for query {}", query);
        return stateSearchRepository.search(queryStringQuery(query), pageable).map(stateMapper::toDto);
    }
}
