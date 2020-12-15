package com.mindforme.app.service;

import com.mindforme.app.service.dto.SupportedRelationDTO;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link com.mindforme.app.domain.SupportedRelation}.
 */
public interface SupportedRelationService {
    /**
     * Save a supportedRelation.
     *
     * @param supportedRelationDTO the entity to save.
     * @return the persisted entity.
     */
    SupportedRelationDTO save(SupportedRelationDTO supportedRelationDTO);

    /**
     * Get all the supportedRelations.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<SupportedRelationDTO> findAll(Pageable pageable);

    /**
     * Get the "id" supportedRelation.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<SupportedRelationDTO> findOne(Long id);

    /**
     * Delete the "id" supportedRelation.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);

    /**
     * Search for the supportedRelation corresponding to the query.
     *
     * @param query the query of the search.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<SupportedRelationDTO> search(String query, Pageable pageable);
}
