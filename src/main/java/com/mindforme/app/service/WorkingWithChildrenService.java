package com.mindforme.app.service;

import com.mindforme.app.service.dto.WorkingWithChildrenDTO;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link com.mindforme.app.domain.WorkingWithChildren}.
 */
public interface WorkingWithChildrenService {
    /**
     * Save a workingWithChildren.
     *
     * @param workingWithChildrenDTO the entity to save.
     * @return the persisted entity.
     */
    WorkingWithChildrenDTO save(WorkingWithChildrenDTO workingWithChildrenDTO);

    /**
     * Get all the workingWithChildren.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<WorkingWithChildrenDTO> findAll(Pageable pageable);

    /**
     * Get the "id" workingWithChildren.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<WorkingWithChildrenDTO> findOne(Long id);

    /**
     * Delete the "id" workingWithChildren.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);

    /**
     * Search for the workingWithChildren corresponding to the query.
     *
     * @param query the query of the search.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<WorkingWithChildrenDTO> search(String query, Pageable pageable);
}
