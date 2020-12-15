package com.mindforme.app.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class MedicalConditionDataMapperTest {
    private MedicalConditionDataMapper medicalConditionDataMapper;

    @BeforeEach
    public void setUp() {
        medicalConditionDataMapper = new MedicalConditionDataMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(medicalConditionDataMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(medicalConditionDataMapper.fromId(null)).isNull();
    }
}
