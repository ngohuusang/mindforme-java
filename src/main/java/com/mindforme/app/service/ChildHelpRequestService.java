package com.mindforme.app.service;

import com.mindforme.app.service.dto.ChildHelpRequestDTO;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link com.mindforme.app.domain.ChildHelpRequest}.
 */
public interface ChildHelpRequestService {
    /**
     * Save a childHelpRequest.
     *
     * @param childHelpRequestDTO the entity to save.
     * @return the persisted entity.
     */
    ChildHelpRequestDTO save(ChildHelpRequestDTO childHelpRequestDTO);

    /**
     * Get all the childHelpRequests.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<ChildHelpRequestDTO> findAll(Pageable pageable);

    /**
     * Get all the childHelpRequests with eager load of many-to-many relationships.
     *
     * @return the list of entities.
     */
    Page<ChildHelpRequestDTO> findAllWithEagerRelationships(Pageable pageable);

    /**
     * Get the "id" childHelpRequest.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<ChildHelpRequestDTO> findOne(Long id);

    /**
     * Delete the "id" childHelpRequest.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);

    /**
     * Search for the childHelpRequest corresponding to the query.
     *
     * @param query the query of the search.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<ChildHelpRequestDTO> search(String query, Pageable pageable);
}
