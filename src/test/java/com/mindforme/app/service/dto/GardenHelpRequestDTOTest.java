package com.mindforme.app.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.mindforme.app.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

public class GardenHelpRequestDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(GardenHelpRequestDTO.class);
        GardenHelpRequestDTO gardenHelpRequestDTO1 = new GardenHelpRequestDTO();
        gardenHelpRequestDTO1.setId(1L);
        GardenHelpRequestDTO gardenHelpRequestDTO2 = new GardenHelpRequestDTO();
        assertThat(gardenHelpRequestDTO1).isNotEqualTo(gardenHelpRequestDTO2);
        gardenHelpRequestDTO2.setId(gardenHelpRequestDTO1.getId());
        assertThat(gardenHelpRequestDTO1).isEqualTo(gardenHelpRequestDTO2);
        gardenHelpRequestDTO2.setId(2L);
        assertThat(gardenHelpRequestDTO1).isNotEqualTo(gardenHelpRequestDTO2);
        gardenHelpRequestDTO1.setId(null);
        assertThat(gardenHelpRequestDTO1).isNotEqualTo(gardenHelpRequestDTO2);
    }
}
