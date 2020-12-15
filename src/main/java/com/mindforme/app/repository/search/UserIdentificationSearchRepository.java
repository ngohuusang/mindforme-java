package com.mindforme.app.repository.search;

import com.mindforme.app.domain.UserIdentification;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the {@link UserIdentification} entity.
 */
public interface UserIdentificationSearchRepository extends ElasticsearchRepository<UserIdentification, Long> {}
