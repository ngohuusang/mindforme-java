package com.mindforme.app.repository.search;

import com.mindforme.app.domain.WalkingOther;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the {@link WalkingOther} entity.
 */
public interface WalkingOtherSearchRepository extends ElasticsearchRepository<WalkingOther, Long> {}
