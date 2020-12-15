package com.mindforme.app.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.mindforme.app.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

public class MindingNotificationTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(MindingNotification.class);
        MindingNotification mindingNotification1 = new MindingNotification();
        mindingNotification1.setId(1L);
        MindingNotification mindingNotification2 = new MindingNotification();
        mindingNotification2.setId(mindingNotification1.getId());
        assertThat(mindingNotification1).isEqualTo(mindingNotification2);
        mindingNotification2.setId(2L);
        assertThat(mindingNotification1).isNotEqualTo(mindingNotification2);
        mindingNotification1.setId(null);
        assertThat(mindingNotification1).isNotEqualTo(mindingNotification2);
    }
}
