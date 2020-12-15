package com.mindforme.app.service;

import com.mindforme.app.service.dto.PetBreedDTO;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link com.mindforme.app.domain.PetBreed}.
 */
public interface PetBreedService {
    /**
     * Save a petBreed.
     *
     * @param petBreedDTO the entity to save.
     * @return the persisted entity.
     */
    PetBreedDTO save(PetBreedDTO petBreedDTO);

    /**
     * Get all the petBreeds.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<PetBreedDTO> findAll(Pageable pageable);

    /**
     * Get the "id" petBreed.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<PetBreedDTO> findOne(Long id);

    /**
     * Delete the "id" petBreed.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);

    /**
     * Search for the petBreed corresponding to the query.
     *
     * @param query the query of the search.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<PetBreedDTO> search(String query, Pageable pageable);
}
