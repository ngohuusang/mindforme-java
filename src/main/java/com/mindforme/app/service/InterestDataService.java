package com.mindforme.app.service;

import com.mindforme.app.service.dto.InterestDataDTO;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link com.mindforme.app.domain.InterestData}.
 */
public interface InterestDataService {
    /**
     * Save a interestData.
     *
     * @param interestDataDTO the entity to save.
     * @return the persisted entity.
     */
    InterestDataDTO save(InterestDataDTO interestDataDTO);

    /**
     * Get all the interestData.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<InterestDataDTO> findAll(Pageable pageable);

    /**
     * Get the "id" interestData.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<InterestDataDTO> findOne(Long id);

    /**
     * Delete the "id" interestData.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);

    /**
     * Search for the interestData corresponding to the query.
     *
     * @param query the query of the search.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<InterestDataDTO> search(String query, Pageable pageable);
}
