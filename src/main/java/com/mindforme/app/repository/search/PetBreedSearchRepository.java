package com.mindforme.app.repository.search;

import com.mindforme.app.domain.PetBreed;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the {@link PetBreed} entity.
 */
public interface PetBreedSearchRepository extends ElasticsearchRepository<PetBreed, Long> {}
