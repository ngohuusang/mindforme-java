package com.mindforme.app.service;

import com.mindforme.app.service.dto.SupportedHelpRequestDTO;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link com.mindforme.app.domain.SupportedHelpRequest}.
 */
public interface SupportedHelpRequestService {
    /**
     * Save a supportedHelpRequest.
     *
     * @param supportedHelpRequestDTO the entity to save.
     * @return the persisted entity.
     */
    SupportedHelpRequestDTO save(SupportedHelpRequestDTO supportedHelpRequestDTO);

    /**
     * Get all the supportedHelpRequests.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<SupportedHelpRequestDTO> findAll(Pageable pageable);

    /**
     * Get all the supportedHelpRequests with eager load of many-to-many relationships.
     *
     * @return the list of entities.
     */
    Page<SupportedHelpRequestDTO> findAllWithEagerRelationships(Pageable pageable);

    /**
     * Get the "id" supportedHelpRequest.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<SupportedHelpRequestDTO> findOne(Long id);

    /**
     * Delete the "id" supportedHelpRequest.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);

    /**
     * Search for the supportedHelpRequest corresponding to the query.
     *
     * @param query the query of the search.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<SupportedHelpRequestDTO> search(String query, Pageable pageable);
}
