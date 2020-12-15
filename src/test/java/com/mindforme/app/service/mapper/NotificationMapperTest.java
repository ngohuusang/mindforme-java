package com.mindforme.app.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class NotificationMapperTest {
    private NotificationMapper notificationMapper;

    @BeforeEach
    public void setUp() {
        notificationMapper = new NotificationMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(notificationMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(notificationMapper.fromId(null)).isNull();
    }
}
