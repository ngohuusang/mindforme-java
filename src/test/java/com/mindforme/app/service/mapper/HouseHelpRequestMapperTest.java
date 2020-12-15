package com.mindforme.app.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class HouseHelpRequestMapperTest {
    private HouseHelpRequestMapper houseHelpRequestMapper;

    @BeforeEach
    public void setUp() {
        houseHelpRequestMapper = new HouseHelpRequestMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(houseHelpRequestMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(houseHelpRequestMapper.fromId(null)).isNull();
    }
}
