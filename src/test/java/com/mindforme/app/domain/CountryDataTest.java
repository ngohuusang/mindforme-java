package com.mindforme.app.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.mindforme.app.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

public class CountryDataTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(CountryData.class);
        CountryData countryData1 = new CountryData();
        countryData1.setId(1L);
        CountryData countryData2 = new CountryData();
        countryData2.setId(countryData1.getId());
        assertThat(countryData1).isEqualTo(countryData2);
        countryData2.setId(2L);
        assertThat(countryData1).isNotEqualTo(countryData2);
        countryData1.setId(null);
        assertThat(countryData1).isNotEqualTo(countryData2);
    }
}
