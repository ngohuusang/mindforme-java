package com.mindforme.app.repository.search;

import com.mindforme.app.domain.WalkingOtherData;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the {@link WalkingOtherData} entity.
 */
public interface WalkingOtherDataSearchRepository extends ElasticsearchRepository<WalkingOtherData, Long> {}
