package com.mindforme.app.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.mindforme.app.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

public class CityDataDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(CityDataDTO.class);
        CityDataDTO cityDataDTO1 = new CityDataDTO();
        cityDataDTO1.setId(1L);
        CityDataDTO cityDataDTO2 = new CityDataDTO();
        assertThat(cityDataDTO1).isNotEqualTo(cityDataDTO2);
        cityDataDTO2.setId(cityDataDTO1.getId());
        assertThat(cityDataDTO1).isEqualTo(cityDataDTO2);
        cityDataDTO2.setId(2L);
        assertThat(cityDataDTO1).isNotEqualTo(cityDataDTO2);
        cityDataDTO1.setId(null);
        assertThat(cityDataDTO1).isNotEqualTo(cityDataDTO2);
    }
}
