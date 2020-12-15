package com.mindforme.app.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class LanguageMapperTest {
    private LanguageMapper languageMapper;

    @BeforeEach
    public void setUp() {
        languageMapper = new LanguageMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(languageMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(languageMapper.fromId(null)).isNull();
    }
}
