package com.mindforme.app.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.mindforme.app.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

public class WalkingOtherDataTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(WalkingOtherData.class);
        WalkingOtherData walkingOtherData1 = new WalkingOtherData();
        walkingOtherData1.setId(1L);
        WalkingOtherData walkingOtherData2 = new WalkingOtherData();
        walkingOtherData2.setId(walkingOtherData1.getId());
        assertThat(walkingOtherData1).isEqualTo(walkingOtherData2);
        walkingOtherData2.setId(2L);
        assertThat(walkingOtherData1).isNotEqualTo(walkingOtherData2);
        walkingOtherData1.setId(null);
        assertThat(walkingOtherData1).isNotEqualTo(walkingOtherData2);
    }
}
