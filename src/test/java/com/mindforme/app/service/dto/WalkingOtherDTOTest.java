package com.mindforme.app.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.mindforme.app.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

public class WalkingOtherDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(WalkingOtherDTO.class);
        WalkingOtherDTO walkingOtherDTO1 = new WalkingOtherDTO();
        walkingOtherDTO1.setId(1L);
        WalkingOtherDTO walkingOtherDTO2 = new WalkingOtherDTO();
        assertThat(walkingOtherDTO1).isNotEqualTo(walkingOtherDTO2);
        walkingOtherDTO2.setId(walkingOtherDTO1.getId());
        assertThat(walkingOtherDTO1).isEqualTo(walkingOtherDTO2);
        walkingOtherDTO2.setId(2L);
        assertThat(walkingOtherDTO1).isNotEqualTo(walkingOtherDTO2);
        walkingOtherDTO1.setId(null);
        assertThat(walkingOtherDTO1).isNotEqualTo(walkingOtherDTO2);
    }
}
