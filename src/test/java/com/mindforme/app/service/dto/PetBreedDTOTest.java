package com.mindforme.app.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.mindforme.app.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

public class PetBreedDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(PetBreedDTO.class);
        PetBreedDTO petBreedDTO1 = new PetBreedDTO();
        petBreedDTO1.setId(1L);
        PetBreedDTO petBreedDTO2 = new PetBreedDTO();
        assertThat(petBreedDTO1).isNotEqualTo(petBreedDTO2);
        petBreedDTO2.setId(petBreedDTO1.getId());
        assertThat(petBreedDTO1).isEqualTo(petBreedDTO2);
        petBreedDTO2.setId(2L);
        assertThat(petBreedDTO1).isNotEqualTo(petBreedDTO2);
        petBreedDTO1.setId(null);
        assertThat(petBreedDTO1).isNotEqualTo(petBreedDTO2);
    }
}
