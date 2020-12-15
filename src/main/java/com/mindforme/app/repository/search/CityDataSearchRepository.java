package com.mindforme.app.repository.search;

import com.mindforme.app.domain.CityData;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the {@link CityData} entity.
 */
public interface CityDataSearchRepository extends ElasticsearchRepository<CityData, Long> {}
