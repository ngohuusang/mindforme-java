package com.mindforme.app.repository.search;

import com.mindforme.app.domain.AllergyData;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the {@link AllergyData} entity.
 */
public interface AllergyDataSearchRepository extends ElasticsearchRepository<AllergyData, Long> {}
