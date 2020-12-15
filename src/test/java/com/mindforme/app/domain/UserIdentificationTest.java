package com.mindforme.app.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.mindforme.app.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

public class UserIdentificationTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(UserIdentification.class);
        UserIdentification userIdentification1 = new UserIdentification();
        userIdentification1.setId(1L);
        UserIdentification userIdentification2 = new UserIdentification();
        userIdentification2.setId(userIdentification1.getId());
        assertThat(userIdentification1).isEqualTo(userIdentification2);
        userIdentification2.setId(2L);
        assertThat(userIdentification1).isNotEqualTo(userIdentification2);
        userIdentification1.setId(null);
        assertThat(userIdentification1).isNotEqualTo(userIdentification2);
    }
}
