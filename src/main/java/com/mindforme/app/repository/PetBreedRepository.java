package com.mindforme.app.repository;

import com.mindforme.app.domain.PetBreed;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the PetBreed entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PetBreedRepository extends JpaRepository<PetBreed, Long> {}
