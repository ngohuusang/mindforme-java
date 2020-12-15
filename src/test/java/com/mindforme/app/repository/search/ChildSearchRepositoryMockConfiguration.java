package com.mindforme.app.repository.search;

import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Configuration;

/**
 * Configure a Mock version of {@link ChildSearchRepository} to test the
 * application without starting Elasticsearch.
 */
@Configuration
public class ChildSearchRepositoryMockConfiguration {
    @MockBean
    private ChildSearchRepository mockChildSearchRepository;
}
