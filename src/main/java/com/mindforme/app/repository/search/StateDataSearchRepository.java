package com.mindforme.app.repository.search;

import com.mindforme.app.domain.StateData;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the {@link StateData} entity.
 */
public interface StateDataSearchRepository extends ElasticsearchRepository<StateData, Long> {}
