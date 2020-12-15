package com.mindforme.app.service;

import com.mindforme.app.service.dto.MessageItemDTO;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link com.mindforme.app.domain.MessageItem}.
 */
public interface MessageItemService {
    /**
     * Save a messageItem.
     *
     * @param messageItemDTO the entity to save.
     * @return the persisted entity.
     */
    MessageItemDTO save(MessageItemDTO messageItemDTO);

    /**
     * Get all the messageItems.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<MessageItemDTO> findAll(Pageable pageable);

    /**
     * Get the "id" messageItem.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<MessageItemDTO> findOne(Long id);

    /**
     * Delete the "id" messageItem.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);

    /**
     * Search for the messageItem corresponding to the query.
     *
     * @param query the query of the search.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<MessageItemDTO> search(String query, Pageable pageable);
}
