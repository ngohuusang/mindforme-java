package com.mindforme.app.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.mindforme.app.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

public class PetBreedDataTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(PetBreedData.class);
        PetBreedData petBreedData1 = new PetBreedData();
        petBreedData1.setId(1L);
        PetBreedData petBreedData2 = new PetBreedData();
        petBreedData2.setId(petBreedData1.getId());
        assertThat(petBreedData1).isEqualTo(petBreedData2);
        petBreedData2.setId(2L);
        assertThat(petBreedData1).isNotEqualTo(petBreedData2);
        petBreedData1.setId(null);
        assertThat(petBreedData1).isNotEqualTo(petBreedData2);
    }
}
