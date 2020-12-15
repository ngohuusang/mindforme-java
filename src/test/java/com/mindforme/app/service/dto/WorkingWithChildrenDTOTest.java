package com.mindforme.app.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.mindforme.app.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

public class WorkingWithChildrenDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(WorkingWithChildrenDTO.class);
        WorkingWithChildrenDTO workingWithChildrenDTO1 = new WorkingWithChildrenDTO();
        workingWithChildrenDTO1.setId(1L);
        WorkingWithChildrenDTO workingWithChildrenDTO2 = new WorkingWithChildrenDTO();
        assertThat(workingWithChildrenDTO1).isNotEqualTo(workingWithChildrenDTO2);
        workingWithChildrenDTO2.setId(workingWithChildrenDTO1.getId());
        assertThat(workingWithChildrenDTO1).isEqualTo(workingWithChildrenDTO2);
        workingWithChildrenDTO2.setId(2L);
        assertThat(workingWithChildrenDTO1).isNotEqualTo(workingWithChildrenDTO2);
        workingWithChildrenDTO1.setId(null);
        assertThat(workingWithChildrenDTO1).isNotEqualTo(workingWithChildrenDTO2);
    }
}
