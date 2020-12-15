package com.mindforme.app.repository.search;

import com.mindforme.app.domain.EmergencyContact;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the {@link EmergencyContact} entity.
 */
public interface EmergencyContactSearchRepository extends ElasticsearchRepository<EmergencyContact, Long> {}
