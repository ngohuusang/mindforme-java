package com.mindforme.app.repository;

import com.mindforme.app.domain.WalkingOther;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the WalkingOther entity.
 */
@SuppressWarnings("unused")
@Repository
public interface WalkingOtherRepository extends JpaRepository<WalkingOther, Long> {}
