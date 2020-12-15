package com.mindforme.app.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.mindforme.app.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

public class MedicalConditionDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(MedicalConditionDTO.class);
        MedicalConditionDTO medicalConditionDTO1 = new MedicalConditionDTO();
        medicalConditionDTO1.setId(1L);
        MedicalConditionDTO medicalConditionDTO2 = new MedicalConditionDTO();
        assertThat(medicalConditionDTO1).isNotEqualTo(medicalConditionDTO2);
        medicalConditionDTO2.setId(medicalConditionDTO1.getId());
        assertThat(medicalConditionDTO1).isEqualTo(medicalConditionDTO2);
        medicalConditionDTO2.setId(2L);
        assertThat(medicalConditionDTO1).isNotEqualTo(medicalConditionDTO2);
        medicalConditionDTO1.setId(null);
        assertThat(medicalConditionDTO1).isNotEqualTo(medicalConditionDTO2);
    }
}
