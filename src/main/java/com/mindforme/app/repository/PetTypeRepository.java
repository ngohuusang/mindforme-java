package com.mindforme.app.repository;

import com.mindforme.app.domain.PetType;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the PetType entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PetTypeRepository extends JpaRepository<PetType, Long> {}
