package com.mindforme.app.repository;

import com.mindforme.app.domain.SupportedRelationData;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the SupportedRelationData entity.
 */
@SuppressWarnings("unused")
@Repository
public interface SupportedRelationDataRepository extends JpaRepository<SupportedRelationData, Long> {}
