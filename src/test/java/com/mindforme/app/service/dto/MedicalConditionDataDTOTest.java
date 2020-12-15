package com.mindforme.app.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.mindforme.app.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

public class MedicalConditionDataDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(MedicalConditionDataDTO.class);
        MedicalConditionDataDTO medicalConditionDataDTO1 = new MedicalConditionDataDTO();
        medicalConditionDataDTO1.setId(1L);
        MedicalConditionDataDTO medicalConditionDataDTO2 = new MedicalConditionDataDTO();
        assertThat(medicalConditionDataDTO1).isNotEqualTo(medicalConditionDataDTO2);
        medicalConditionDataDTO2.setId(medicalConditionDataDTO1.getId());
        assertThat(medicalConditionDataDTO1).isEqualTo(medicalConditionDataDTO2);
        medicalConditionDataDTO2.setId(2L);
        assertThat(medicalConditionDataDTO1).isNotEqualTo(medicalConditionDataDTO2);
        medicalConditionDataDTO1.setId(null);
        assertThat(medicalConditionDataDTO1).isNotEqualTo(medicalConditionDataDTO2);
    }
}
