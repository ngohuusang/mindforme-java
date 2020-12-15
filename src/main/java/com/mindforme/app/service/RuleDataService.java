package com.mindforme.app.service;

import com.mindforme.app.service.dto.RuleDataDTO;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link com.mindforme.app.domain.RuleData}.
 */
public interface RuleDataService {
    /**
     * Save a ruleData.
     *
     * @param ruleDataDTO the entity to save.
     * @return the persisted entity.
     */
    RuleDataDTO save(RuleDataDTO ruleDataDTO);

    /**
     * Get all the ruleData.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<RuleDataDTO> findAll(Pageable pageable);

    /**
     * Get the "id" ruleData.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<RuleDataDTO> findOne(Long id);

    /**
     * Delete the "id" ruleData.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);

    /**
     * Search for the ruleData corresponding to the query.
     *
     * @param query the query of the search.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<RuleDataDTO> search(String query, Pageable pageable);
}
