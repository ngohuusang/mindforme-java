package com.mindforme.app.repository;

import com.mindforme.app.domain.SupportedHelpRequest;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the SupportedHelpRequest entity.
 */
@Repository
public interface SupportedHelpRequestRepository extends JpaRepository<SupportedHelpRequest, Long> {
    @Query(
        value = "select distinct supportedHelpRequest from SupportedHelpRequest supportedHelpRequest left join fetch supportedHelpRequest.supporteds",
        countQuery = "select count(distinct supportedHelpRequest) from SupportedHelpRequest supportedHelpRequest"
    )
    Page<SupportedHelpRequest> findAllWithEagerRelationships(Pageable pageable);

    @Query(
        "select distinct supportedHelpRequest from SupportedHelpRequest supportedHelpRequest left join fetch supportedHelpRequest.supporteds"
    )
    List<SupportedHelpRequest> findAllWithEagerRelationships();

    @Query(
        "select supportedHelpRequest from SupportedHelpRequest supportedHelpRequest left join fetch supportedHelpRequest.supporteds where supportedHelpRequest.id =:id"
    )
    Optional<SupportedHelpRequest> findOneWithEagerRelationships(@Param("id") Long id);
}
