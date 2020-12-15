package com.mindforme.app.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.mindforme.app.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

public class HouseHelpRequestDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(HouseHelpRequestDTO.class);
        HouseHelpRequestDTO houseHelpRequestDTO1 = new HouseHelpRequestDTO();
        houseHelpRequestDTO1.setId(1L);
        HouseHelpRequestDTO houseHelpRequestDTO2 = new HouseHelpRequestDTO();
        assertThat(houseHelpRequestDTO1).isNotEqualTo(houseHelpRequestDTO2);
        houseHelpRequestDTO2.setId(houseHelpRequestDTO1.getId());
        assertThat(houseHelpRequestDTO1).isEqualTo(houseHelpRequestDTO2);
        houseHelpRequestDTO2.setId(2L);
        assertThat(houseHelpRequestDTO1).isNotEqualTo(houseHelpRequestDTO2);
        houseHelpRequestDTO1.setId(null);
        assertThat(houseHelpRequestDTO1).isNotEqualTo(houseHelpRequestDTO2);
    }
}
