package com.mindforme.app.repository;

import com.mindforme.app.domain.UserIdentification;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the UserIdentification entity.
 */
@SuppressWarnings("unused")
@Repository
public interface UserIdentificationRepository extends JpaRepository<UserIdentification, Long> {}
