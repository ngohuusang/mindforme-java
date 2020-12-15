package com.mindforme.app.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class PublicHolidayMapperTest {
    private PublicHolidayMapper publicHolidayMapper;

    @BeforeEach
    public void setUp() {
        publicHolidayMapper = new PublicHolidayMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(publicHolidayMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(publicHolidayMapper.fromId(null)).isNull();
    }
}
