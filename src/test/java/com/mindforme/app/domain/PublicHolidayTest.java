package com.mindforme.app.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.mindforme.app.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

public class PublicHolidayTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(PublicHoliday.class);
        PublicHoliday publicHoliday1 = new PublicHoliday();
        publicHoliday1.setId(1L);
        PublicHoliday publicHoliday2 = new PublicHoliday();
        publicHoliday2.setId(publicHoliday1.getId());
        assertThat(publicHoliday1).isEqualTo(publicHoliday2);
        publicHoliday2.setId(2L);
        assertThat(publicHoliday1).isNotEqualTo(publicHoliday2);
        publicHoliday1.setId(null);
        assertThat(publicHoliday1).isNotEqualTo(publicHoliday2);
    }
}
