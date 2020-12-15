package com.mindforme.app.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.mindforme.app.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

public class EmergencyContactTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(EmergencyContact.class);
        EmergencyContact emergencyContact1 = new EmergencyContact();
        emergencyContact1.setId(1L);
        EmergencyContact emergencyContact2 = new EmergencyContact();
        emergencyContact2.setId(emergencyContact1.getId());
        assertThat(emergencyContact1).isEqualTo(emergencyContact2);
        emergencyContact2.setId(2L);
        assertThat(emergencyContact1).isNotEqualTo(emergencyContact2);
        emergencyContact1.setId(null);
        assertThat(emergencyContact1).isNotEqualTo(emergencyContact2);
    }
}
