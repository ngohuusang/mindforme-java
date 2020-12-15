package com.mindforme.app.repository.search;

import com.mindforme.app.domain.Supported;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the {@link Supported} entity.
 */
public interface SupportedSearchRepository extends ElasticsearchRepository<Supported, Long> {}
