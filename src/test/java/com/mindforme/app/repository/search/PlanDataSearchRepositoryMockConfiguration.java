package com.mindforme.app.repository.search;

import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Configuration;

/**
 * Configure a Mock version of {@link PlanDataSearchRepository} to test the
 * application without starting Elasticsearch.
 */
@Configuration
public class PlanDataSearchRepositoryMockConfiguration {
    @MockBean
    private PlanDataSearchRepository mockPlanDataSearchRepository;
}
