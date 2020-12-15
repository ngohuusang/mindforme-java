package com.mindforme.app.service;

import com.mindforme.app.service.dto.CouponDTO;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link com.mindforme.app.domain.Coupon}.
 */
public interface CouponService {
    /**
     * Save a coupon.
     *
     * @param couponDTO the entity to save.
     * @return the persisted entity.
     */
    CouponDTO save(CouponDTO couponDTO);

    /**
     * Get all the coupons.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<CouponDTO> findAll(Pageable pageable);

    /**
     * Get the "id" coupon.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<CouponDTO> findOne(Long id);

    /**
     * Delete the "id" coupon.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);

    /**
     * Search for the coupon corresponding to the query.
     *
     * @param query the query of the search.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<CouponDTO> search(String query, Pageable pageable);
}
