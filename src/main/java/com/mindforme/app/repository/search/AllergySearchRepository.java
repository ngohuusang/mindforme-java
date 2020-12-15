package com.mindforme.app.repository.search;

import com.mindforme.app.domain.Allergy;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the {@link Allergy} entity.
 */
public interface AllergySearchRepository extends ElasticsearchRepository<Allergy, Long> {}
