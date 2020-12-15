package com.mindforme.app.repository.search;

import com.mindforme.app.domain.PlanData;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the {@link PlanData} entity.
 */
public interface PlanDataSearchRepository extends ElasticsearchRepository<PlanData, Long> {}
