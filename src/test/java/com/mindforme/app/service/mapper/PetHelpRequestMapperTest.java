package com.mindforme.app.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class PetHelpRequestMapperTest {
    private PetHelpRequestMapper petHelpRequestMapper;

    @BeforeEach
    public void setUp() {
        petHelpRequestMapper = new PetHelpRequestMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(petHelpRequestMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(petHelpRequestMapper.fromId(null)).isNull();
    }
}
