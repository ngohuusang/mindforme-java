package com.mindforme.app.service;

import com.mindforme.app.service.dto.GardenHelpRequestDTO;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link com.mindforme.app.domain.GardenHelpRequest}.
 */
public interface GardenHelpRequestService {
    /**
     * Save a gardenHelpRequest.
     *
     * @param gardenHelpRequestDTO the entity to save.
     * @return the persisted entity.
     */
    GardenHelpRequestDTO save(GardenHelpRequestDTO gardenHelpRequestDTO);

    /**
     * Get all the gardenHelpRequests.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<GardenHelpRequestDTO> findAll(Pageable pageable);

    /**
     * Get the "id" gardenHelpRequest.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<GardenHelpRequestDTO> findOne(Long id);

    /**
     * Delete the "id" gardenHelpRequest.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);

    /**
     * Search for the gardenHelpRequest corresponding to the query.
     *
     * @param query the query of the search.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<GardenHelpRequestDTO> search(String query, Pageable pageable);
}
