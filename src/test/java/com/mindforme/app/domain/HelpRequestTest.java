package com.mindforme.app.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.mindforme.app.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

public class HelpRequestTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(HelpRequest.class);
        HelpRequest helpRequest1 = new HelpRequest();
        helpRequest1.setId(1L);
        HelpRequest helpRequest2 = new HelpRequest();
        helpRequest2.setId(helpRequest1.getId());
        assertThat(helpRequest1).isEqualTo(helpRequest2);
        helpRequest2.setId(2L);
        assertThat(helpRequest1).isNotEqualTo(helpRequest2);
        helpRequest1.setId(null);
        assertThat(helpRequest1).isNotEqualTo(helpRequest2);
    }
}
