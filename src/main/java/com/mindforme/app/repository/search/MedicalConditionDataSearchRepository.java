package com.mindforme.app.repository.search;

import com.mindforme.app.domain.MedicalConditionData;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the {@link MedicalConditionData} entity.
 */
public interface MedicalConditionDataSearchRepository extends ElasticsearchRepository<MedicalConditionData, Long> {}
