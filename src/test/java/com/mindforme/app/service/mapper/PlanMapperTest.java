package com.mindforme.app.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class PlanMapperTest {
    private PlanMapper planMapper;

    @BeforeEach
    public void setUp() {
        planMapper = new PlanMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(planMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(planMapper.fromId(null)).isNull();
    }
}
