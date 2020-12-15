package com.mindforme.app.repository;

import com.mindforme.app.domain.CityData;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the CityData entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CityDataRepository extends JpaRepository<CityData, Long> {}
