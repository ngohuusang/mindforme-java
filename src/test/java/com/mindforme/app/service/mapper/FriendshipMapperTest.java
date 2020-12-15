package com.mindforme.app.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class FriendshipMapperTest {
    private FriendshipMapper friendshipMapper;

    @BeforeEach
    public void setUp() {
        friendshipMapper = new FriendshipMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(friendshipMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(friendshipMapper.fromId(null)).isNull();
    }
}
