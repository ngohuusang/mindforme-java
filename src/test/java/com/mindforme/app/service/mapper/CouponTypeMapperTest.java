package com.mindforme.app.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class CouponTypeMapperTest {
    private CouponTypeMapper couponTypeMapper;

    @BeforeEach
    public void setUp() {
        couponTypeMapper = new CouponTypeMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(couponTypeMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(couponTypeMapper.fromId(null)).isNull();
    }
}
