package com.mindforme.app.repository.search;

import com.mindforme.app.domain.Plan;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the {@link Plan} entity.
 */
public interface PlanSearchRepository extends ElasticsearchRepository<Plan, Long> {}
