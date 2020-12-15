package com.mindforme.app.repository.search;

import com.mindforme.app.domain.Pet;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the {@link Pet} entity.
 */
public interface PetSearchRepository extends ElasticsearchRepository<Pet, Long> {}
