package com.mindforme.app.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class SupportedMapperTest {
    private SupportedMapper supportedMapper;

    @BeforeEach
    public void setUp() {
        supportedMapper = new SupportedMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(supportedMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(supportedMapper.fromId(null)).isNull();
    }
}
