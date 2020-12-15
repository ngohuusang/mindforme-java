package com.mindforme.app.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.mindforme.app.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

public class FeedingTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Feeding.class);
        Feeding feeding1 = new Feeding();
        feeding1.setId(1L);
        Feeding feeding2 = new Feeding();
        feeding2.setId(feeding1.getId());
        assertThat(feeding1).isEqualTo(feeding2);
        feeding2.setId(2L);
        assertThat(feeding1).isNotEqualTo(feeding2);
        feeding1.setId(null);
        assertThat(feeding1).isNotEqualTo(feeding2);
    }
}
