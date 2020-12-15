package com.mindforme.app.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.mindforme.app.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

public class PlanDataTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(PlanData.class);
        PlanData planData1 = new PlanData();
        planData1.setId(1L);
        PlanData planData2 = new PlanData();
        planData2.setId(planData1.getId());
        assertThat(planData1).isEqualTo(planData2);
        planData2.setId(2L);
        assertThat(planData1).isNotEqualTo(planData2);
        planData1.setId(null);
        assertThat(planData1).isNotEqualTo(planData2);
    }
}
