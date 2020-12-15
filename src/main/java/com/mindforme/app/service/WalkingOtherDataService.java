package com.mindforme.app.service;

import com.mindforme.app.service.dto.WalkingOtherDataDTO;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link com.mindforme.app.domain.WalkingOtherData}.
 */
public interface WalkingOtherDataService {
    /**
     * Save a walkingOtherData.
     *
     * @param walkingOtherDataDTO the entity to save.
     * @return the persisted entity.
     */
    WalkingOtherDataDTO save(WalkingOtherDataDTO walkingOtherDataDTO);

    /**
     * Get all the walkingOtherData.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<WalkingOtherDataDTO> findAll(Pageable pageable);

    /**
     * Get the "id" walkingOtherData.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<WalkingOtherDataDTO> findOne(Long id);

    /**
     * Delete the "id" walkingOtherData.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);

    /**
     * Search for the walkingOtherData corresponding to the query.
     *
     * @param query the query of the search.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<WalkingOtherDataDTO> search(String query, Pageable pageable);
}
