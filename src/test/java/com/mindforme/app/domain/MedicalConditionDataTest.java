package com.mindforme.app.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.mindforme.app.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

public class MedicalConditionDataTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(MedicalConditionData.class);
        MedicalConditionData medicalConditionData1 = new MedicalConditionData();
        medicalConditionData1.setId(1L);
        MedicalConditionData medicalConditionData2 = new MedicalConditionData();
        medicalConditionData2.setId(medicalConditionData1.getId());
        assertThat(medicalConditionData1).isEqualTo(medicalConditionData2);
        medicalConditionData2.setId(2L);
        assertThat(medicalConditionData1).isNotEqualTo(medicalConditionData2);
        medicalConditionData1.setId(null);
        assertThat(medicalConditionData1).isNotEqualTo(medicalConditionData2);
    }
}
