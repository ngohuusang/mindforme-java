package com.mindforme.app.repository.search;

import com.mindforme.app.domain.PetBreedData;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the {@link PetBreedData} entity.
 */
public interface PetBreedDataSearchRepository extends ElasticsearchRepository<PetBreedData, Long> {}
