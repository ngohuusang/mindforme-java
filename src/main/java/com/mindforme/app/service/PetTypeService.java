package com.mindforme.app.service;

import com.mindforme.app.service.dto.PetTypeDTO;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link com.mindforme.app.domain.PetType}.
 */
public interface PetTypeService {
    /**
     * Save a petType.
     *
     * @param petTypeDTO the entity to save.
     * @return the persisted entity.
     */
    PetTypeDTO save(PetTypeDTO petTypeDTO);

    /**
     * Get all the petTypes.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<PetTypeDTO> findAll(Pageable pageable);

    /**
     * Get the "id" petType.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<PetTypeDTO> findOne(Long id);

    /**
     * Delete the "id" petType.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);

    /**
     * Search for the petType corresponding to the query.
     *
     * @param query the query of the search.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<PetTypeDTO> search(String query, Pageable pageable);
}
