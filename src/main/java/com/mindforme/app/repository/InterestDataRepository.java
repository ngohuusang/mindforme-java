package com.mindforme.app.repository;

import com.mindforme.app.domain.InterestData;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the InterestData entity.
 */
@SuppressWarnings("unused")
@Repository
public interface InterestDataRepository extends JpaRepository<InterestData, Long> {}
