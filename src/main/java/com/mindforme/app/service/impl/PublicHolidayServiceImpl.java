package com.mindforme.app.service.impl;

import static org.elasticsearch.index.query.QueryBuilders.*;

import com.mindforme.app.domain.PublicHoliday;
import com.mindforme.app.repository.PublicHolidayRepository;
import com.mindforme.app.repository.search.PublicHolidaySearchRepository;
import com.mindforme.app.service.PublicHolidayService;
import com.mindforme.app.service.dto.PublicHolidayDTO;
import com.mindforme.app.service.mapper.PublicHolidayMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link PublicHoliday}.
 */
@Service
@Transactional
public class PublicHolidayServiceImpl implements PublicHolidayService {
    private final Logger log = LoggerFactory.getLogger(PublicHolidayServiceImpl.class);

    private final PublicHolidayRepository publicHolidayRepository;

    private final PublicHolidayMapper publicHolidayMapper;

    private final PublicHolidaySearchRepository publicHolidaySearchRepository;

    public PublicHolidayServiceImpl(
        PublicHolidayRepository publicHolidayRepository,
        PublicHolidayMapper publicHolidayMapper,
        PublicHolidaySearchRepository publicHolidaySearchRepository
    ) {
        this.publicHolidayRepository = publicHolidayRepository;
        this.publicHolidayMapper = publicHolidayMapper;
        this.publicHolidaySearchRepository = publicHolidaySearchRepository;
    }

    @Override
    public PublicHolidayDTO save(PublicHolidayDTO publicHolidayDTO) {
        log.debug("Request to save PublicHoliday : {}", publicHolidayDTO);
        PublicHoliday publicHoliday = publicHolidayMapper.toEntity(publicHolidayDTO);
        publicHoliday = publicHolidayRepository.save(publicHoliday);
        PublicHolidayDTO result = publicHolidayMapper.toDto(publicHoliday);
        publicHolidaySearchRepository.save(publicHoliday);
        return result;
    }

    @Override
    @Transactional(readOnly = true)
    public Page<PublicHolidayDTO> findAll(Pageable pageable) {
        log.debug("Request to get all PublicHolidays");
        return publicHolidayRepository.findAll(pageable).map(publicHolidayMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<PublicHolidayDTO> findOne(Long id) {
        log.debug("Request to get PublicHoliday : {}", id);
        return publicHolidayRepository.findById(id).map(publicHolidayMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete PublicHoliday : {}", id);
        publicHolidayRepository.deleteById(id);
        publicHolidaySearchRepository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<PublicHolidayDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of PublicHolidays for query {}", query);
        return publicHolidaySearchRepository.search(queryStringQuery(query), pageable).map(publicHolidayMapper::toDto);
    }
}
