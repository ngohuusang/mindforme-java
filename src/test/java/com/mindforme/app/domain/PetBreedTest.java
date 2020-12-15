package com.mindforme.app.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.mindforme.app.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

public class PetBreedTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(PetBreed.class);
        PetBreed petBreed1 = new PetBreed();
        petBreed1.setId(1L);
        PetBreed petBreed2 = new PetBreed();
        petBreed2.setId(petBreed1.getId());
        assertThat(petBreed1).isEqualTo(petBreed2);
        petBreed2.setId(2L);
        assertThat(petBreed1).isNotEqualTo(petBreed2);
        petBreed1.setId(null);
        assertThat(petBreed1).isNotEqualTo(petBreed2);
    }
}
