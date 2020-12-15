package com.mindforme.app.service;

import com.mindforme.app.service.dto.FamilyGalleryDTO;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link com.mindforme.app.domain.FamilyGallery}.
 */
public interface FamilyGalleryService {
    /**
     * Save a familyGallery.
     *
     * @param familyGalleryDTO the entity to save.
     * @return the persisted entity.
     */
    FamilyGalleryDTO save(FamilyGalleryDTO familyGalleryDTO);

    /**
     * Get all the familyGalleries.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<FamilyGalleryDTO> findAll(Pageable pageable);

    /**
     * Get the "id" familyGallery.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<FamilyGalleryDTO> findOne(Long id);

    /**
     * Delete the "id" familyGallery.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);

    /**
     * Search for the familyGallery corresponding to the query.
     *
     * @param query the query of the search.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<FamilyGalleryDTO> search(String query, Pageable pageable);
}
