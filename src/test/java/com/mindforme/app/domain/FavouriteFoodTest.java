package com.mindforme.app.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.mindforme.app.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

public class FavouriteFoodTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(FavouriteFood.class);
        FavouriteFood favouriteFood1 = new FavouriteFood();
        favouriteFood1.setId(1L);
        FavouriteFood favouriteFood2 = new FavouriteFood();
        favouriteFood2.setId(favouriteFood1.getId());
        assertThat(favouriteFood1).isEqualTo(favouriteFood2);
        favouriteFood2.setId(2L);
        assertThat(favouriteFood1).isNotEqualTo(favouriteFood2);
        favouriteFood1.setId(null);
        assertThat(favouriteFood1).isNotEqualTo(favouriteFood2);
    }
}
