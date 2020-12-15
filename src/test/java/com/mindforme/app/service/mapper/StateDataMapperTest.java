package com.mindforme.app.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class StateDataMapperTest {
    private StateDataMapper stateDataMapper;

    @BeforeEach
    public void setUp() {
        stateDataMapper = new StateDataMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(stateDataMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(stateDataMapper.fromId(null)).isNull();
    }
}
