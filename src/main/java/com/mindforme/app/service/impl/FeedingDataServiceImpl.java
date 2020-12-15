package com.mindforme.app.service.impl;

import static org.elasticsearch.index.query.QueryBuilders.*;

import com.mindforme.app.domain.FeedingData;
import com.mindforme.app.repository.FeedingDataRepository;
import com.mindforme.app.repository.search.FeedingDataSearchRepository;
import com.mindforme.app.service.FeedingDataService;
import com.mindforme.app.service.dto.FeedingDataDTO;
import com.mindforme.app.service.mapper.FeedingDataMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link FeedingData}.
 */
@Service
@Transactional
public class FeedingDataServiceImpl implements FeedingDataService {
    private final Logger log = LoggerFactory.getLogger(FeedingDataServiceImpl.class);

    private final FeedingDataRepository feedingDataRepository;

    private final FeedingDataMapper feedingDataMapper;

    private final FeedingDataSearchRepository feedingDataSearchRepository;

    public FeedingDataServiceImpl(
        FeedingDataRepository feedingDataRepository,
        FeedingDataMapper feedingDataMapper,
        FeedingDataSearchRepository feedingDataSearchRepository
    ) {
        this.feedingDataRepository = feedingDataRepository;
        this.feedingDataMapper = feedingDataMapper;
        this.feedingDataSearchRepository = feedingDataSearchRepository;
    }

    @Override
    public FeedingDataDTO save(FeedingDataDTO feedingDataDTO) {
        log.debug("Request to save FeedingData : {}", feedingDataDTO);
        FeedingData feedingData = feedingDataMapper.toEntity(feedingDataDTO);
        feedingData = feedingDataRepository.save(feedingData);
        FeedingDataDTO result = feedingDataMapper.toDto(feedingData);
        feedingDataSearchRepository.save(feedingData);
        return result;
    }

    @Override
    @Transactional(readOnly = true)
    public Page<FeedingDataDTO> findAll(Pageable pageable) {
        log.debug("Request to get all FeedingData");
        return feedingDataRepository.findAll(pageable).map(feedingDataMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<FeedingDataDTO> findOne(Long id) {
        log.debug("Request to get FeedingData : {}", id);
        return feedingDataRepository.findById(id).map(feedingDataMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete FeedingData : {}", id);
        feedingDataRepository.deleteById(id);
        feedingDataSearchRepository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<FeedingDataDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of FeedingData for query {}", query);
        return feedingDataSearchRepository.search(queryStringQuery(query), pageable).map(feedingDataMapper::toDto);
    }
}
