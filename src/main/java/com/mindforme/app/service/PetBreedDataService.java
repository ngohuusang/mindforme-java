package com.mindforme.app.service;

import com.mindforme.app.service.dto.PetBreedDataDTO;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link com.mindforme.app.domain.PetBreedData}.
 */
public interface PetBreedDataService {
    /**
     * Save a petBreedData.
     *
     * @param petBreedDataDTO the entity to save.
     * @return the persisted entity.
     */
    PetBreedDataDTO save(PetBreedDataDTO petBreedDataDTO);

    /**
     * Get all the petBreedData.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<PetBreedDataDTO> findAll(Pageable pageable);

    /**
     * Get the "id" petBreedData.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<PetBreedDataDTO> findOne(Long id);

    /**
     * Delete the "id" petBreedData.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);

    /**
     * Search for the petBreedData corresponding to the query.
     *
     * @param query the query of the search.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<PetBreedDataDTO> search(String query, Pageable pageable);
}
