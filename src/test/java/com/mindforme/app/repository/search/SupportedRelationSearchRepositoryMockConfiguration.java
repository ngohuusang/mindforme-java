package com.mindforme.app.repository.search;

import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Configuration;

/**
 * Configure a Mock version of {@link SupportedRelationSearchRepository} to test the
 * application without starting Elasticsearch.
 */
@Configuration
public class SupportedRelationSearchRepositoryMockConfiguration {
    @MockBean
    private SupportedRelationSearchRepository mockSupportedRelationSearchRepository;
}
