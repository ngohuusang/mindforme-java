package com.mindforme.app.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.mindforme.app.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

public class MindingNotificationDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(MindingNotificationDTO.class);
        MindingNotificationDTO mindingNotificationDTO1 = new MindingNotificationDTO();
        mindingNotificationDTO1.setId(1L);
        MindingNotificationDTO mindingNotificationDTO2 = new MindingNotificationDTO();
        assertThat(mindingNotificationDTO1).isNotEqualTo(mindingNotificationDTO2);
        mindingNotificationDTO2.setId(mindingNotificationDTO1.getId());
        assertThat(mindingNotificationDTO1).isEqualTo(mindingNotificationDTO2);
        mindingNotificationDTO2.setId(2L);
        assertThat(mindingNotificationDTO1).isNotEqualTo(mindingNotificationDTO2);
        mindingNotificationDTO1.setId(null);
        assertThat(mindingNotificationDTO1).isNotEqualTo(mindingNotificationDTO2);
    }
}
