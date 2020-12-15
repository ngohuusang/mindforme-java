package com.mindforme.app.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.mindforme.app.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

public class EmergencyContactDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(EmergencyContactDTO.class);
        EmergencyContactDTO emergencyContactDTO1 = new EmergencyContactDTO();
        emergencyContactDTO1.setId(1L);
        EmergencyContactDTO emergencyContactDTO2 = new EmergencyContactDTO();
        assertThat(emergencyContactDTO1).isNotEqualTo(emergencyContactDTO2);
        emergencyContactDTO2.setId(emergencyContactDTO1.getId());
        assertThat(emergencyContactDTO1).isEqualTo(emergencyContactDTO2);
        emergencyContactDTO2.setId(2L);
        assertThat(emergencyContactDTO1).isNotEqualTo(emergencyContactDTO2);
        emergencyContactDTO1.setId(null);
        assertThat(emergencyContactDTO1).isNotEqualTo(emergencyContactDTO2);
    }
}
