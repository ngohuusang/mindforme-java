package com.mindforme.app.repository;

import com.mindforme.app.domain.ChildRelation;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the ChildRelation entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ChildRelationRepository extends JpaRepository<ChildRelation, Long> {}
