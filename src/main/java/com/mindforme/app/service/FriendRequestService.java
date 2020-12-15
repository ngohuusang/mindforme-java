package com.mindforme.app.service;

import com.mindforme.app.service.dto.FriendRequestDTO;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link com.mindforme.app.domain.FriendRequest}.
 */
public interface FriendRequestService {
    /**
     * Save a friendRequest.
     *
     * @param friendRequestDTO the entity to save.
     * @return the persisted entity.
     */
    FriendRequestDTO save(FriendRequestDTO friendRequestDTO);

    /**
     * Get all the friendRequests.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<FriendRequestDTO> findAll(Pageable pageable);

    /**
     * Get the "id" friendRequest.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<FriendRequestDTO> findOne(Long id);

    /**
     * Delete the "id" friendRequest.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);

    /**
     * Search for the friendRequest corresponding to the query.
     *
     * @param query the query of the search.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<FriendRequestDTO> search(String query, Pageable pageable);
}
