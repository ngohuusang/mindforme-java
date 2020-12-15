package com.mindforme.app.service;

import com.mindforme.app.service.dto.FriendshipGroupDTO;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link com.mindforme.app.domain.FriendshipGroup}.
 */
public interface FriendshipGroupService {
    /**
     * Save a friendshipGroup.
     *
     * @param friendshipGroupDTO the entity to save.
     * @return the persisted entity.
     */
    FriendshipGroupDTO save(FriendshipGroupDTO friendshipGroupDTO);

    /**
     * Get all the friendshipGroups.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<FriendshipGroupDTO> findAll(Pageable pageable);

    /**
     * Get the "id" friendshipGroup.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<FriendshipGroupDTO> findOne(Long id);

    /**
     * Delete the "id" friendshipGroup.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);

    /**
     * Search for the friendshipGroup corresponding to the query.
     *
     * @param query the query of the search.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<FriendshipGroupDTO> search(String query, Pageable pageable);
}
