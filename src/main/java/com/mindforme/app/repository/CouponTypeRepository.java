package com.mindforme.app.repository;

import com.mindforme.app.domain.CouponType;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the CouponType entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CouponTypeRepository extends JpaRepository<CouponType, Long> {}
