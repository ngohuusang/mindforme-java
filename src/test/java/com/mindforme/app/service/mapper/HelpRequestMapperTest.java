package com.mindforme.app.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class HelpRequestMapperTest {
    private HelpRequestMapper helpRequestMapper;

    @BeforeEach
    public void setUp() {
        helpRequestMapper = new HelpRequestMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(helpRequestMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(helpRequestMapper.fromId(null)).isNull();
    }
}
