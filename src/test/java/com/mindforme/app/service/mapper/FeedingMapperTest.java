package com.mindforme.app.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class FeedingMapperTest {
    private FeedingMapper feedingMapper;

    @BeforeEach
    public void setUp() {
        feedingMapper = new FeedingMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(feedingMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(feedingMapper.fromId(null)).isNull();
    }
}
