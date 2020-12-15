package com.mindforme.app.repository;

import com.mindforme.app.domain.WorkingWithChildren;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the WorkingWithChildren entity.
 */
@SuppressWarnings("unused")
@Repository
public interface WorkingWithChildrenRepository extends JpaRepository<WorkingWithChildren, Long> {}
