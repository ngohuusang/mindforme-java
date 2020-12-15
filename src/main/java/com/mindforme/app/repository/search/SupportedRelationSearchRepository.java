package com.mindforme.app.repository.search;

import com.mindforme.app.domain.SupportedRelation;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the {@link SupportedRelation} entity.
 */
public interface SupportedRelationSearchRepository extends ElasticsearchRepository<SupportedRelation, Long> {}
