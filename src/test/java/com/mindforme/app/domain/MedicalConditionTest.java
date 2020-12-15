package com.mindforme.app.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.mindforme.app.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

public class MedicalConditionTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(MedicalCondition.class);
        MedicalCondition medicalCondition1 = new MedicalCondition();
        medicalCondition1.setId(1L);
        MedicalCondition medicalCondition2 = new MedicalCondition();
        medicalCondition2.setId(medicalCondition1.getId());
        assertThat(medicalCondition1).isEqualTo(medicalCondition2);
        medicalCondition2.setId(2L);
        assertThat(medicalCondition1).isNotEqualTo(medicalCondition2);
        medicalCondition1.setId(null);
        assertThat(medicalCondition1).isNotEqualTo(medicalCondition2);
    }
}
