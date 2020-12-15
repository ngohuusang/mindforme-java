package com.mindforme.app.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.mindforme.app.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

public class UserIdentificationDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(UserIdentificationDTO.class);
        UserIdentificationDTO userIdentificationDTO1 = new UserIdentificationDTO();
        userIdentificationDTO1.setId(1L);
        UserIdentificationDTO userIdentificationDTO2 = new UserIdentificationDTO();
        assertThat(userIdentificationDTO1).isNotEqualTo(userIdentificationDTO2);
        userIdentificationDTO2.setId(userIdentificationDTO1.getId());
        assertThat(userIdentificationDTO1).isEqualTo(userIdentificationDTO2);
        userIdentificationDTO2.setId(2L);
        assertThat(userIdentificationDTO1).isNotEqualTo(userIdentificationDTO2);
        userIdentificationDTO1.setId(null);
        assertThat(userIdentificationDTO1).isNotEqualTo(userIdentificationDTO2);
    }
}
