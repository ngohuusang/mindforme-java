package com.mindforme.app.repository;

import com.mindforme.app.domain.MedicalCondition;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the MedicalCondition entity.
 */
@SuppressWarnings("unused")
@Repository
public interface MedicalConditionRepository extends JpaRepository<MedicalCondition, Long> {}
