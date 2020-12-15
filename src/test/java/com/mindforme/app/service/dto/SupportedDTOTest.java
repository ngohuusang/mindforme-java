package com.mindforme.app.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.mindforme.app.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

public class SupportedDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(SupportedDTO.class);
        SupportedDTO supportedDTO1 = new SupportedDTO();
        supportedDTO1.setId(1L);
        SupportedDTO supportedDTO2 = new SupportedDTO();
        assertThat(supportedDTO1).isNotEqualTo(supportedDTO2);
        supportedDTO2.setId(supportedDTO1.getId());
        assertThat(supportedDTO1).isEqualTo(supportedDTO2);
        supportedDTO2.setId(2L);
        assertThat(supportedDTO1).isNotEqualTo(supportedDTO2);
        supportedDTO1.setId(null);
        assertThat(supportedDTO1).isNotEqualTo(supportedDTO2);
    }
}
