package com.mindforme.app.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class PetBreedMapperTest {
    private PetBreedMapper petBreedMapper;

    @BeforeEach
    public void setUp() {
        petBreedMapper = new PetBreedMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(petBreedMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(petBreedMapper.fromId(null)).isNull();
    }
}
