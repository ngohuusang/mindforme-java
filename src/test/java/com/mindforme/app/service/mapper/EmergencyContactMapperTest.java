package com.mindforme.app.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class EmergencyContactMapperTest {
    private EmergencyContactMapper emergencyContactMapper;

    @BeforeEach
    public void setUp() {
        emergencyContactMapper = new EmergencyContactMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(emergencyContactMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(emergencyContactMapper.fromId(null)).isNull();
    }
}
