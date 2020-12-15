package com.mindforme.app.repository;

import com.mindforme.app.domain.FavouriteFood;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the FavouriteFood entity.
 */
@SuppressWarnings("unused")
@Repository
public interface FavouriteFoodRepository extends JpaRepository<FavouriteFood, Long> {}
