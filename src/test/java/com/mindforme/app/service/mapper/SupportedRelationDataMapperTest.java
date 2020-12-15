package com.mindforme.app.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class SupportedRelationDataMapperTest {
    private SupportedRelationDataMapper supportedRelationDataMapper;

    @BeforeEach
    public void setUp() {
        supportedRelationDataMapper = new SupportedRelationDataMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(supportedRelationDataMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(supportedRelationDataMapper.fromId(null)).isNull();
    }
}
