package com.mindforme.app.service;

import com.mindforme.app.service.dto.SupportedRelationDataDTO;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link com.mindforme.app.domain.SupportedRelationData}.
 */
public interface SupportedRelationDataService {
    /**
     * Save a supportedRelationData.
     *
     * @param supportedRelationDataDTO the entity to save.
     * @return the persisted entity.
     */
    SupportedRelationDataDTO save(SupportedRelationDataDTO supportedRelationDataDTO);

    /**
     * Get all the supportedRelationData.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<SupportedRelationDataDTO> findAll(Pageable pageable);

    /**
     * Get the "id" supportedRelationData.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<SupportedRelationDataDTO> findOne(Long id);

    /**
     * Delete the "id" supportedRelationData.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);

    /**
     * Search for the supportedRelationData corresponding to the query.
     *
     * @param query the query of the search.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<SupportedRelationDataDTO> search(String query, Pageable pageable);
}
