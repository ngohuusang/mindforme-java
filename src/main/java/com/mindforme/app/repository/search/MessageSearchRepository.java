package com.mindforme.app.repository.search;

import com.mindforme.app.domain.Message;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the {@link Message} entity.
 */
public interface MessageSearchRepository extends ElasticsearchRepository<Message, Long> {}
