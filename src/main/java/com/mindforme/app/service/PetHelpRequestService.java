package com.mindforme.app.service;

import com.mindforme.app.service.dto.PetHelpRequestDTO;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link com.mindforme.app.domain.PetHelpRequest}.
 */
public interface PetHelpRequestService {
    /**
     * Save a petHelpRequest.
     *
     * @param petHelpRequestDTO the entity to save.
     * @return the persisted entity.
     */
    PetHelpRequestDTO save(PetHelpRequestDTO petHelpRequestDTO);

    /**
     * Get all the petHelpRequests.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<PetHelpRequestDTO> findAll(Pageable pageable);

    /**
     * Get all the petHelpRequests with eager load of many-to-many relationships.
     *
     * @return the list of entities.
     */
    Page<PetHelpRequestDTO> findAllWithEagerRelationships(Pageable pageable);

    /**
     * Get the "id" petHelpRequest.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<PetHelpRequestDTO> findOne(Long id);

    /**
     * Delete the "id" petHelpRequest.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);

    /**
     * Search for the petHelpRequest corresponding to the query.
     *
     * @param query the query of the search.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<PetHelpRequestDTO> search(String query, Pageable pageable);
}
