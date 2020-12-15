package com.mindforme.app.repository;

import com.mindforme.app.domain.FriendshipGroup;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the FriendshipGroup entity.
 */
@SuppressWarnings("unused")
@Repository
public interface FriendshipGroupRepository extends JpaRepository<FriendshipGroup, Long> {}
