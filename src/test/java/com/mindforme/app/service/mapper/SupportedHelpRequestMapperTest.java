package com.mindforme.app.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class SupportedHelpRequestMapperTest {
    private SupportedHelpRequestMapper supportedHelpRequestMapper;

    @BeforeEach
    public void setUp() {
        supportedHelpRequestMapper = new SupportedHelpRequestMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(supportedHelpRequestMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(supportedHelpRequestMapper.fromId(null)).isNull();
    }
}
