package com.mindforme.app.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class WalkingOtherMapperTest {
    private WalkingOtherMapper walkingOtherMapper;

    @BeforeEach
    public void setUp() {
        walkingOtherMapper = new WalkingOtherMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(walkingOtherMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(walkingOtherMapper.fromId(null)).isNull();
    }
}
