package com.mindforme.app.repository;

import com.mindforme.app.domain.FavouriteFoodData;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the FavouriteFoodData entity.
 */
@SuppressWarnings("unused")
@Repository
public interface FavouriteFoodDataRepository extends JpaRepository<FavouriteFoodData, Long> {}
