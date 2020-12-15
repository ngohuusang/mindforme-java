package com.mindforme.app.repository.search;

import com.mindforme.app.domain.Notification;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the {@link Notification} entity.
 */
public interface NotificationSearchRepository extends ElasticsearchRepository<Notification, Long> {}
