package com.mindforme.app.repository;

import com.mindforme.app.domain.PublicHoliday;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the PublicHoliday entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PublicHolidayRepository extends JpaRepository<PublicHoliday, Long> {}
