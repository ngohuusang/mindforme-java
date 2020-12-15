package com.mindforme.app.repository.search;

import com.mindforme.app.domain.GardenHelpRequest;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the {@link GardenHelpRequest} entity.
 */
public interface GardenHelpRequestSearchRepository extends ElasticsearchRepository<GardenHelpRequest, Long> {}
