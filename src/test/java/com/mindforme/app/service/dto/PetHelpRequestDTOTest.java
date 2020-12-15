package com.mindforme.app.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.mindforme.app.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

public class PetHelpRequestDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(PetHelpRequestDTO.class);
        PetHelpRequestDTO petHelpRequestDTO1 = new PetHelpRequestDTO();
        petHelpRequestDTO1.setId(1L);
        PetHelpRequestDTO petHelpRequestDTO2 = new PetHelpRequestDTO();
        assertThat(petHelpRequestDTO1).isNotEqualTo(petHelpRequestDTO2);
        petHelpRequestDTO2.setId(petHelpRequestDTO1.getId());
        assertThat(petHelpRequestDTO1).isEqualTo(petHelpRequestDTO2);
        petHelpRequestDTO2.setId(2L);
        assertThat(petHelpRequestDTO1).isNotEqualTo(petHelpRequestDTO2);
        petHelpRequestDTO1.setId(null);
        assertThat(petHelpRequestDTO1).isNotEqualTo(petHelpRequestDTO2);
    }
}
