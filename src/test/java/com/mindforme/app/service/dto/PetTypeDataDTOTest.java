package com.mindforme.app.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.mindforme.app.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

public class PetTypeDataDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(PetTypeDataDTO.class);
        PetTypeDataDTO petTypeDataDTO1 = new PetTypeDataDTO();
        petTypeDataDTO1.setId(1L);
        PetTypeDataDTO petTypeDataDTO2 = new PetTypeDataDTO();
        assertThat(petTypeDataDTO1).isNotEqualTo(petTypeDataDTO2);
        petTypeDataDTO2.setId(petTypeDataDTO1.getId());
        assertThat(petTypeDataDTO1).isEqualTo(petTypeDataDTO2);
        petTypeDataDTO2.setId(2L);
        assertThat(petTypeDataDTO1).isNotEqualTo(petTypeDataDTO2);
        petTypeDataDTO1.setId(null);
        assertThat(petTypeDataDTO1).isNotEqualTo(petTypeDataDTO2);
    }
}
