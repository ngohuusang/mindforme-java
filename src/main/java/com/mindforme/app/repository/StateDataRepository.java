package com.mindforme.app.repository;

import com.mindforme.app.domain.StateData;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the StateData entity.
 */
@SuppressWarnings("unused")
@Repository
public interface StateDataRepository extends JpaRepository<StateData, Long> {}
