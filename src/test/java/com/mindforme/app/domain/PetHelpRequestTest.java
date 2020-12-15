package com.mindforme.app.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.mindforme.app.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

public class PetHelpRequestTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(PetHelpRequest.class);
        PetHelpRequest petHelpRequest1 = new PetHelpRequest();
        petHelpRequest1.setId(1L);
        PetHelpRequest petHelpRequest2 = new PetHelpRequest();
        petHelpRequest2.setId(petHelpRequest1.getId());
        assertThat(petHelpRequest1).isEqualTo(petHelpRequest2);
        petHelpRequest2.setId(2L);
        assertThat(petHelpRequest1).isNotEqualTo(petHelpRequest2);
        petHelpRequest1.setId(null);
        assertThat(petHelpRequest1).isNotEqualTo(petHelpRequest2);
    }
}
