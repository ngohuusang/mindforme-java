package com.mindforme.app.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.mindforme.app.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

public class CountryDataDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(CountryDataDTO.class);
        CountryDataDTO countryDataDTO1 = new CountryDataDTO();
        countryDataDTO1.setId(1L);
        CountryDataDTO countryDataDTO2 = new CountryDataDTO();
        assertThat(countryDataDTO1).isNotEqualTo(countryDataDTO2);
        countryDataDTO2.setId(countryDataDTO1.getId());
        assertThat(countryDataDTO1).isEqualTo(countryDataDTO2);
        countryDataDTO2.setId(2L);
        assertThat(countryDataDTO1).isNotEqualTo(countryDataDTO2);
        countryDataDTO1.setId(null);
        assertThat(countryDataDTO1).isNotEqualTo(countryDataDTO2);
    }
}
