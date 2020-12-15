package com.mindforme.app.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class InterestDataMapperTest {
    private InterestDataMapper interestDataMapper;

    @BeforeEach
    public void setUp() {
        interestDataMapper = new InterestDataMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(interestDataMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(interestDataMapper.fromId(null)).isNull();
    }
}
