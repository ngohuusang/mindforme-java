package com.mindforme.app.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class MessageItemMapperTest {
    private MessageItemMapper messageItemMapper;

    @BeforeEach
    public void setUp() {
        messageItemMapper = new MessageItemMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(messageItemMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(messageItemMapper.fromId(null)).isNull();
    }
}
