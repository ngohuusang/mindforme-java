package com.mindforme.app.repository.search;

import com.mindforme.app.domain.HelpRequest;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the {@link HelpRequest} entity.
 */
public interface HelpRequestSearchRepository extends ElasticsearchRepository<HelpRequest, Long> {}
