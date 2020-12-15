package com.mindforme.app.repository.search;

import com.mindforme.app.domain.MedicalCondition;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the {@link MedicalCondition} entity.
 */
public interface MedicalConditionSearchRepository extends ElasticsearchRepository<MedicalCondition, Long> {}
