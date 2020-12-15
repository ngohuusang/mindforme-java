package com.mindforme.app.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.mindforme.app.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

public class CouponTypeTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(CouponType.class);
        CouponType couponType1 = new CouponType();
        couponType1.setId(1L);
        CouponType couponType2 = new CouponType();
        couponType2.setId(couponType1.getId());
        assertThat(couponType1).isEqualTo(couponType2);
        couponType2.setId(2L);
        assertThat(couponType1).isNotEqualTo(couponType2);
        couponType1.setId(null);
        assertThat(couponType1).isNotEqualTo(couponType2);
    }
}
