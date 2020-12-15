package com.mindforme.app.service.impl;

import static org.elasticsearch.index.query.QueryBuilders.*;

import com.mindforme.app.domain.FavouriteFood;
import com.mindforme.app.repository.FavouriteFoodRepository;
import com.mindforme.app.repository.search.FavouriteFoodSearchRepository;
import com.mindforme.app.service.FavouriteFoodService;
import com.mindforme.app.service.dto.FavouriteFoodDTO;
import com.mindforme.app.service.mapper.FavouriteFoodMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link FavouriteFood}.
 */
@Service
@Transactional
public class FavouriteFoodServiceImpl implements FavouriteFoodService {
    private final Logger log = LoggerFactory.getLogger(FavouriteFoodServiceImpl.class);

    private final FavouriteFoodRepository favouriteFoodRepository;

    private final FavouriteFoodMapper favouriteFoodMapper;

    private final FavouriteFoodSearchRepository favouriteFoodSearchRepository;

    public FavouriteFoodServiceImpl(
        FavouriteFoodRepository favouriteFoodRepository,
        FavouriteFoodMapper favouriteFoodMapper,
        FavouriteFoodSearchRepository favouriteFoodSearchRepository
    ) {
        this.favouriteFoodRepository = favouriteFoodRepository;
        this.favouriteFoodMapper = favouriteFoodMapper;
        this.favouriteFoodSearchRepository = favouriteFoodSearchRepository;
    }

    @Override
    public FavouriteFoodDTO save(FavouriteFoodDTO favouriteFoodDTO) {
        log.debug("Request to save FavouriteFood : {}", favouriteFoodDTO);
        FavouriteFood favouriteFood = favouriteFoodMapper.toEntity(favouriteFoodDTO);
        favouriteFood = favouriteFoodRepository.save(favouriteFood);
        FavouriteFoodDTO result = favouriteFoodMapper.toDto(favouriteFood);
        favouriteFoodSearchRepository.save(favouriteFood);
        return result;
    }

    @Override
    @Transactional(readOnly = true)
    public Page<FavouriteFoodDTO> findAll(Pageable pageable) {
        log.debug("Request to get all FavouriteFoods");
        return favouriteFoodRepository.findAll(pageable).map(favouriteFoodMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<FavouriteFoodDTO> findOne(Long id) {
        log.debug("Request to get FavouriteFood : {}", id);
        return favouriteFoodRepository.findById(id).map(favouriteFoodMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete FavouriteFood : {}", id);
        favouriteFoodRepository.deleteById(id);
        favouriteFoodSearchRepository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<FavouriteFoodDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of FavouriteFoods for query {}", query);
        return favouriteFoodSearchRepository.search(queryStringQuery(query), pageable).map(favouriteFoodMapper::toDto);
    }
}
