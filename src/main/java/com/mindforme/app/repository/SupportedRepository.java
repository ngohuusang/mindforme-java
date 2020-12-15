package com.mindforme.app.repository;

import com.mindforme.app.domain.Supported;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the Supported entity.
 */
@SuppressWarnings("unused")
@Repository
public interface SupportedRepository extends JpaRepository<Supported, Long> {}
