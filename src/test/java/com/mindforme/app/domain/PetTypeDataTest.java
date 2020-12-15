package com.mindforme.app.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.mindforme.app.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

public class PetTypeDataTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(PetTypeData.class);
        PetTypeData petTypeData1 = new PetTypeData();
        petTypeData1.setId(1L);
        PetTypeData petTypeData2 = new PetTypeData();
        petTypeData2.setId(petTypeData1.getId());
        assertThat(petTypeData1).isEqualTo(petTypeData2);
        petTypeData2.setId(2L);
        assertThat(petTypeData1).isNotEqualTo(petTypeData2);
        petTypeData1.setId(null);
        assertThat(petTypeData1).isNotEqualTo(petTypeData2);
    }
}
