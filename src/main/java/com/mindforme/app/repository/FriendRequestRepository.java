package com.mindforme.app.repository;

import com.mindforme.app.domain.FriendRequest;
import java.util.List;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the FriendRequest entity.
 */
@SuppressWarnings("unused")
@Repository
public interface FriendRequestRepository extends JpaRepository<FriendRequest, Long> {
    @Query("select friendRequest from FriendRequest friendRequest where friendRequest.friend.login = ?#{principal.username}")
    List<FriendRequest> findByFriendIsCurrentUser();

    @Query("select friendRequest from FriendRequest friendRequest where friendRequest.user.login = ?#{principal.username}")
    List<FriendRequest> findByUserIsCurrentUser();
}
