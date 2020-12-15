package com.mindforme.app.repository.search;

import com.mindforme.app.domain.RuleData;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the {@link RuleData} entity.
 */
public interface RuleDataSearchRepository extends ElasticsearchRepository<RuleData, Long> {}
