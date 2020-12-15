package com.mindforme.app.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class AllergyDataMapperTest {
    private AllergyDataMapper allergyDataMapper;

    @BeforeEach
    public void setUp() {
        allergyDataMapper = new AllergyDataMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(allergyDataMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(allergyDataMapper.fromId(null)).isNull();
    }
}
