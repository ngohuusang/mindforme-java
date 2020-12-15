package com.mindforme.app.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.mindforme.app.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

public class FavouriteFoodDataTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(FavouriteFoodData.class);
        FavouriteFoodData favouriteFoodData1 = new FavouriteFoodData();
        favouriteFoodData1.setId(1L);
        FavouriteFoodData favouriteFoodData2 = new FavouriteFoodData();
        favouriteFoodData2.setId(favouriteFoodData1.getId());
        assertThat(favouriteFoodData1).isEqualTo(favouriteFoodData2);
        favouriteFoodData2.setId(2L);
        assertThat(favouriteFoodData1).isNotEqualTo(favouriteFoodData2);
        favouriteFoodData1.setId(null);
        assertThat(favouriteFoodData1).isNotEqualTo(favouriteFoodData2);
    }
}
