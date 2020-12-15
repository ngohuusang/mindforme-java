package com.mindforme.app.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.mindforme.app.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

public class ChildRelationDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ChildRelationDTO.class);
        ChildRelationDTO childRelationDTO1 = new ChildRelationDTO();
        childRelationDTO1.setId(1L);
        ChildRelationDTO childRelationDTO2 = new ChildRelationDTO();
        assertThat(childRelationDTO1).isNotEqualTo(childRelationDTO2);
        childRelationDTO2.setId(childRelationDTO1.getId());
        assertThat(childRelationDTO1).isEqualTo(childRelationDTO2);
        childRelationDTO2.setId(2L);
        assertThat(childRelationDTO1).isNotEqualTo(childRelationDTO2);
        childRelationDTO1.setId(null);
        assertThat(childRelationDTO1).isNotEqualTo(childRelationDTO2);
    }
}
