package com.mindforme.app.repository;

import com.mindforme.app.domain.SupportedRelation;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the SupportedRelation entity.
 */
@SuppressWarnings("unused")
@Repository
public interface SupportedRelationRepository extends JpaRepository<SupportedRelation, Long> {}
