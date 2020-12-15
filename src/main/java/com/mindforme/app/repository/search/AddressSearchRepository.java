package com.mindforme.app.repository.search;

import com.mindforme.app.domain.Address;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the {@link Address} entity.
 */
public interface AddressSearchRepository extends ElasticsearchRepository<Address, Long> {}
