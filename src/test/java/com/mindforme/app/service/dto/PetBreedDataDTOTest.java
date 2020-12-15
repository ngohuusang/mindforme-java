package com.mindforme.app.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.mindforme.app.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

public class PetBreedDataDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(PetBreedDataDTO.class);
        PetBreedDataDTO petBreedDataDTO1 = new PetBreedDataDTO();
        petBreedDataDTO1.setId(1L);
        PetBreedDataDTO petBreedDataDTO2 = new PetBreedDataDTO();
        assertThat(petBreedDataDTO1).isNotEqualTo(petBreedDataDTO2);
        petBreedDataDTO2.setId(petBreedDataDTO1.getId());
        assertThat(petBreedDataDTO1).isEqualTo(petBreedDataDTO2);
        petBreedDataDTO2.setId(2L);
        assertThat(petBreedDataDTO1).isNotEqualTo(petBreedDataDTO2);
        petBreedDataDTO1.setId(null);
        assertThat(petBreedDataDTO1).isNotEqualTo(petBreedDataDTO2);
    }
}
