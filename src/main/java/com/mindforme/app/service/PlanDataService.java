package com.mindforme.app.service;

import com.mindforme.app.service.dto.PlanDataDTO;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link com.mindforme.app.domain.PlanData}.
 */
public interface PlanDataService {
    /**
     * Save a planData.
     *
     * @param planDataDTO the entity to save.
     * @return the persisted entity.
     */
    PlanDataDTO save(PlanDataDTO planDataDTO);

    /**
     * Get all the planData.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<PlanDataDTO> findAll(Pageable pageable);

    /**
     * Get the "id" planData.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<PlanDataDTO> findOne(Long id);

    /**
     * Delete the "id" planData.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);

    /**
     * Search for the planData corresponding to the query.
     *
     * @param query the query of the search.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<PlanDataDTO> search(String query, Pageable pageable);
}
