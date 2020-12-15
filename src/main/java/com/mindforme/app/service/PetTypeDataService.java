package com.mindforme.app.service;

import com.mindforme.app.service.dto.PetTypeDataDTO;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link com.mindforme.app.domain.PetTypeData}.
 */
public interface PetTypeDataService {
    /**
     * Save a petTypeData.
     *
     * @param petTypeDataDTO the entity to save.
     * @return the persisted entity.
     */
    PetTypeDataDTO save(PetTypeDataDTO petTypeDataDTO);

    /**
     * Get all the petTypeData.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<PetTypeDataDTO> findAll(Pageable pageable);

    /**
     * Get the "id" petTypeData.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<PetTypeDataDTO> findOne(Long id);

    /**
     * Delete the "id" petTypeData.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);

    /**
     * Search for the petTypeData corresponding to the query.
     *
     * @param query the query of the search.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<PetTypeDataDTO> search(String query, Pageable pageable);
}
