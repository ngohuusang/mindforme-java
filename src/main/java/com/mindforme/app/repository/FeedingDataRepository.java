package com.mindforme.app.repository;

import com.mindforme.app.domain.FeedingData;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the FeedingData entity.
 */
@SuppressWarnings("unused")
@Repository
public interface FeedingDataRepository extends JpaRepository<FeedingData, Long> {}
