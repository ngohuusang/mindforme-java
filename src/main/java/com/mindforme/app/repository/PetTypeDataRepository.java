package com.mindforme.app.repository;

import com.mindforme.app.domain.PetTypeData;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the PetTypeData entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PetTypeDataRepository extends JpaRepository<PetTypeData, Long> {}
