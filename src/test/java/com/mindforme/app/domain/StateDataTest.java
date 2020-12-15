package com.mindforme.app.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.mindforme.app.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

public class StateDataTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(StateData.class);
        StateData stateData1 = new StateData();
        stateData1.setId(1L);
        StateData stateData2 = new StateData();
        stateData2.setId(stateData1.getId());
        assertThat(stateData1).isEqualTo(stateData2);
        stateData2.setId(2L);
        assertThat(stateData1).isNotEqualTo(stateData2);
        stateData1.setId(null);
        assertThat(stateData1).isNotEqualTo(stateData2);
    }
}
