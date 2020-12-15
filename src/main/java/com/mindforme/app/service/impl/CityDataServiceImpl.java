package com.mindforme.app.service.impl;

import static org.elasticsearch.index.query.QueryBuilders.*;

import com.mindforme.app.domain.CityData;
import com.mindforme.app.repository.CityDataRepository;
import com.mindforme.app.repository.search.CityDataSearchRepository;
import com.mindforme.app.service.CityDataService;
import com.mindforme.app.service.dto.CityDataDTO;
import com.mindforme.app.service.mapper.CityDataMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link CityData}.
 */
@Service
@Transactional
public class CityDataServiceImpl implements CityDataService {
    private final Logger log = LoggerFactory.getLogger(CityDataServiceImpl.class);

    private final CityDataRepository cityDataRepository;

    private final CityDataMapper cityDataMapper;

    private final CityDataSearchRepository cityDataSearchRepository;

    public CityDataServiceImpl(
        CityDataRepository cityDataRepository,
        CityDataMapper cityDataMapper,
        CityDataSearchRepository cityDataSearchRepository
    ) {
        this.cityDataRepository = cityDataRepository;
        this.cityDataMapper = cityDataMapper;
        this.cityDataSearchRepository = cityDataSearchRepository;
    }

    @Override
    public CityDataDTO save(CityDataDTO cityDataDTO) {
        log.debug("Request to save CityData : {}", cityDataDTO);
        CityData cityData = cityDataMapper.toEntity(cityDataDTO);
        cityData = cityDataRepository.save(cityData);
        CityDataDTO result = cityDataMapper.toDto(cityData);
        cityDataSearchRepository.save(cityData);
        return result;
    }

    @Override
    @Transactional(readOnly = true)
    public Page<CityDataDTO> findAll(Pageable pageable) {
        log.debug("Request to get all CityData");
        return cityDataRepository.findAll(pageable).map(cityDataMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<CityDataDTO> findOne(Long id) {
        log.debug("Request to get CityData : {}", id);
        return cityDataRepository.findById(id).map(cityDataMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete CityData : {}", id);
        cityDataRepository.deleteById(id);
        cityDataSearchRepository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<CityDataDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of CityData for query {}", query);
        return cityDataSearchRepository.search(queryStringQuery(query), pageable).map(cityDataMapper::toDto);
    }
}
