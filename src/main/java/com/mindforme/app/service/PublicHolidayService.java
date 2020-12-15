package com.mindforme.app.service;

import com.mindforme.app.service.dto.PublicHolidayDTO;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link com.mindforme.app.domain.PublicHoliday}.
 */
public interface PublicHolidayService {
    /**
     * Save a publicHoliday.
     *
     * @param publicHolidayDTO the entity to save.
     * @return the persisted entity.
     */
    PublicHolidayDTO save(PublicHolidayDTO publicHolidayDTO);

    /**
     * Get all the publicHolidays.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<PublicHolidayDTO> findAll(Pageable pageable);

    /**
     * Get the "id" publicHoliday.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<PublicHolidayDTO> findOne(Long id);

    /**
     * Delete the "id" publicHoliday.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);

    /**
     * Search for the publicHoliday corresponding to the query.
     *
     * @param query the query of the search.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<PublicHolidayDTO> search(String query, Pageable pageable);
}
