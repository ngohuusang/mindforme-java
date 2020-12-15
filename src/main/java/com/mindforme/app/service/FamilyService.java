package com.mindforme.app.service;

import com.mindforme.app.service.dto.FamilyDTO;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link com.mindforme.app.domain.Family}.
 */
public interface FamilyService {
    /**
     * Save a family.
     *
     * @param familyDTO the entity to save.
     * @return the persisted entity.
     */
    FamilyDTO save(FamilyDTO familyDTO);

    /**
     * Get all the families.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<FamilyDTO> findAll(Pageable pageable);

    /**
     * Get the "id" family.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<FamilyDTO> findOne(Long id);

    /**
     * Delete the "id" family.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);

    /**
     * Search for the family corresponding to the query.
     *
     * @param query the query of the search.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<FamilyDTO> search(String query, Pageable pageable);
}
