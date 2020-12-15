package com.mindforme.app.service;

import com.mindforme.app.service.dto.ChildRelationDTO;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link com.mindforme.app.domain.ChildRelation}.
 */
public interface ChildRelationService {
    /**
     * Save a childRelation.
     *
     * @param childRelationDTO the entity to save.
     * @return the persisted entity.
     */
    ChildRelationDTO save(ChildRelationDTO childRelationDTO);

    /**
     * Get all the childRelations.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<ChildRelationDTO> findAll(Pageable pageable);

    /**
     * Get the "id" childRelation.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<ChildRelationDTO> findOne(Long id);

    /**
     * Delete the "id" childRelation.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);

    /**
     * Search for the childRelation corresponding to the query.
     *
     * @param query the query of the search.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<ChildRelationDTO> search(String query, Pageable pageable);
}
