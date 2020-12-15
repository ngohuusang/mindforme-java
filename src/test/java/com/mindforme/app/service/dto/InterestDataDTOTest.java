package com.mindforme.app.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.mindforme.app.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

public class InterestDataDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(InterestDataDTO.class);
        InterestDataDTO interestDataDTO1 = new InterestDataDTO();
        interestDataDTO1.setId(1L);
        InterestDataDTO interestDataDTO2 = new InterestDataDTO();
        assertThat(interestDataDTO1).isNotEqualTo(interestDataDTO2);
        interestDataDTO2.setId(interestDataDTO1.getId());
        assertThat(interestDataDTO1).isEqualTo(interestDataDTO2);
        interestDataDTO2.setId(2L);
        assertThat(interestDataDTO1).isNotEqualTo(interestDataDTO2);
        interestDataDTO1.setId(null);
        assertThat(interestDataDTO1).isNotEqualTo(interestDataDTO2);
    }
}
