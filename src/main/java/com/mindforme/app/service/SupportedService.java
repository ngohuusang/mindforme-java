package com.mindforme.app.service;

import com.mindforme.app.service.dto.SupportedDTO;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link com.mindforme.app.domain.Supported}.
 */
public interface SupportedService {
    /**
     * Save a supported.
     *
     * @param supportedDTO the entity to save.
     * @return the persisted entity.
     */
    SupportedDTO save(SupportedDTO supportedDTO);

    /**
     * Get all the supporteds.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<SupportedDTO> findAll(Pageable pageable);

    /**
     * Get the "id" supported.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<SupportedDTO> findOne(Long id);

    /**
     * Delete the "id" supported.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);

    /**
     * Search for the supported corresponding to the query.
     *
     * @param query the query of the search.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<SupportedDTO> search(String query, Pageable pageable);
}
