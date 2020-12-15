package com.mindforme.app.repository.search;

import com.mindforme.app.domain.City;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the {@link City} entity.
 */
public interface CitySearchRepository extends ElasticsearchRepository<City, Long> {}
