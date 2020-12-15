package com.mindforme.app.repository;

import com.mindforme.app.domain.MedicalConditionData;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the MedicalConditionData entity.
 */
@SuppressWarnings("unused")
@Repository
public interface MedicalConditionDataRepository extends JpaRepository<MedicalConditionData, Long> {}
