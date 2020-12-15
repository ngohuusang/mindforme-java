package com.mindforme.app.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.mindforme.app.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

public class ChildHelpRequestDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ChildHelpRequestDTO.class);
        ChildHelpRequestDTO childHelpRequestDTO1 = new ChildHelpRequestDTO();
        childHelpRequestDTO1.setId(1L);
        ChildHelpRequestDTO childHelpRequestDTO2 = new ChildHelpRequestDTO();
        assertThat(childHelpRequestDTO1).isNotEqualTo(childHelpRequestDTO2);
        childHelpRequestDTO2.setId(childHelpRequestDTO1.getId());
        assertThat(childHelpRequestDTO1).isEqualTo(childHelpRequestDTO2);
        childHelpRequestDTO2.setId(2L);
        assertThat(childHelpRequestDTO1).isNotEqualTo(childHelpRequestDTO2);
        childHelpRequestDTO1.setId(null);
        assertThat(childHelpRequestDTO1).isNotEqualTo(childHelpRequestDTO2);
    }
}
