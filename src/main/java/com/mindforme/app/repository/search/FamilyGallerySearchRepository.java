package com.mindforme.app.repository.search;

import com.mindforme.app.domain.FamilyGallery;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the {@link FamilyGallery} entity.
 */
public interface FamilyGallerySearchRepository extends ElasticsearchRepository<FamilyGallery, Long> {}
