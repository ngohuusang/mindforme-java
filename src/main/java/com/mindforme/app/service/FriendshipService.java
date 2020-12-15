package com.mindforme.app.service;

import com.mindforme.app.service.dto.FriendshipDTO;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link com.mindforme.app.domain.Friendship}.
 */
public interface FriendshipService {
    /**
     * Save a friendship.
     *
     * @param friendshipDTO the entity to save.
     * @return the persisted entity.
     */
    FriendshipDTO save(FriendshipDTO friendshipDTO);

    /**
     * Get all the friendships.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<FriendshipDTO> findAll(Pageable pageable);

    /**
     * Get the "id" friendship.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<FriendshipDTO> findOne(Long id);

    /**
     * Delete the "id" friendship.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);

    /**
     * Search for the friendship corresponding to the query.
     *
     * @param query the query of the search.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<FriendshipDTO> search(String query, Pageable pageable);
}
