package com.mindforme.app.repository;

import com.mindforme.app.domain.Allergy;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the Allergy entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AllergyRepository extends JpaRepository<Allergy, Long> {}
