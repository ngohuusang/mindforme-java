package com.mindforme.app.repository;

import com.mindforme.app.domain.GardenHelpRequest;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the GardenHelpRequest entity.
 */
@SuppressWarnings("unused")
@Repository
public interface GardenHelpRequestRepository extends JpaRepository<GardenHelpRequest, Long> {}
