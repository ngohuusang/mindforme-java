package com.mindforme.app.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.mindforme.app.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

public class SupportedRelationDataDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(SupportedRelationDataDTO.class);
        SupportedRelationDataDTO supportedRelationDataDTO1 = new SupportedRelationDataDTO();
        supportedRelationDataDTO1.setId(1L);
        SupportedRelationDataDTO supportedRelationDataDTO2 = new SupportedRelationDataDTO();
        assertThat(supportedRelationDataDTO1).isNotEqualTo(supportedRelationDataDTO2);
        supportedRelationDataDTO2.setId(supportedRelationDataDTO1.getId());
        assertThat(supportedRelationDataDTO1).isEqualTo(supportedRelationDataDTO2);
        supportedRelationDataDTO2.setId(2L);
        assertThat(supportedRelationDataDTO1).isNotEqualTo(supportedRelationDataDTO2);
        supportedRelationDataDTO1.setId(null);
        assertThat(supportedRelationDataDTO1).isNotEqualTo(supportedRelationDataDTO2);
    }
}
