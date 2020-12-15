package com.mindforme.app.service;

import com.mindforme.app.service.dto.FeedingDataDTO;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link com.mindforme.app.domain.FeedingData}.
 */
public interface FeedingDataService {
    /**
     * Save a feedingData.
     *
     * @param feedingDataDTO the entity to save.
     * @return the persisted entity.
     */
    FeedingDataDTO save(FeedingDataDTO feedingDataDTO);

    /**
     * Get all the feedingData.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<FeedingDataDTO> findAll(Pageable pageable);

    /**
     * Get the "id" feedingData.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<FeedingDataDTO> findOne(Long id);

    /**
     * Delete the "id" feedingData.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);

    /**
     * Search for the feedingData corresponding to the query.
     *
     * @param query the query of the search.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<FeedingDataDTO> search(String query, Pageable pageable);
}
