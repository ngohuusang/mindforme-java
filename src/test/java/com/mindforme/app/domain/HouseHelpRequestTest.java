package com.mindforme.app.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.mindforme.app.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

public class HouseHelpRequestTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(HouseHelpRequest.class);
        HouseHelpRequest houseHelpRequest1 = new HouseHelpRequest();
        houseHelpRequest1.setId(1L);
        HouseHelpRequest houseHelpRequest2 = new HouseHelpRequest();
        houseHelpRequest2.setId(houseHelpRequest1.getId());
        assertThat(houseHelpRequest1).isEqualTo(houseHelpRequest2);
        houseHelpRequest2.setId(2L);
        assertThat(houseHelpRequest1).isNotEqualTo(houseHelpRequest2);
        houseHelpRequest1.setId(null);
        assertThat(houseHelpRequest1).isNotEqualTo(houseHelpRequest2);
    }
}
