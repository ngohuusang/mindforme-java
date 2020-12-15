package com.mindforme.app.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class UserIdentificationMapperTest {
    private UserIdentificationMapper userIdentificationMapper;

    @BeforeEach
    public void setUp() {
        userIdentificationMapper = new UserIdentificationMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(userIdentificationMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(userIdentificationMapper.fromId(null)).isNull();
    }
}
