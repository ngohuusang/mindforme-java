package com.mindforme.app.repository.search;

import com.mindforme.app.domain.PetTypeData;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the {@link PetTypeData} entity.
 */
public interface PetTypeDataSearchRepository extends ElasticsearchRepository<PetTypeData, Long> {}
