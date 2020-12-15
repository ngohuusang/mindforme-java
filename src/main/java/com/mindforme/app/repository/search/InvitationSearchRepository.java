package com.mindforme.app.repository.search;

import com.mindforme.app.domain.Invitation;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the {@link Invitation} entity.
 */
public interface InvitationSearchRepository extends ElasticsearchRepository<Invitation, Long> {}
