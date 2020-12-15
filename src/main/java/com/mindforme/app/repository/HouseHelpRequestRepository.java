package com.mindforme.app.repository;

import com.mindforme.app.domain.HouseHelpRequest;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the HouseHelpRequest entity.
 */
@SuppressWarnings("unused")
@Repository
public interface HouseHelpRequestRepository extends JpaRepository<HouseHelpRequest, Long> {}
