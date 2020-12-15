package com.mindforme.app.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class CountryDataMapperTest {
    private CountryDataMapper countryDataMapper;

    @BeforeEach
    public void setUp() {
        countryDataMapper = new CountryDataMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(countryDataMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(countryDataMapper.fromId(null)).isNull();
    }
}
