package com.mindforme.app.service.impl;

import static org.elasticsearch.index.query.QueryBuilders.*;

import com.mindforme.app.domain.CountryData;
import com.mindforme.app.repository.CountryDataRepository;
import com.mindforme.app.repository.search.CountryDataSearchRepository;
import com.mindforme.app.service.CountryDataService;
import com.mindforme.app.service.dto.CountryDataDTO;
import com.mindforme.app.service.mapper.CountryDataMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link CountryData}.
 */
@Service
@Transactional
public class CountryDataServiceImpl implements CountryDataService {
    private final Logger log = LoggerFactory.getLogger(CountryDataServiceImpl.class);

    private final CountryDataRepository countryDataRepository;

    private final CountryDataMapper countryDataMapper;

    private final CountryDataSearchRepository countryDataSearchRepository;

    public CountryDataServiceImpl(
        CountryDataRepository countryDataRepository,
        CountryDataMapper countryDataMapper,
        CountryDataSearchRepository countryDataSearchRepository
    ) {
        this.countryDataRepository = countryDataRepository;
        this.countryDataMapper = countryDataMapper;
        this.countryDataSearchRepository = countryDataSearchRepository;
    }

    @Override
    public CountryDataDTO save(CountryDataDTO countryDataDTO) {
        log.debug("Request to save CountryData : {}", countryDataDTO);
        CountryData countryData = countryDataMapper.toEntity(countryDataDTO);
        countryData = countryDataRepository.save(countryData);
        CountryDataDTO result = countryDataMapper.toDto(countryData);
        countryDataSearchRepository.save(countryData);
        return result;
    }

    @Override
    @Transactional(readOnly = true)
    public Page<CountryDataDTO> findAll(Pageable pageable) {
        log.debug("Request to get all CountryData");
        return countryDataRepository.findAll(pageable).map(countryDataMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<CountryDataDTO> findOne(Long id) {
        log.debug("Request to get CountryData : {}", id);
        return countryDataRepository.findById(id).map(countryDataMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete CountryData : {}", id);
        countryDataRepository.deleteById(id);
        countryDataSearchRepository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<CountryDataDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of CountryData for query {}", query);
        return countryDataSearchRepository.search(queryStringQuery(query), pageable).map(countryDataMapper::toDto);
    }
}
