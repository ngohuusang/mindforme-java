package com.mindforme.app.service;

import com.mindforme.app.service.dto.MedicalConditionDataDTO;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link com.mindforme.app.domain.MedicalConditionData}.
 */
public interface MedicalConditionDataService {
    /**
     * Save a medicalConditionData.
     *
     * @param medicalConditionDataDTO the entity to save.
     * @return the persisted entity.
     */
    MedicalConditionDataDTO save(MedicalConditionDataDTO medicalConditionDataDTO);

    /**
     * Get all the medicalConditionData.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<MedicalConditionDataDTO> findAll(Pageable pageable);

    /**
     * Get the "id" medicalConditionData.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<MedicalConditionDataDTO> findOne(Long id);

    /**
     * Delete the "id" medicalConditionData.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);

    /**
     * Search for the medicalConditionData corresponding to the query.
     *
     * @param query the query of the search.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<MedicalConditionDataDTO> search(String query, Pageable pageable);
}
