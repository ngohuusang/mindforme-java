package com.mindforme.app.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class MindingNotificationMapperTest {
    private MindingNotificationMapper mindingNotificationMapper;

    @BeforeEach
    public void setUp() {
        mindingNotificationMapper = new MindingNotificationMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(mindingNotificationMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(mindingNotificationMapper.fromId(null)).isNull();
    }
}
