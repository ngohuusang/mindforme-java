package com.mindforme.app.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.mindforme.app.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

public class FavouriteFoodDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(FavouriteFoodDTO.class);
        FavouriteFoodDTO favouriteFoodDTO1 = new FavouriteFoodDTO();
        favouriteFoodDTO1.setId(1L);
        FavouriteFoodDTO favouriteFoodDTO2 = new FavouriteFoodDTO();
        assertThat(favouriteFoodDTO1).isNotEqualTo(favouriteFoodDTO2);
        favouriteFoodDTO2.setId(favouriteFoodDTO1.getId());
        assertThat(favouriteFoodDTO1).isEqualTo(favouriteFoodDTO2);
        favouriteFoodDTO2.setId(2L);
        assertThat(favouriteFoodDTO1).isNotEqualTo(favouriteFoodDTO2);
        favouriteFoodDTO1.setId(null);
        assertThat(favouriteFoodDTO1).isNotEqualTo(favouriteFoodDTO2);
    }
}
