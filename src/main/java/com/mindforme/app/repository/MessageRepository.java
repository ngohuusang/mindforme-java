package com.mindforme.app.repository;

import com.mindforme.app.domain.Message;
import java.util.List;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the Message entity.
 */
@SuppressWarnings("unused")
@Repository
public interface MessageRepository extends JpaRepository<Message, Long> {
    @Query("select message from Message message where message.sender.login = ?#{principal.username}")
    List<Message> findBySenderIsCurrentUser();

    @Query("select message from Message message where message.receiver.login = ?#{principal.username}")
    List<Message> findByReceiverIsCurrentUser();
}
