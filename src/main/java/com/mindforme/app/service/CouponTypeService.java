package com.mindforme.app.service;

import com.mindforme.app.service.dto.CouponTypeDTO;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link com.mindforme.app.domain.CouponType}.
 */
public interface CouponTypeService {
    /**
     * Save a couponType.
     *
     * @param couponTypeDTO the entity to save.
     * @return the persisted entity.
     */
    CouponTypeDTO save(CouponTypeDTO couponTypeDTO);

    /**
     * Get all the couponTypes.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<CouponTypeDTO> findAll(Pageable pageable);

    /**
     * Get the "id" couponType.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<CouponTypeDTO> findOne(Long id);

    /**
     * Delete the "id" couponType.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);

    /**
     * Search for the couponType corresponding to the query.
     *
     * @param query the query of the search.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<CouponTypeDTO> search(String query, Pageable pageable);
}
