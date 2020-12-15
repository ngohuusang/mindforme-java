package com.mindforme.app.service;

import com.mindforme.app.service.dto.ChildRelationDataDTO;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link com.mindforme.app.domain.ChildRelationData}.
 */
public interface ChildRelationDataService {
    /**
     * Save a childRelationData.
     *
     * @param childRelationDataDTO the entity to save.
     * @return the persisted entity.
     */
    ChildRelationDataDTO save(ChildRelationDataDTO childRelationDataDTO);

    /**
     * Get all the childRelationData.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<ChildRelationDataDTO> findAll(Pageable pageable);

    /**
     * Get the "id" childRelationData.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<ChildRelationDataDTO> findOne(Long id);

    /**
     * Delete the "id" childRelationData.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);

    /**
     * Search for the childRelationData corresponding to the query.
     *
     * @param query the query of the search.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<ChildRelationDataDTO> search(String query, Pageable pageable);
}
