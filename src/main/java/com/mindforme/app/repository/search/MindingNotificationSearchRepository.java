package com.mindforme.app.repository.search;

import com.mindforme.app.domain.MindingNotification;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the {@link MindingNotification} entity.
 */
public interface MindingNotificationSearchRepository extends ElasticsearchRepository<MindingNotification, Long> {}
