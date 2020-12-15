package com.mindforme.app.repository.search;

import com.mindforme.app.domain.FriendshipGroup;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the {@link FriendshipGroup} entity.
 */
public interface FriendshipGroupSearchRepository extends ElasticsearchRepository<FriendshipGroup, Long> {}
