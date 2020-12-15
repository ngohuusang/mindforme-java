package com.mindforme.app.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.mindforme.app.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

public class AllergyDataTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(AllergyData.class);
        AllergyData allergyData1 = new AllergyData();
        allergyData1.setId(1L);
        AllergyData allergyData2 = new AllergyData();
        allergyData2.setId(allergyData1.getId());
        assertThat(allergyData1).isEqualTo(allergyData2);
        allergyData2.setId(2L);
        assertThat(allergyData1).isNotEqualTo(allergyData2);
        allergyData1.setId(null);
        assertThat(allergyData1).isNotEqualTo(allergyData2);
    }
}
