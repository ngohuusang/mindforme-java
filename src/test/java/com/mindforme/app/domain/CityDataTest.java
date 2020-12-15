package com.mindforme.app.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.mindforme.app.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

public class CityDataTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(CityData.class);
        CityData cityData1 = new CityData();
        cityData1.setId(1L);
        CityData cityData2 = new CityData();
        cityData2.setId(cityData1.getId());
        assertThat(cityData1).isEqualTo(cityData2);
        cityData2.setId(2L);
        assertThat(cityData1).isNotEqualTo(cityData2);
        cityData1.setId(null);
        assertThat(cityData1).isNotEqualTo(cityData2);
    }
}
