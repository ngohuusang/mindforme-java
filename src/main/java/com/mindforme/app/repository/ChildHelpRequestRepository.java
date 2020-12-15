package com.mindforme.app.repository;

import com.mindforme.app.domain.ChildHelpRequest;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the ChildHelpRequest entity.
 */
@Repository
public interface ChildHelpRequestRepository extends JpaRepository<ChildHelpRequest, Long> {
    @Query(
        value = "select distinct childHelpRequest from ChildHelpRequest childHelpRequest left join fetch childHelpRequest.children",
        countQuery = "select count(distinct childHelpRequest) from ChildHelpRequest childHelpRequest"
    )
    Page<ChildHelpRequest> findAllWithEagerRelationships(Pageable pageable);

    @Query("select distinct childHelpRequest from ChildHelpRequest childHelpRequest left join fetch childHelpRequest.children")
    List<ChildHelpRequest> findAllWithEagerRelationships();

    @Query(
        "select childHelpRequest from ChildHelpRequest childHelpRequest left join fetch childHelpRequest.children where childHelpRequest.id =:id"
    )
    Optional<ChildHelpRequest> findOneWithEagerRelationships(@Param("id") Long id);
}
