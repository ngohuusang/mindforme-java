package com.mindforme.app.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class ChildRelationMapperTest {
    private ChildRelationMapper childRelationMapper;

    @BeforeEach
    public void setUp() {
        childRelationMapper = new ChildRelationMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(childRelationMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(childRelationMapper.fromId(null)).isNull();
    }
}
