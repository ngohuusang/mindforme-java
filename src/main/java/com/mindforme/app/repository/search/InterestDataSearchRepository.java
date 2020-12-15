package com.mindforme.app.repository.search;

import com.mindforme.app.domain.InterestData;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the {@link InterestData} entity.
 */
public interface InterestDataSearchRepository extends ElasticsearchRepository<InterestData, Long> {}
