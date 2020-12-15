package com.mindforme.app.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class ChildMapperTest {
    private ChildMapper childMapper;

    @BeforeEach
    public void setUp() {
        childMapper = new ChildMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(childMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(childMapper.fromId(null)).isNull();
    }
}
