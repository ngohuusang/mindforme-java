package com.mindforme.app.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.mindforme.app.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

public class SupportedRelationDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(SupportedRelationDTO.class);
        SupportedRelationDTO supportedRelationDTO1 = new SupportedRelationDTO();
        supportedRelationDTO1.setId(1L);
        SupportedRelationDTO supportedRelationDTO2 = new SupportedRelationDTO();
        assertThat(supportedRelationDTO1).isNotEqualTo(supportedRelationDTO2);
        supportedRelationDTO2.setId(supportedRelationDTO1.getId());
        assertThat(supportedRelationDTO1).isEqualTo(supportedRelationDTO2);
        supportedRelationDTO2.setId(2L);
        assertThat(supportedRelationDTO1).isNotEqualTo(supportedRelationDTO2);
        supportedRelationDTO1.setId(null);
        assertThat(supportedRelationDTO1).isNotEqualTo(supportedRelationDTO2);
    }
}
