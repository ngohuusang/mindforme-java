package com.mindforme.app.repository;

import com.mindforme.app.domain.Pet;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the Pet entity.
 */
@Repository
public interface PetRepository extends JpaRepository<Pet, Long> {
    @Query(
        value = "select distinct pet from Pet pet left join fetch pet.feedings left join fetch pet.rules left join fetch pet.allergies left join fetch pet.walkings",
        countQuery = "select count(distinct pet) from Pet pet"
    )
    Page<Pet> findAllWithEagerRelationships(Pageable pageable);

    @Query(
        "select distinct pet from Pet pet left join fetch pet.feedings left join fetch pet.rules left join fetch pet.allergies left join fetch pet.walkings"
    )
    List<Pet> findAllWithEagerRelationships();

    @Query(
        "select pet from Pet pet left join fetch pet.feedings left join fetch pet.rules left join fetch pet.allergies left join fetch pet.walkings where pet.id =:id"
    )
    Optional<Pet> findOneWithEagerRelationships(@Param("id") Long id);
}
