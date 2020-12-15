package com.mindforme.app.repository.search;

import com.mindforme.app.domain.Friendship;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the {@link Friendship} entity.
 */
public interface FriendshipSearchRepository extends ElasticsearchRepository<Friendship, Long> {}
