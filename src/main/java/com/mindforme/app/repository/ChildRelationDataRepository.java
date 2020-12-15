package com.mindforme.app.repository;

import com.mindforme.app.domain.ChildRelationData;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the ChildRelationData entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ChildRelationDataRepository extends JpaRepository<ChildRelationData, Long> {}
