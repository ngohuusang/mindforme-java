package com.mindforme.app.repository.search;

import com.mindforme.app.domain.Doctor;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the {@link Doctor} entity.
 */
public interface DoctorSearchRepository extends ElasticsearchRepository<Doctor, Long> {}
