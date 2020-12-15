package com.mindforme.app.repository.search;

import com.mindforme.app.domain.Language;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the {@link Language} entity.
 */
public interface LanguageSearchRepository extends ElasticsearchRepository<Language, Long> {}
