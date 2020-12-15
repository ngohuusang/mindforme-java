package com.mindforme.app.repository.search;

import com.mindforme.app.domain.ChildRelationData;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the {@link ChildRelationData} entity.
 */
public interface ChildRelationDataSearchRepository extends ElasticsearchRepository<ChildRelationData, Long> {}
