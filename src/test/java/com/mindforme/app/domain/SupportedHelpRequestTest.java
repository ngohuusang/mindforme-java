package com.mindforme.app.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.mindforme.app.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

public class SupportedHelpRequestTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(SupportedHelpRequest.class);
        SupportedHelpRequest supportedHelpRequest1 = new SupportedHelpRequest();
        supportedHelpRequest1.setId(1L);
        SupportedHelpRequest supportedHelpRequest2 = new SupportedHelpRequest();
        supportedHelpRequest2.setId(supportedHelpRequest1.getId());
        assertThat(supportedHelpRequest1).isEqualTo(supportedHelpRequest2);
        supportedHelpRequest2.setId(2L);
        assertThat(supportedHelpRequest1).isNotEqualTo(supportedHelpRequest2);
        supportedHelpRequest1.setId(null);
        assertThat(supportedHelpRequest1).isNotEqualTo(supportedHelpRequest2);
    }
}
