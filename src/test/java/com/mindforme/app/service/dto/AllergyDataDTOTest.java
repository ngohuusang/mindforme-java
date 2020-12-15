package com.mindforme.app.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.mindforme.app.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

public class AllergyDataDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(AllergyDataDTO.class);
        AllergyDataDTO allergyDataDTO1 = new AllergyDataDTO();
        allergyDataDTO1.setId(1L);
        AllergyDataDTO allergyDataDTO2 = new AllergyDataDTO();
        assertThat(allergyDataDTO1).isNotEqualTo(allergyDataDTO2);
        allergyDataDTO2.setId(allergyDataDTO1.getId());
        assertThat(allergyDataDTO1).isEqualTo(allergyDataDTO2);
        allergyDataDTO2.setId(2L);
        assertThat(allergyDataDTO1).isNotEqualTo(allergyDataDTO2);
        allergyDataDTO1.setId(null);
        assertThat(allergyDataDTO1).isNotEqualTo(allergyDataDTO2);
    }
}
