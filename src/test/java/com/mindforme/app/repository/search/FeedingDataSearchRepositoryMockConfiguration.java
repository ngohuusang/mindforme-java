package com.mindforme.app.repository.search;

import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Configuration;

/**
 * Configure a Mock version of {@link FeedingDataSearchRepository} to test the
 * application without starting Elasticsearch.
 */
@Configuration
public class FeedingDataSearchRepositoryMockConfiguration {
    @MockBean
    private FeedingDataSearchRepository mockFeedingDataSearchRepository;
}
