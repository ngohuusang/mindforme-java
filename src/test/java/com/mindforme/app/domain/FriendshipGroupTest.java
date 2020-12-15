package com.mindforme.app.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.mindforme.app.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

public class FriendshipGroupTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(FriendshipGroup.class);
        FriendshipGroup friendshipGroup1 = new FriendshipGroup();
        friendshipGroup1.setId(1L);
        FriendshipGroup friendshipGroup2 = new FriendshipGroup();
        friendshipGroup2.setId(friendshipGroup1.getId());
        assertThat(friendshipGroup1).isEqualTo(friendshipGroup2);
        friendshipGroup2.setId(2L);
        assertThat(friendshipGroup1).isNotEqualTo(friendshipGroup2);
        friendshipGroup1.setId(null);
        assertThat(friendshipGroup1).isNotEqualTo(friendshipGroup2);
    }
}
