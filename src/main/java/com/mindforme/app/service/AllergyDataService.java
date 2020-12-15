package com.mindforme.app.service;

import com.mindforme.app.service.dto.AllergyDataDTO;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link com.mindforme.app.domain.AllergyData}.
 */
public interface AllergyDataService {
    /**
     * Save a allergyData.
     *
     * @param allergyDataDTO the entity to save.
     * @return the persisted entity.
     */
    AllergyDataDTO save(AllergyDataDTO allergyDataDTO);

    /**
     * Get all the allergyData.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<AllergyDataDTO> findAll(Pageable pageable);

    /**
     * Get the "id" allergyData.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<AllergyDataDTO> findOne(Long id);

    /**
     * Delete the "id" allergyData.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);

    /**
     * Search for the allergyData corresponding to the query.
     *
     * @param query the query of the search.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<AllergyDataDTO> search(String query, Pageable pageable);
}
