package com.mindforme.app.service;

import com.mindforme.app.service.dto.FeedingDTO;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link com.mindforme.app.domain.Feeding}.
 */
public interface FeedingService {
    /**
     * Save a feeding.
     *
     * @param feedingDTO the entity to save.
     * @return the persisted entity.
     */
    FeedingDTO save(FeedingDTO feedingDTO);

    /**
     * Get all the feedings.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<FeedingDTO> findAll(Pageable pageable);

    /**
     * Get the "id" feeding.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<FeedingDTO> findOne(Long id);

    /**
     * Delete the "id" feeding.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);

    /**
     * Search for the feeding corresponding to the query.
     *
     * @param query the query of the search.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<FeedingDTO> search(String query, Pageable pageable);
}
