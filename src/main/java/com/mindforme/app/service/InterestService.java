package com.mindforme.app.service;

import com.mindforme.app.service.dto.InterestDTO;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link com.mindforme.app.domain.Interest}.
 */
public interface InterestService {
    /**
     * Save a interest.
     *
     * @param interestDTO the entity to save.
     * @return the persisted entity.
     */
    InterestDTO save(InterestDTO interestDTO);

    /**
     * Get all the interests.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<InterestDTO> findAll(Pageable pageable);

    /**
     * Get the "id" interest.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<InterestDTO> findOne(Long id);

    /**
     * Delete the "id" interest.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);

    /**
     * Search for the interest corresponding to the query.
     *
     * @param query the query of the search.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<InterestDTO> search(String query, Pageable pageable);
}
