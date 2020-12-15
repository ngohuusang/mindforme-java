package com.mindforme.app.repository;

import com.mindforme.app.domain.MindingNotification;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the MindingNotification entity.
 */
@SuppressWarnings("unused")
@Repository
public interface MindingNotificationRepository extends JpaRepository<MindingNotification, Long> {}
