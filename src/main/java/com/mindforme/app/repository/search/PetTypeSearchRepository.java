package com.mindforme.app.repository.search;

import com.mindforme.app.domain.PetType;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the {@link PetType} entity.
 */
public interface PetTypeSearchRepository extends ElasticsearchRepository<PetType, Long> {}
