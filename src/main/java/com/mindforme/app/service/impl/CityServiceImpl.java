package com.mindforme.app.service.impl;

import static org.elasticsearch.index.query.QueryBuilders.*;

import com.mindforme.app.domain.City;
import com.mindforme.app.repository.CityRepository;
import com.mindforme.app.repository.search.CitySearchRepository;
import com.mindforme.app.service.CityService;
import com.mindforme.app.service.dto.CityDTO;
import com.mindforme.app.service.mapper.CityMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link City}.
 */
@Service
@Transactional
public class CityServiceImpl implements CityService {
    private final Logger log = LoggerFactory.getLogger(CityServiceImpl.class);

    private final CityRepository cityRepository;

    private final CityMapper cityMapper;

    private final CitySearchRepository citySearchRepository;

    public CityServiceImpl(CityRepository cityRepository, CityMapper cityMapper, CitySearchRepository citySearchRepository) {
        this.cityRepository = cityRepository;
        this.cityMapper = cityMapper;
        this.citySearchRepository = citySearchRepository;
    }

    @Override
    public CityDTO save(CityDTO cityDTO) {
        log.debug("Request to save City : {}", cityDTO);
        City city = cityMapper.toEntity(cityDTO);
        city = cityRepository.save(city);
        CityDTO result = cityMapper.toDto(city);
        citySearchRepository.save(city);
        return result;
    }

    @Override
    @Transactional(readOnly = true)
    public Page<CityDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Cities");
        return cityRepository.findAll(pageable).map(cityMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<CityDTO> findOne(Long id) {
        log.debug("Request to get City : {}", id);
        return cityRepository.findById(id).map(cityMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete City : {}", id);
        cityRepository.deleteById(id);
        citySearchRepository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<CityDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of Cities for query {}", query);
        return citySearchRepository.search(queryStringQuery(query), pageable).map(cityMapper::toDto);
    }
}
