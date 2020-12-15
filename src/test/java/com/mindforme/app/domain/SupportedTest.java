package com.mindforme.app.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.mindforme.app.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

public class SupportedTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Supported.class);
        Supported supported1 = new Supported();
        supported1.setId(1L);
        Supported supported2 = new Supported();
        supported2.setId(supported1.getId());
        assertThat(supported1).isEqualTo(supported2);
        supported2.setId(2L);
        assertThat(supported1).isNotEqualTo(supported2);
        supported1.setId(null);
        assertThat(supported1).isNotEqualTo(supported2);
    }
}
