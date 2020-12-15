package com.mindforme.app.service;

import com.mindforme.app.service.dto.PlanDTO;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link com.mindforme.app.domain.Plan}.
 */
public interface PlanService {
    /**
     * Save a plan.
     *
     * @param planDTO the entity to save.
     * @return the persisted entity.
     */
    PlanDTO save(PlanDTO planDTO);

    /**
     * Get all the plans.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<PlanDTO> findAll(Pageable pageable);

    /**
     * Get the "id" plan.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<PlanDTO> findOne(Long id);

    /**
     * Delete the "id" plan.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);

    /**
     * Search for the plan corresponding to the query.
     *
     * @param query the query of the search.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<PlanDTO> search(String query, Pageable pageable);
}
