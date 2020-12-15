package com.mindforme.app.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class FamilyMapperTest {
    private FamilyMapper familyMapper;

    @BeforeEach
    public void setUp() {
        familyMapper = new FamilyMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(familyMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(familyMapper.fromId(null)).isNull();
    }
}
