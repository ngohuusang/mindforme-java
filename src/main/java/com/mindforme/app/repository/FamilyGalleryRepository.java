package com.mindforme.app.repository;

import com.mindforme.app.domain.FamilyGallery;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the FamilyGallery entity.
 */
@SuppressWarnings("unused")
@Repository
public interface FamilyGalleryRepository extends JpaRepository<FamilyGallery, Long> {}
