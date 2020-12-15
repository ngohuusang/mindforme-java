package com.mindforme.app.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class InvitationMapperTest {
    private InvitationMapper invitationMapper;

    @BeforeEach
    public void setUp() {
        invitationMapper = new InvitationMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(invitationMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(invitationMapper.fromId(null)).isNull();
    }
}
