package com.mindforme.app.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class AllergyMapperTest {
    private AllergyMapper allergyMapper;

    @BeforeEach
    public void setUp() {
        allergyMapper = new AllergyMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(allergyMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(allergyMapper.fromId(null)).isNull();
    }
}
