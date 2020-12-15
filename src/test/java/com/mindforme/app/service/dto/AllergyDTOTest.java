package com.mindforme.app.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.mindforme.app.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

public class AllergyDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(AllergyDTO.class);
        AllergyDTO allergyDTO1 = new AllergyDTO();
        allergyDTO1.setId(1L);
        AllergyDTO allergyDTO2 = new AllergyDTO();
        assertThat(allergyDTO1).isNotEqualTo(allergyDTO2);
        allergyDTO2.setId(allergyDTO1.getId());
        assertThat(allergyDTO1).isEqualTo(allergyDTO2);
        allergyDTO2.setId(2L);
        assertThat(allergyDTO1).isNotEqualTo(allergyDTO2);
        allergyDTO1.setId(null);
        assertThat(allergyDTO1).isNotEqualTo(allergyDTO2);
    }
}
