package com.mindforme.app.repository;

import com.mindforme.app.domain.PetHelpRequest;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the PetHelpRequest entity.
 */
@Repository
public interface PetHelpRequestRepository extends JpaRepository<PetHelpRequest, Long> {
    @Query(
        value = "select distinct petHelpRequest from PetHelpRequest petHelpRequest left join fetch petHelpRequest.pets",
        countQuery = "select count(distinct petHelpRequest) from PetHelpRequest petHelpRequest"
    )
    Page<PetHelpRequest> findAllWithEagerRelationships(Pageable pageable);

    @Query("select distinct petHelpRequest from PetHelpRequest petHelpRequest left join fetch petHelpRequest.pets")
    List<PetHelpRequest> findAllWithEagerRelationships();

    @Query("select petHelpRequest from PetHelpRequest petHelpRequest left join fetch petHelpRequest.pets where petHelpRequest.id =:id")
    Optional<PetHelpRequest> findOneWithEagerRelationships(@Param("id") Long id);
}
