package com.mindforme.app.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.mindforme.app.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

public class PlanDataDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(PlanDataDTO.class);
        PlanDataDTO planDataDTO1 = new PlanDataDTO();
        planDataDTO1.setId(1L);
        PlanDataDTO planDataDTO2 = new PlanDataDTO();
        assertThat(planDataDTO1).isNotEqualTo(planDataDTO2);
        planDataDTO2.setId(planDataDTO1.getId());
        assertThat(planDataDTO1).isEqualTo(planDataDTO2);
        planDataDTO2.setId(2L);
        assertThat(planDataDTO1).isNotEqualTo(planDataDTO2);
        planDataDTO1.setId(null);
        assertThat(planDataDTO1).isNotEqualTo(planDataDTO2);
    }
}
