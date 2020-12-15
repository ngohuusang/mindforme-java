package com.mindforme.app.repository;

import com.mindforme.app.domain.Notification;
import java.util.List;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the Notification entity.
 */
@SuppressWarnings("unused")
@Repository
public interface NotificationRepository extends JpaRepository<Notification, Long> {
    @Query("select notification from Notification notification where notification.sender.login = ?#{principal.username}")
    List<Notification> findBySenderIsCurrentUser();

    @Query("select notification from Notification notification where notification.receiver.login = ?#{principal.username}")
    List<Notification> findByReceiverIsCurrentUser();
}
