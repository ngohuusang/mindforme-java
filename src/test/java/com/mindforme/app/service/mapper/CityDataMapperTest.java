package com.mindforme.app.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class CityDataMapperTest {
    private CityDataMapper cityDataMapper;

    @BeforeEach
    public void setUp() {
        cityDataMapper = new CityDataMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(cityDataMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(cityDataMapper.fromId(null)).isNull();
    }
}
