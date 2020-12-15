package com.mindforme.app.repository.search;

import com.mindforme.app.domain.Child;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the {@link Child} entity.
 */
public interface ChildSearchRepository extends ElasticsearchRepository<Child, Long> {}
