package com.mindforme.app.repository.search;

import com.mindforme.app.domain.PublicHoliday;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the {@link PublicHoliday} entity.
 */
public interface PublicHolidaySearchRepository extends ElasticsearchRepository<PublicHoliday, Long> {}
