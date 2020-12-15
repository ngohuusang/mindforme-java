package com.mindforme.app.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.mindforme.app.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

public class GardenHelpRequestTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(GardenHelpRequest.class);
        GardenHelpRequest gardenHelpRequest1 = new GardenHelpRequest();
        gardenHelpRequest1.setId(1L);
        GardenHelpRequest gardenHelpRequest2 = new GardenHelpRequest();
        gardenHelpRequest2.setId(gardenHelpRequest1.getId());
        assertThat(gardenHelpRequest1).isEqualTo(gardenHelpRequest2);
        gardenHelpRequest2.setId(2L);
        assertThat(gardenHelpRequest1).isNotEqualTo(gardenHelpRequest2);
        gardenHelpRequest1.setId(null);
        assertThat(gardenHelpRequest1).isNotEqualTo(gardenHelpRequest2);
    }
}
