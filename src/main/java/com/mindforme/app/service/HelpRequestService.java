package com.mindforme.app.service;

import com.mindforme.app.service.dto.HelpRequestDTO;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link com.mindforme.app.domain.HelpRequest}.
 */
public interface HelpRequestService {
    /**
     * Save a helpRequest.
     *
     * @param helpRequestDTO the entity to save.
     * @return the persisted entity.
     */
    HelpRequestDTO save(HelpRequestDTO helpRequestDTO);

    /**
     * Get all the helpRequests.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<HelpRequestDTO> findAll(Pageable pageable);

    /**
     * Get the "id" helpRequest.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<HelpRequestDTO> findOne(Long id);

    /**
     * Delete the "id" helpRequest.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);

    /**
     * Search for the helpRequest corresponding to the query.
     *
     * @param query the query of the search.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<HelpRequestDTO> search(String query, Pageable pageable);
}
