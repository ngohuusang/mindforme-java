package com.mindforme.app.service;

import com.mindforme.app.service.dto.StateDataDTO;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link com.mindforme.app.domain.StateData}.
 */
public interface StateDataService {
    /**
     * Save a stateData.
     *
     * @param stateDataDTO the entity to save.
     * @return the persisted entity.
     */
    StateDataDTO save(StateDataDTO stateDataDTO);

    /**
     * Get all the stateData.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<StateDataDTO> findAll(Pageable pageable);

    /**
     * Get the "id" stateData.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<StateDataDTO> findOne(Long id);

    /**
     * Delete the "id" stateData.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);

    /**
     * Search for the stateData corresponding to the query.
     *
     * @param query the query of the search.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<StateDataDTO> search(String query, Pageable pageable);
}
