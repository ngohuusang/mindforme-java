package com.mindforme.app.repository.search;

import com.mindforme.app.domain.FeedingData;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the {@link FeedingData} entity.
 */
public interface FeedingDataSearchRepository extends ElasticsearchRepository<FeedingData, Long> {}
