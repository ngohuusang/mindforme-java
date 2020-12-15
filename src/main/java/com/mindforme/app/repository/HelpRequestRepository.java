package com.mindforme.app.repository;

import com.mindforme.app.domain.HelpRequest;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the HelpRequest entity.
 */
@SuppressWarnings("unused")
@Repository
public interface HelpRequestRepository extends JpaRepository<HelpRequest, Long> {}
