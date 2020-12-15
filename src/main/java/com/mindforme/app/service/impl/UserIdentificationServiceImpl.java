package com.mindforme.app.service.impl;

import static org.elasticsearch.index.query.QueryBuilders.*;

import com.mindforme.app.domain.UserIdentification;
import com.mindforme.app.repository.UserIdentificationRepository;
import com.mindforme.app.repository.search.UserIdentificationSearchRepository;
import com.mindforme.app.service.UserIdentificationService;
import com.mindforme.app.service.dto.UserIdentificationDTO;
import com.mindforme.app.service.mapper.UserIdentificationMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link UserIdentification}.
 */
@Service
@Transactional
public class UserIdentificationServiceImpl implements UserIdentificationService {
    private final Logger log = LoggerFactory.getLogger(UserIdentificationServiceImpl.class);

    private final UserIdentificationRepository userIdentificationRepository;

    private final UserIdentificationMapper userIdentificationMapper;

    private final UserIdentificationSearchRepository userIdentificationSearchRepository;

    public UserIdentificationServiceImpl(
        UserIdentificationRepository userIdentificationRepository,
        UserIdentificationMapper userIdentificationMapper,
        UserIdentificationSearchRepository userIdentificationSearchRepository
    ) {
        this.userIdentificationRepository = userIdentificationRepository;
        this.userIdentificationMapper = userIdentificationMapper;
        this.userIdentificationSearchRepository = userIdentificationSearchRepository;
    }

    @Override
    public UserIdentificationDTO save(UserIdentificationDTO userIdentificationDTO) {
        log.debug("Request to save UserIdentification : {}", userIdentificationDTO);
        UserIdentification userIdentification = userIdentificationMapper.toEntity(userIdentificationDTO);
        userIdentification = userIdentificationRepository.save(userIdentification);
        UserIdentificationDTO result = userIdentificationMapper.toDto(userIdentification);
        userIdentificationSearchRepository.save(userIdentification);
        return result;
    }

    @Override
    @Transactional(readOnly = true)
    public Page<UserIdentificationDTO> findAll(Pageable pageable) {
        log.debug("Request to get all UserIdentifications");
        return userIdentificationRepository.findAll(pageable).map(userIdentificationMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<UserIdentificationDTO> findOne(Long id) {
        log.debug("Request to get UserIdentification : {}", id);
        return userIdentificationRepository.findById(id).map(userIdentificationMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete UserIdentification : {}", id);
        userIdentificationRepository.deleteById(id);
        userIdentificationSearchRepository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<UserIdentificationDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of UserIdentifications for query {}", query);
        return userIdentificationSearchRepository.search(queryStringQuery(query), pageable).map(userIdentificationMapper::toDto);
    }
}
