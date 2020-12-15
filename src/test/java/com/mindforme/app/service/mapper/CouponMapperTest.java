package com.mindforme.app.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class CouponMapperTest {
    private CouponMapper couponMapper;

    @BeforeEach
    public void setUp() {
        couponMapper = new CouponMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(couponMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(couponMapper.fromId(null)).isNull();
    }
}
