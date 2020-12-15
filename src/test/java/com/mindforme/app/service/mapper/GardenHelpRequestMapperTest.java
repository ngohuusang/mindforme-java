package com.mindforme.app.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class GardenHelpRequestMapperTest {
    private GardenHelpRequestMapper gardenHelpRequestMapper;

    @BeforeEach
    public void setUp() {
        gardenHelpRequestMapper = new GardenHelpRequestMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(gardenHelpRequestMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(gardenHelpRequestMapper.fromId(null)).isNull();
    }
}
