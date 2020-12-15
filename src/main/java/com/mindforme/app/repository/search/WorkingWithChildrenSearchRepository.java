package com.mindforme.app.repository.search;

import com.mindforme.app.domain.WorkingWithChildren;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the {@link WorkingWithChildren} entity.
 */
public interface WorkingWithChildrenSearchRepository extends ElasticsearchRepository<WorkingWithChildren, Long> {}
