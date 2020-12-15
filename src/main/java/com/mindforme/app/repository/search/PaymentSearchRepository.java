package com.mindforme.app.repository.search;

import com.mindforme.app.domain.Payment;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the {@link Payment} entity.
 */
public interface PaymentSearchRepository extends ElasticsearchRepository<Payment, Long> {}
