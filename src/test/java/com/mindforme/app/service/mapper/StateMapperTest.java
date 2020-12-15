package com.mindforme.app.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class StateMapperTest {
    private StateMapper stateMapper;

    @BeforeEach
    public void setUp() {
        stateMapper = new StateMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(stateMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(stateMapper.fromId(null)).isNull();
    }
}
