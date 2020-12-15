package com.mindforme.app.repository.search;

import com.mindforme.app.domain.ChildHelpRequest;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the {@link ChildHelpRequest} entity.
 */
public interface ChildHelpRequestSearchRepository extends ElasticsearchRepository<ChildHelpRequest, Long> {}
