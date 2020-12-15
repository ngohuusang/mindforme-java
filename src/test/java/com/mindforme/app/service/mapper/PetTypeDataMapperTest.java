package com.mindforme.app.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class PetTypeDataMapperTest {
    private PetTypeDataMapper petTypeDataMapper;

    @BeforeEach
    public void setUp() {
        petTypeDataMapper = new PetTypeDataMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(petTypeDataMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(petTypeDataMapper.fromId(null)).isNull();
    }
}
