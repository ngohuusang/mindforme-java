package com.mindforme.app.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class PetBreedDataMapperTest {
    private PetBreedDataMapper petBreedDataMapper;

    @BeforeEach
    public void setUp() {
        petBreedDataMapper = new PetBreedDataMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(petBreedDataMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(petBreedDataMapper.fromId(null)).isNull();
    }
}
