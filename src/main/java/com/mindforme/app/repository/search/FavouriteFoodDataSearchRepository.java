package com.mindforme.app.repository.search;

import com.mindforme.app.domain.FavouriteFoodData;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the {@link FavouriteFoodData} entity.
 */
public interface FavouriteFoodDataSearchRepository extends ElasticsearchRepository<FavouriteFoodData, Long> {}
