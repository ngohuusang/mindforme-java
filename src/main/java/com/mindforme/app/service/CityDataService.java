package com.mindforme.app.service;

import com.mindforme.app.service.dto.CityDataDTO;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link com.mindforme.app.domain.CityData}.
 */
public interface CityDataService {
    /**
     * Save a cityData.
     *
     * @param cityDataDTO the entity to save.
     * @return the persisted entity.
     */
    CityDataDTO save(CityDataDTO cityDataDTO);

    /**
     * Get all the cityData.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<CityDataDTO> findAll(Pageable pageable);

    /**
     * Get the "id" cityData.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<CityDataDTO> findOne(Long id);

    /**
     * Delete the "id" cityData.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);

    /**
     * Search for the cityData corresponding to the query.
     *
     * @param query the query of the search.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<CityDataDTO> search(String query, Pageable pageable);
}
