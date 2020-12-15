package com.mindforme.app.repository;

import com.mindforme.app.domain.CountryData;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the CountryData entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CountryDataRepository extends JpaRepository<CountryData, Long> {}
