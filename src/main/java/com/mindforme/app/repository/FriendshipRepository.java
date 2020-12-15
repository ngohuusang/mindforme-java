package com.mindforme.app.repository;

import com.mindforme.app.domain.Friendship;
import java.util.List;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the Friendship entity.
 */
@SuppressWarnings("unused")
@Repository
public interface FriendshipRepository extends JpaRepository<Friendship, Long> {
    @Query("select friendship from Friendship friendship where friendship.friend.login = ?#{principal.username}")
    List<Friendship> findByFriendIsCurrentUser();

    @Query("select friendship from Friendship friendship where friendship.user.login = ?#{principal.username}")
    List<Friendship> findByUserIsCurrentUser();
}
