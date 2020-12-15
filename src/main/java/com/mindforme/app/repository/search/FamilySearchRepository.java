package com.mindforme.app.repository.search;

import com.mindforme.app.domain.Family;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the {@link Family} entity.
 */
public interface FamilySearchRepository extends ElasticsearchRepository<Family, Long> {}
