package com.mindforme.app.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.mindforme.app.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

public class FriendRequestTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(FriendRequest.class);
        FriendRequest friendRequest1 = new FriendRequest();
        friendRequest1.setId(1L);
        FriendRequest friendRequest2 = new FriendRequest();
        friendRequest2.setId(friendRequest1.getId());
        assertThat(friendRequest1).isEqualTo(friendRequest2);
        friendRequest2.setId(2L);
        assertThat(friendRequest1).isNotEqualTo(friendRequest2);
        friendRequest1.setId(null);
        assertThat(friendRequest1).isNotEqualTo(friendRequest2);
    }
}
