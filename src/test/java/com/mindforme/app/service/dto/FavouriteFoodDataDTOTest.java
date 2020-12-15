package com.mindforme.app.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.mindforme.app.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

public class FavouriteFoodDataDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(FavouriteFoodDataDTO.class);
        FavouriteFoodDataDTO favouriteFoodDataDTO1 = new FavouriteFoodDataDTO();
        favouriteFoodDataDTO1.setId(1L);
        FavouriteFoodDataDTO favouriteFoodDataDTO2 = new FavouriteFoodDataDTO();
        assertThat(favouriteFoodDataDTO1).isNotEqualTo(favouriteFoodDataDTO2);
        favouriteFoodDataDTO2.setId(favouriteFoodDataDTO1.getId());
        assertThat(favouriteFoodDataDTO1).isEqualTo(favouriteFoodDataDTO2);
        favouriteFoodDataDTO2.setId(2L);
        assertThat(favouriteFoodDataDTO1).isNotEqualTo(favouriteFoodDataDTO2);
        favouriteFoodDataDTO1.setId(null);
        assertThat(favouriteFoodDataDTO1).isNotEqualTo(favouriteFoodDataDTO2);
    }
}
