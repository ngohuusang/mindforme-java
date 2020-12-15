package com.mindforme.app.repository.search;

import com.mindforme.app.domain.FriendRequest;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the {@link FriendRequest} entity.
 */
public interface FriendRequestSearchRepository extends ElasticsearchRepository<FriendRequest, Long> {}
