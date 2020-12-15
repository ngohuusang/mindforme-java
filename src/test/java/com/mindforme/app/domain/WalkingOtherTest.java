package com.mindforme.app.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.mindforme.app.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

public class WalkingOtherTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(WalkingOther.class);
        WalkingOther walkingOther1 = new WalkingOther();
        walkingOther1.setId(1L);
        WalkingOther walkingOther2 = new WalkingOther();
        walkingOther2.setId(walkingOther1.getId());
        assertThat(walkingOther1).isEqualTo(walkingOther2);
        walkingOther2.setId(2L);
        assertThat(walkingOther1).isNotEqualTo(walkingOther2);
        walkingOther1.setId(null);
        assertThat(walkingOther1).isNotEqualTo(walkingOther2);
    }
}
