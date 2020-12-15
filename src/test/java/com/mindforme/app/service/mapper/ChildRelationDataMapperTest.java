package com.mindforme.app.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class ChildRelationDataMapperTest {
    private ChildRelationDataMapper childRelationDataMapper;

    @BeforeEach
    public void setUp() {
        childRelationDataMapper = new ChildRelationDataMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(childRelationDataMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(childRelationDataMapper.fromId(null)).isNull();
    }
}
