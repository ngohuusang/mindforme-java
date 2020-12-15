package com.mindforme.app.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.mindforme.app.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

public class ChildHelpRequestTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ChildHelpRequest.class);
        ChildHelpRequest childHelpRequest1 = new ChildHelpRequest();
        childHelpRequest1.setId(1L);
        ChildHelpRequest childHelpRequest2 = new ChildHelpRequest();
        childHelpRequest2.setId(childHelpRequest1.getId());
        assertThat(childHelpRequest1).isEqualTo(childHelpRequest2);
        childHelpRequest2.setId(2L);
        assertThat(childHelpRequest1).isNotEqualTo(childHelpRequest2);
        childHelpRequest1.setId(null);
        assertThat(childHelpRequest1).isNotEqualTo(childHelpRequest2);
    }
}
