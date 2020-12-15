package com.mindforme.app.repository.search;

import com.mindforme.app.domain.Feeding;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the {@link Feeding} entity.
 */
public interface FeedingSearchRepository extends ElasticsearchRepository<Feeding, Long> {}
