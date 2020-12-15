package com.mindforme.app.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.mindforme.app.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

public class ChildRelationDataDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ChildRelationDataDTO.class);
        ChildRelationDataDTO childRelationDataDTO1 = new ChildRelationDataDTO();
        childRelationDataDTO1.setId(1L);
        ChildRelationDataDTO childRelationDataDTO2 = new ChildRelationDataDTO();
        assertThat(childRelationDataDTO1).isNotEqualTo(childRelationDataDTO2);
        childRelationDataDTO2.setId(childRelationDataDTO1.getId());
        assertThat(childRelationDataDTO1).isEqualTo(childRelationDataDTO2);
        childRelationDataDTO2.setId(2L);
        assertThat(childRelationDataDTO1).isNotEqualTo(childRelationDataDTO2);
        childRelationDataDTO1.setId(null);
        assertThat(childRelationDataDTO1).isNotEqualTo(childRelationDataDTO2);
    }
}
