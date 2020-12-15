package com.mindforme.app.service.impl;

import static org.elasticsearch.index.query.QueryBuilders.*;

import com.mindforme.app.domain.MindingNotification;
import com.mindforme.app.repository.MindingNotificationRepository;
import com.mindforme.app.repository.search.MindingNotificationSearchRepository;
import com.mindforme.app.service.MindingNotificationService;
import com.mindforme.app.service.dto.MindingNotificationDTO;
import com.mindforme.app.service.mapper.MindingNotificationMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link MindingNotification}.
 */
@Service
@Transactional
public class MindingNotificationServiceImpl implements MindingNotificationService {
    private final Logger log = LoggerFactory.getLogger(MindingNotificationServiceImpl.class);

    private final MindingNotificationRepository mindingNotificationRepository;

    private final MindingNotificationMapper mindingNotificationMapper;

    private final MindingNotificationSearchRepository mindingNotificationSearchRepository;

    public MindingNotificationServiceImpl(
        MindingNotificationRepository mindingNotificationRepository,
        MindingNotificationMapper mindingNotificationMapper,
        MindingNotificationSearchRepository mindingNotificationSearchRepository
    ) {
        this.mindingNotificationRepository = mindingNotificationRepository;
        this.mindingNotificationMapper = mindingNotificationMapper;
        this.mindingNotificationSearchRepository = mindingNotificationSearchRepository;
    }

    @Override
    public MindingNotificationDTO save(MindingNotificationDTO mindingNotificationDTO) {
        log.debug("Request to save MindingNotification : {}", mindingNotificationDTO);
        MindingNotification mindingNotification = mindingNotificationMapper.toEntity(mindingNotificationDTO);
        mindingNotification = mindingNotificationRepository.save(mindingNotification);
        MindingNotificationDTO result = mindingNotificationMapper.toDto(mindingNotification);
        mindingNotificationSearchRepository.save(mindingNotification);
        return result;
    }

    @Override
    @Transactional(readOnly = true)
    public Page<MindingNotificationDTO> findAll(Pageable pageable) {
        log.debug("Request to get all MindingNotifications");
        return mindingNotificationRepository.findAll(pageable).map(mindingNotificationMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<MindingNotificationDTO> findOne(Long id) {
        log.debug("Request to get MindingNotification : {}", id);
        return mindingNotificationRepository.findById(id).map(mindingNotificationMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete MindingNotification : {}", id);
        mindingNotificationRepository.deleteById(id);
        mindingNotificationSearchRepository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<MindingNotificationDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of MindingNotifications for query {}", query);
        return mindingNotificationSearchRepository.search(queryStringQuery(query), pageable).map(mindingNotificationMapper::toDto);
    }
}
