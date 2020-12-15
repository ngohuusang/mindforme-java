package com.mindforme.app.repository.search;

import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Configuration;

/**
 * Configure a Mock version of {@link SupportedSearchRepository} to test the
 * application without starting Elasticsearch.
 */
@Configuration
public class SupportedSearchRepositoryMockConfiguration {
    @MockBean
    private SupportedSearchRepository mockSupportedSearchRepository;
}
