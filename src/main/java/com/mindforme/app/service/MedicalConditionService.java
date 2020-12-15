package com.mindforme.app.service;

import com.mindforme.app.service.dto.MedicalConditionDTO;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link com.mindforme.app.domain.MedicalCondition}.
 */
public interface MedicalConditionService {
    /**
     * Save a medicalCondition.
     *
     * @param medicalConditionDTO the entity to save.
     * @return the persisted entity.
     */
    MedicalConditionDTO save(MedicalConditionDTO medicalConditionDTO);

    /**
     * Get all the medicalConditions.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<MedicalConditionDTO> findAll(Pageable pageable);

    /**
     * Get the "id" medicalCondition.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<MedicalConditionDTO> findOne(Long id);

    /**
     * Delete the "id" medicalCondition.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);

    /**
     * Search for the medicalCondition corresponding to the query.
     *
     * @param query the query of the search.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<MedicalConditionDTO> search(String query, Pageable pageable);
}
