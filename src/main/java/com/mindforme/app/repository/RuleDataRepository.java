package com.mindforme.app.repository;

import com.mindforme.app.domain.RuleData;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the RuleData entity.
 */
@SuppressWarnings("unused")
@Repository
public interface RuleDataRepository extends JpaRepository<RuleData, Long> {}
