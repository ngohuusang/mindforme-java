package com.mindforme.app.repository.search;

import com.mindforme.app.domain.Coupon;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the {@link Coupon} entity.
 */
public interface CouponSearchRepository extends ElasticsearchRepository<Coupon, Long> {}
