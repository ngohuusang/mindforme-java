package com.mindforme.app.repository;

import com.mindforme.app.domain.PetBreedData;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the PetBreedData entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PetBreedDataRepository extends JpaRepository<PetBreedData, Long> {}
