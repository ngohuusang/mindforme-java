package com.mindforme.app.service;

import com.mindforme.app.service.dto.HouseHelpRequestDTO;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link com.mindforme.app.domain.HouseHelpRequest}.
 */
public interface HouseHelpRequestService {
    /**
     * Save a houseHelpRequest.
     *
     * @param houseHelpRequestDTO the entity to save.
     * @return the persisted entity.
     */
    HouseHelpRequestDTO save(HouseHelpRequestDTO houseHelpRequestDTO);

    /**
     * Get all the houseHelpRequests.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<HouseHelpRequestDTO> findAll(Pageable pageable);

    /**
     * Get the "id" houseHelpRequest.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<HouseHelpRequestDTO> findOne(Long id);

    /**
     * Delete the "id" houseHelpRequest.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);

    /**
     * Search for the houseHelpRequest corresponding to the query.
     *
     * @param query the query of the search.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<HouseHelpRequestDTO> search(String query, Pageable pageable);
}
