package com.mindforme.app.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class WalkingOtherDataMapperTest {
    private WalkingOtherDataMapper walkingOtherDataMapper;

    @BeforeEach
    public void setUp() {
        walkingOtherDataMapper = new WalkingOtherDataMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(walkingOtherDataMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(walkingOtherDataMapper.fromId(null)).isNull();
    }
}
