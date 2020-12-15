package com.mindforme.app.repository.search;

import com.mindforme.app.domain.CouponType;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the {@link CouponType} entity.
 */
public interface CouponTypeSearchRepository extends ElasticsearchRepository<CouponType, Long> {}
