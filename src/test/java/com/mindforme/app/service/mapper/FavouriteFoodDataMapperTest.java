package com.mindforme.app.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class FavouriteFoodDataMapperTest {
    private FavouriteFoodDataMapper favouriteFoodDataMapper;

    @BeforeEach
    public void setUp() {
        favouriteFoodDataMapper = new FavouriteFoodDataMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(favouriteFoodDataMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(favouriteFoodDataMapper.fromId(null)).isNull();
    }
}
