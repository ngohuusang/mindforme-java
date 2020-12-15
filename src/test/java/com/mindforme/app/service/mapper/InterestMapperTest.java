package com.mindforme.app.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class InterestMapperTest {
    private InterestMapper interestMapper;

    @BeforeEach
    public void setUp() {
        interestMapper = new InterestMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(interestMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(interestMapper.fromId(null)).isNull();
    }
}
