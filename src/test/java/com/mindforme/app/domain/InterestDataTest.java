package com.mindforme.app.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.mindforme.app.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

public class InterestDataTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(InterestData.class);
        InterestData interestData1 = new InterestData();
        interestData1.setId(1L);
        InterestData interestData2 = new InterestData();
        interestData2.setId(interestData1.getId());
        assertThat(interestData1).isEqualTo(interestData2);
        interestData2.setId(2L);
        assertThat(interestData1).isNotEqualTo(interestData2);
        interestData1.setId(null);
        assertThat(interestData1).isNotEqualTo(interestData2);
    }
}
