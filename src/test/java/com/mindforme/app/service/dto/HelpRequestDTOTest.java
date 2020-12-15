package com.mindforme.app.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.mindforme.app.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

public class HelpRequestDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(HelpRequestDTO.class);
        HelpRequestDTO helpRequestDTO1 = new HelpRequestDTO();
        helpRequestDTO1.setId(1L);
        HelpRequestDTO helpRequestDTO2 = new HelpRequestDTO();
        assertThat(helpRequestDTO1).isNotEqualTo(helpRequestDTO2);
        helpRequestDTO2.setId(helpRequestDTO1.getId());
        assertThat(helpRequestDTO1).isEqualTo(helpRequestDTO2);
        helpRequestDTO2.setId(2L);
        assertThat(helpRequestDTO1).isNotEqualTo(helpRequestDTO2);
        helpRequestDTO1.setId(null);
        assertThat(helpRequestDTO1).isNotEqualTo(helpRequestDTO2);
    }
}
