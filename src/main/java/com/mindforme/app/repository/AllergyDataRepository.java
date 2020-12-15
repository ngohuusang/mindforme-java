package com.mindforme.app.repository;

import com.mindforme.app.domain.AllergyData;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the AllergyData entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AllergyDataRepository extends JpaRepository<AllergyData, Long> {}
