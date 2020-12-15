package com.mindforme.app.repository;

import com.mindforme.app.domain.Feeding;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the Feeding entity.
 */
@SuppressWarnings("unused")
@Repository
public interface FeedingRepository extends JpaRepository<Feeding, Long> {}
