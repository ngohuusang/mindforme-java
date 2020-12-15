package com.mindforme.app.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.mindforme.app.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

public class StateDataDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(StateDataDTO.class);
        StateDataDTO stateDataDTO1 = new StateDataDTO();
        stateDataDTO1.setId(1L);
        StateDataDTO stateDataDTO2 = new StateDataDTO();
        assertThat(stateDataDTO1).isNotEqualTo(stateDataDTO2);
        stateDataDTO2.setId(stateDataDTO1.getId());
        assertThat(stateDataDTO1).isEqualTo(stateDataDTO2);
        stateDataDTO2.setId(2L);
        assertThat(stateDataDTO1).isNotEqualTo(stateDataDTO2);
        stateDataDTO1.setId(null);
        assertThat(stateDataDTO1).isNotEqualTo(stateDataDTO2);
    }
}
