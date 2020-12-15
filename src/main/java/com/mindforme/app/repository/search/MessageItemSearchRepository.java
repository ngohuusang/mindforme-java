package com.mindforme.app.repository.search;

import com.mindforme.app.domain.MessageItem;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the {@link MessageItem} entity.
 */
public interface MessageItemSearchRepository extends ElasticsearchRepository<MessageItem, Long> {}
