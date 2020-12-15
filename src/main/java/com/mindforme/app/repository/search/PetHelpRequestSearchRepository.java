package com.mindforme.app.repository.search;

import com.mindforme.app.domain.PetHelpRequest;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the {@link PetHelpRequest} entity.
 */
public interface PetHelpRequestSearchRepository extends ElasticsearchRepository<PetHelpRequest, Long> {}
