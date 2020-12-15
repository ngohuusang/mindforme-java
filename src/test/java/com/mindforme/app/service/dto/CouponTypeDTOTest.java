package com.mindforme.app.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.mindforme.app.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

public class CouponTypeDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(CouponTypeDTO.class);
        CouponTypeDTO couponTypeDTO1 = new CouponTypeDTO();
        couponTypeDTO1.setId(1L);
        CouponTypeDTO couponTypeDTO2 = new CouponTypeDTO();
        assertThat(couponTypeDTO1).isNotEqualTo(couponTypeDTO2);
        couponTypeDTO2.setId(couponTypeDTO1.getId());
        assertThat(couponTypeDTO1).isEqualTo(couponTypeDTO2);
        couponTypeDTO2.setId(2L);
        assertThat(couponTypeDTO1).isNotEqualTo(couponTypeDTO2);
        couponTypeDTO1.setId(null);
        assertThat(couponTypeDTO1).isNotEqualTo(couponTypeDTO2);
    }
}
