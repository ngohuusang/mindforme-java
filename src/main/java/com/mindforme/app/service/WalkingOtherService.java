package com.mindforme.app.service;

import com.mindforme.app.service.dto.WalkingOtherDTO;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link com.mindforme.app.domain.WalkingOther}.
 */
public interface WalkingOtherService {
    /**
     * Save a walkingOther.
     *
     * @param walkingOtherDTO the entity to save.
     * @return the persisted entity.
     */
    WalkingOtherDTO save(WalkingOtherDTO walkingOtherDTO);

    /**
     * Get all the walkingOthers.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<WalkingOtherDTO> findAll(Pageable pageable);

    /**
     * Get the "id" walkingOther.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<WalkingOtherDTO> findOne(Long id);

    /**
     * Delete the "id" walkingOther.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);

    /**
     * Search for the walkingOther corresponding to the query.
     *
     * @param query the query of the search.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<WalkingOtherDTO> search(String query, Pageable pageable);
}
