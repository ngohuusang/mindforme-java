package com.mindforme.app.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.mindforme.app.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

public class AllergyTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Allergy.class);
        Allergy allergy1 = new Allergy();
        allergy1.setId(1L);
        Allergy allergy2 = new Allergy();
        allergy2.setId(allergy1.getId());
        assertThat(allergy1).isEqualTo(allergy2);
        allergy2.setId(2L);
        assertThat(allergy1).isNotEqualTo(allergy2);
        allergy1.setId(null);
        assertThat(allergy1).isNotEqualTo(allergy2);
    }
}
