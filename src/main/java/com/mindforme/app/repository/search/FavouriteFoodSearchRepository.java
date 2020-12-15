package com.mindforme.app.repository.search;

import com.mindforme.app.domain.FavouriteFood;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the {@link FavouriteFood} entity.
 */
public interface FavouriteFoodSearchRepository extends ElasticsearchRepository<FavouriteFood, Long> {}
