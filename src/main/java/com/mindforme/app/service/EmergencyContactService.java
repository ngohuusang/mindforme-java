package com.mindforme.app.service;

import com.mindforme.app.service.dto.EmergencyContactDTO;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link com.mindforme.app.domain.EmergencyContact}.
 */
public interface EmergencyContactService {
    /**
     * Save a emergencyContact.
     *
     * @param emergencyContactDTO the entity to save.
     * @return the persisted entity.
     */
    EmergencyContactDTO save(EmergencyContactDTO emergencyContactDTO);

    /**
     * Get all the emergencyContacts.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<EmergencyContactDTO> findAll(Pageable pageable);

    /**
     * Get the "id" emergencyContact.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<EmergencyContactDTO> findOne(Long id);

    /**
     * Delete the "id" emergencyContact.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);

    /**
     * Search for the emergencyContact corresponding to the query.
     *
     * @param query the query of the search.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<EmergencyContactDTO> search(String query, Pageable pageable);
}
