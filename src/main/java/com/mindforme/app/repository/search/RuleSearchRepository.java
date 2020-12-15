package com.mindforme.app.repository.search;

import com.mindforme.app.domain.Rule;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the {@link Rule} entity.
 */
public interface RuleSearchRepository extends ElasticsearchRepository<Rule, Long> {}
