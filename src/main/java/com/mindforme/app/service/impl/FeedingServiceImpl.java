package com.mindforme.app.service.impl;

import static org.elasticsearch.index.query.QueryBuilders.*;

import com.mindforme.app.domain.Feeding;
import com.mindforme.app.repository.FeedingRepository;
import com.mindforme.app.repository.search.FeedingSearchRepository;
import com.mindforme.app.service.FeedingService;
import com.mindforme.app.service.dto.FeedingDTO;
import com.mindforme.app.service.mapper.FeedingMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Feeding}.
 */
@Service
@Transactional
public class FeedingServiceImpl implements FeedingService {
    private final Logger log = LoggerFactory.getLogger(FeedingServiceImpl.class);

    private final FeedingRepository feedingRepository;

    private final FeedingMapper feedingMapper;

    private final FeedingSearchRepository feedingSearchRepository;

    public FeedingServiceImpl(
        FeedingRepository feedingRepository,
        FeedingMapper feedingMapper,
        FeedingSearchRepository feedingSearchRepository
    ) {
        this.feedingRepository = feedingRepository;
        this.feedingMapper = feedingMapper;
        this.feedingSearchRepository = feedingSearchRepository;
    }

    @Override
    public FeedingDTO save(FeedingDTO feedingDTO) {
        log.debug("Request to save Feeding : {}", feedingDTO);
        Feeding feeding = feedingMapper.toEntity(feedingDTO);
        feeding = feedingRepository.save(feeding);
        FeedingDTO result = feedingMapper.toDto(feeding);
        feedingSearchRepository.save(feeding);
        return result;
    }

    @Override
    @Transactional(readOnly = true)
    public Page<FeedingDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Feedings");
        return feedingRepository.findAll(pageable).map(feedingMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<FeedingDTO> findOne(Long id) {
        log.debug("Request to get Feeding : {}", id);
        return feedingRepository.findById(id).map(feedingMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Feeding : {}", id);
        feedingRepository.deleteById(id);
        feedingSearchRepository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<FeedingDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of Feedings for query {}", query);
        return feedingSearchRepository.search(queryStringQuery(query), pageable).map(feedingMapper::toDto);
    }
}
