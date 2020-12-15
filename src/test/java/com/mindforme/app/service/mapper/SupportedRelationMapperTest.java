package com.mindforme.app.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class SupportedRelationMapperTest {
    private SupportedRelationMapper supportedRelationMapper;

    @BeforeEach
    public void setUp() {
        supportedRelationMapper = new SupportedRelationMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(supportedRelationMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(supportedRelationMapper.fromId(null)).isNull();
    }
}
