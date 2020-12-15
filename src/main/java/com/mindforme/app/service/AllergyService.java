package com.mindforme.app.service;

import com.mindforme.app.service.dto.AllergyDTO;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link com.mindforme.app.domain.Allergy}.
 */
public interface AllergyService {
    /**
     * Save a allergy.
     *
     * @param allergyDTO the entity to save.
     * @return the persisted entity.
     */
    AllergyDTO save(AllergyDTO allergyDTO);

    /**
     * Get all the allergies.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<AllergyDTO> findAll(Pageable pageable);

    /**
     * Get the "id" allergy.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<AllergyDTO> findOne(Long id);

    /**
     * Delete the "id" allergy.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);

    /**
     * Search for the allergy corresponding to the query.
     *
     * @param query the query of the search.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<AllergyDTO> search(String query, Pageable pageable);
}
