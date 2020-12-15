package com.mindforme.app.repository.search;

import com.mindforme.app.domain.ChildRelation;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the {@link ChildRelation} entity.
 */
public interface ChildRelationSearchRepository extends ElasticsearchRepository<ChildRelation, Long> {}
