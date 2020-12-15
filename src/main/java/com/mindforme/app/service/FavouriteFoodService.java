package com.mindforme.app.service;

import com.mindforme.app.service.dto.FavouriteFoodDTO;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link com.mindforme.app.domain.FavouriteFood}.
 */
public interface FavouriteFoodService {
    /**
     * Save a favouriteFood.
     *
     * @param favouriteFoodDTO the entity to save.
     * @return the persisted entity.
     */
    FavouriteFoodDTO save(FavouriteFoodDTO favouriteFoodDTO);

    /**
     * Get all the favouriteFoods.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<FavouriteFoodDTO> findAll(Pageable pageable);

    /**
     * Get the "id" favouriteFood.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<FavouriteFoodDTO> findOne(Long id);

    /**
     * Delete the "id" favouriteFood.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);

    /**
     * Search for the favouriteFood corresponding to the query.
     *
     * @param query the query of the search.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<FavouriteFoodDTO> search(String query, Pageable pageable);
}
