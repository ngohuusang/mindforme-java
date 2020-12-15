package com.mindforme.app.repository.search;

import com.mindforme.app.domain.SupportedRelationData;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the {@link SupportedRelationData} entity.
 */
public interface SupportedRelationDataSearchRepository extends ElasticsearchRepository<SupportedRelationData, Long> {}
