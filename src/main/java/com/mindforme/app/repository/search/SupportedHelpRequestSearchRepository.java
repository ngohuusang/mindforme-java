package com.mindforme.app.repository.search;

import com.mindforme.app.domain.SupportedHelpRequest;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the {@link SupportedHelpRequest} entity.
 */
public interface SupportedHelpRequestSearchRepository extends ElasticsearchRepository<SupportedHelpRequest, Long> {}
