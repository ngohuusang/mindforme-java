package com.mindforme.app.repository;

import com.mindforme.app.domain.PlanData;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the PlanData entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PlanDataRepository extends JpaRepository<PlanData, Long> {}
