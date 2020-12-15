package com.mindforme.app.repository;

import com.mindforme.app.domain.MessageItem;
import java.util.List;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the MessageItem entity.
 */
@SuppressWarnings("unused")
@Repository
public interface MessageItemRepository extends JpaRepository<MessageItem, Long> {
    @Query("select messageItem from MessageItem messageItem where messageItem.sender.login = ?#{principal.username}")
    List<MessageItem> findBySenderIsCurrentUser();
}
