package com.mindforme.app.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class PlanDataMapperTest {
    private PlanDataMapper planDataMapper;

    @BeforeEach
    public void setUp() {
        planDataMapper = new PlanDataMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(planDataMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(planDataMapper.fromId(null)).isNull();
    }
}
