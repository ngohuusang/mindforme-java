package com.mindforme.app.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class FriendshipGroupMapperTest {
    private FriendshipGroupMapper friendshipGroupMapper;

    @BeforeEach
    public void setUp() {
        friendshipGroupMapper = new FriendshipGroupMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(friendshipGroupMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(friendshipGroupMapper.fromId(null)).isNull();
    }
}
