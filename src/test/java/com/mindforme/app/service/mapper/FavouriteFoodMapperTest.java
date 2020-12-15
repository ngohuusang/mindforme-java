package com.mindforme.app.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class FavouriteFoodMapperTest {
    private FavouriteFoodMapper favouriteFoodMapper;

    @BeforeEach
    public void setUp() {
        favouriteFoodMapper = new FavouriteFoodMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(favouriteFoodMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(favouriteFoodMapper.fromId(null)).isNull();
    }
}
