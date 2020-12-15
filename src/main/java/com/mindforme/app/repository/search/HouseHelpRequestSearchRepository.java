package com.mindforme.app.repository.search;

import com.mindforme.app.domain.HouseHelpRequest;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the {@link HouseHelpRequest} entity.
 */
public interface HouseHelpRequestSearchRepository extends ElasticsearchRepository<HouseHelpRequest, Long> {}
