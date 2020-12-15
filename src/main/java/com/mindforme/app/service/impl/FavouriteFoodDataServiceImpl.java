package com.mindforme.app.service.impl;

import static org.elasticsearch.index.query.QueryBuilders.*;

import com.mindforme.app.domain.FavouriteFoodData;
import com.mindforme.app.repository.FavouriteFoodDataRepository;
import com.mindforme.app.repository.search.FavouriteFoodDataSearchRepository;
import com.mindforme.app.service.FavouriteFoodDataService;
import com.mindforme.app.service.dto.FavouriteFoodDataDTO;
import com.mindforme.app.service.mapper.FavouriteFoodDataMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link FavouriteFoodData}.
 */
@Service
@Transactional
public class FavouriteFoodDataServiceImpl implements FavouriteFoodDataService {
    private final Logger log = LoggerFactory.getLogger(FavouriteFoodDataServiceImpl.class);

    private final FavouriteFoodDataRepository favouriteFoodDataRepository;

    private final FavouriteFoodDataMapper favouriteFoodDataMapper;

    private final FavouriteFoodDataSearchRepository favouriteFoodDataSearchRepository;

    public FavouriteFoodDataServiceImpl(
        FavouriteFoodDataRepository favouriteFoodDataRepository,
        FavouriteFoodDataMapper favouriteFoodDataMapper,
        FavouriteFoodDataSearchRepository favouriteFoodDataSearchRepository
    ) {
        this.favouriteFoodDataRepository = favouriteFoodDataRepository;
        this.favouriteFoodDataMapper = favouriteFoodDataMapper;
        this.favouriteFoodDataSearchRepository = favouriteFoodDataSearchRepository;
    }

    @Override
    public FavouriteFoodDataDTO save(FavouriteFoodDataDTO favouriteFoodDataDTO) {
        log.debug("Request to save FavouriteFoodData : {}", favouriteFoodDataDTO);
        FavouriteFoodData favouriteFoodData = favouriteFoodDataMapper.toEntity(favouriteFoodDataDTO);
        favouriteFoodData = favouriteFoodDataRepository.save(favouriteFoodData);
        FavouriteFoodDataDTO result = favouriteFoodDataMapper.toDto(favouriteFoodData);
        favouriteFoodDataSearchRepository.save(favouriteFoodData);
        return result;
    }

    @Override
    @Transactional(readOnly = true)
    public Page<FavouriteFoodDataDTO> findAll(Pageable pageable) {
        log.debug("Request to get all FavouriteFoodData");
        return favouriteFoodDataRepository.findAll(pageable).map(favouriteFoodDataMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<FavouriteFoodDataDTO> findOne(Long id) {
        log.debug("Request to get FavouriteFoodData : {}", id);
        return favouriteFoodDataRepository.findById(id).map(favouriteFoodDataMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete FavouriteFoodData : {}", id);
        favouriteFoodDataRepository.deleteById(id);
        favouriteFoodDataSearchRepository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<FavouriteFoodDataDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of FavouriteFoodData for query {}", query);
        return favouriteFoodDataSearchRepository.search(queryStringQuery(query), pageable).map(favouriteFoodDataMapper::toDto);
    }
}
