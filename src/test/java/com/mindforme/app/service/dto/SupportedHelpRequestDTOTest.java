package com.mindforme.app.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.mindforme.app.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

public class SupportedHelpRequestDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(SupportedHelpRequestDTO.class);
        SupportedHelpRequestDTO supportedHelpRequestDTO1 = new SupportedHelpRequestDTO();
        supportedHelpRequestDTO1.setId(1L);
        SupportedHelpRequestDTO supportedHelpRequestDTO2 = new SupportedHelpRequestDTO();
        assertThat(supportedHelpRequestDTO1).isNotEqualTo(supportedHelpRequestDTO2);
        supportedHelpRequestDTO2.setId(supportedHelpRequestDTO1.getId());
        assertThat(supportedHelpRequestDTO1).isEqualTo(supportedHelpRequestDTO2);
        supportedHelpRequestDTO2.setId(2L);
        assertThat(supportedHelpRequestDTO1).isNotEqualTo(supportedHelpRequestDTO2);
        supportedHelpRequestDTO1.setId(null);
        assertThat(supportedHelpRequestDTO1).isNotEqualTo(supportedHelpRequestDTO2);
    }
}
