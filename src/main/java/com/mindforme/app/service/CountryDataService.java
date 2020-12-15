package com.mindforme.app.service;

import com.mindforme.app.service.dto.CountryDataDTO;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link com.mindforme.app.domain.CountryData}.
 */
public interface CountryDataService {
    /**
     * Save a countryData.
     *
     * @param countryDataDTO the entity to save.
     * @return the persisted entity.
     */
    CountryDataDTO save(CountryDataDTO countryDataDTO);

    /**
     * Get all the countryData.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<CountryDataDTO> findAll(Pageable pageable);

    /**
     * Get the "id" countryData.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<CountryDataDTO> findOne(Long id);

    /**
     * Delete the "id" countryData.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);

    /**
     * Search for the countryData corresponding to the query.
     *
     * @param query the query of the search.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<CountryDataDTO> search(String query, Pageable pageable);
}
