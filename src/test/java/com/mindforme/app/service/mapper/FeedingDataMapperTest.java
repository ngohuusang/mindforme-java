package com.mindforme.app.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class FeedingDataMapperTest {
    private FeedingDataMapper feedingDataMapper;

    @BeforeEach
    public void setUp() {
        feedingDataMapper = new FeedingDataMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(feedingDataMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(feedingDataMapper.fromId(null)).isNull();
    }
}
