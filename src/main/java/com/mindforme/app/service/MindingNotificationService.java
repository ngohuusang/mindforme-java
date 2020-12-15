package com.mindforme.app.service;

import com.mindforme.app.service.dto.MindingNotificationDTO;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link com.mindforme.app.domain.MindingNotification}.
 */
public interface MindingNotificationService {
    /**
     * Save a mindingNotification.
     *
     * @param mindingNotificationDTO the entity to save.
     * @return the persisted entity.
     */
    MindingNotificationDTO save(MindingNotificationDTO mindingNotificationDTO);

    /**
     * Get all the mindingNotifications.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<MindingNotificationDTO> findAll(Pageable pageable);

    /**
     * Get the "id" mindingNotification.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<MindingNotificationDTO> findOne(Long id);

    /**
     * Delete the "id" mindingNotification.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);

    /**
     * Search for the mindingNotification corresponding to the query.
     *
     * @param query the query of the search.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<MindingNotificationDTO> search(String query, Pageable pageable);
}
