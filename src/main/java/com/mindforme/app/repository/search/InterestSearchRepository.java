package com.mindforme.app.repository.search;

import com.mindforme.app.domain.Interest;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the {@link Interest} entity.
 */
public interface InterestSearchRepository extends ElasticsearchRepository<Interest, Long> {}
