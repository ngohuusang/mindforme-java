package com.mindforme.app.repository;

import com.mindforme.app.domain.Family;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the Family entity.
 */
@SuppressWarnings("unused")
@Repository
public interface FamilyRepository extends JpaRepository<Family, Long> {}
