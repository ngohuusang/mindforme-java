package com.mindforme.app.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.mindforme.app.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

public class WalkingOtherDataDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(WalkingOtherDataDTO.class);
        WalkingOtherDataDTO walkingOtherDataDTO1 = new WalkingOtherDataDTO();
        walkingOtherDataDTO1.setId(1L);
        WalkingOtherDataDTO walkingOtherDataDTO2 = new WalkingOtherDataDTO();
        assertThat(walkingOtherDataDTO1).isNotEqualTo(walkingOtherDataDTO2);
        walkingOtherDataDTO2.setId(walkingOtherDataDTO1.getId());
        assertThat(walkingOtherDataDTO1).isEqualTo(walkingOtherDataDTO2);
        walkingOtherDataDTO2.setId(2L);
        assertThat(walkingOtherDataDTO1).isNotEqualTo(walkingOtherDataDTO2);
        walkingOtherDataDTO1.setId(null);
        assertThat(walkingOtherDataDTO1).isNotEqualTo(walkingOtherDataDTO2);
    }
}
