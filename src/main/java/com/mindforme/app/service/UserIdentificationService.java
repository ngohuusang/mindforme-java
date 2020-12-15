package com.mindforme.app.service;

import com.mindforme.app.service.dto.UserIdentificationDTO;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link com.mindforme.app.domain.UserIdentification}.
 */
public interface UserIdentificationService {
    /**
     * Save a userIdentification.
     *
     * @param userIdentificationDTO the entity to save.
     * @return the persisted entity.
     */
    UserIdentificationDTO save(UserIdentificationDTO userIdentificationDTO);

    /**
     * Get all the userIdentifications.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<UserIdentificationDTO> findAll(Pageable pageable);

    /**
     * Get the "id" userIdentification.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<UserIdentificationDTO> findOne(Long id);

    /**
     * Delete the "id" userIdentification.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);

    /**
     * Search for the userIdentification corresponding to the query.
     *
     * @param query the query of the search.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<UserIdentificationDTO> search(String query, Pageable pageable);
}
