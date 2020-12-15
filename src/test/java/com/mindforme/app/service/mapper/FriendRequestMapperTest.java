package com.mindforme.app.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class FriendRequestMapperTest {
    private FriendRequestMapper friendRequestMapper;

    @BeforeEach
    public void setUp() {
        friendRequestMapper = new FriendRequestMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(friendRequestMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(friendRequestMapper.fromId(null)).isNull();
    }
}
