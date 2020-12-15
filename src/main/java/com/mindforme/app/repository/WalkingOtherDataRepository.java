package com.mindforme.app.repository;

import com.mindforme.app.domain.WalkingOtherData;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the WalkingOtherData entity.
 */
@SuppressWarnings("unused")
@Repository
public interface WalkingOtherDataRepository extends JpaRepository<WalkingOtherData, Long> {}
