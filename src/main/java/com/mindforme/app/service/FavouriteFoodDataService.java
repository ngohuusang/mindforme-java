package com.mindforme.app.service;

import com.mindforme.app.service.dto.FavouriteFoodDataDTO;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link com.mindforme.app.domain.FavouriteFoodData}.
 */
public interface FavouriteFoodDataService {
    /**
     * Save a favouriteFoodData.
     *
     * @param favouriteFoodDataDTO the entity to save.
     * @return the persisted entity.
     */
    FavouriteFoodDataDTO save(FavouriteFoodDataDTO favouriteFoodDataDTO);

    /**
     * Get all the favouriteFoodData.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<FavouriteFoodDataDTO> findAll(Pageable pageable);

    /**
     * Get the "id" favouriteFoodData.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<FavouriteFoodDataDTO> findOne(Long id);

    /**
     * Delete the "id" favouriteFoodData.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);

    /**
     * Search for the favouriteFoodData corresponding to the query.
     *
     * @param query the query of the search.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<FavouriteFoodDataDTO> search(String query, Pageable pageable);
}
