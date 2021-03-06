package com.mindforme.app.repository;

import com.mindforme.app.domain.Invitation;
import java.util.List;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the Invitation entity.
 */
@SuppressWarnings("unused")
@Repository
public interface InvitationRepository extends JpaRepository<Invitation, Long> {
    @Query("select invitation from Invitation invitation where invitation.user.login = ?#{principal.username}")
    List<Invitation> findByUserIsCurrentUser();
}
