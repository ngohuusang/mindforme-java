package com.mindforme.app.repository.search;

import com.mindforme.app.domain.CountryData;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the {@link CountryData} entity.
 */
public interface CountryDataSearchRepository extends ElasticsearchRepository<CountryData, Long> {}
