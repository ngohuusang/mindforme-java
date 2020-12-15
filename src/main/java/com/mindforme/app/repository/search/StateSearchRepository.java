package com.mindforme.app.repository.search;

import com.mindforme.app.domain.State;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the {@link State} entity.
 */
public interface StateSearchRepository extends ElasticsearchRepository<State, Long> {}
