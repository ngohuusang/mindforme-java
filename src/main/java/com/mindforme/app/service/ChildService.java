package com.mindforme.app.service;

import com.mindforme.app.service.dto.ChildDTO;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link com.mindforme.app.domain.Child}.
 */
public interface ChildService {
    /**
     * Save a child.
     *
     * @param childDTO the entity to save.
     * @return the persisted entity.
     */
    ChildDTO save(ChildDTO childDTO);

    /**
     * Get all the children.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<ChildDTO> findAll(Pageable pageable);

    /**
     * Get all the children with eager load of many-to-many relationships.
     *
     * @return the list of entities.
     */
    Page<ChildDTO> findAllWithEagerRelationships(Pageable pageable);

    /**
     * Get the "id" child.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<ChildDTO> findOne(Long id);

    /**
     * Delete the "id" child.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);

    /**
     * Search for the child corresponding to the query.
     *
     * @param query the query of the search.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<ChildDTO> search(String query, Pageable pageable);
}
