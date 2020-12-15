package com.mindforme.app.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.mindforme.app.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

public class FriendshipGroupDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(FriendshipGroupDTO.class);
        FriendshipGroupDTO friendshipGroupDTO1 = new FriendshipGroupDTO();
        friendshipGroupDTO1.setId(1L);
        FriendshipGroupDTO friendshipGroupDTO2 = new FriendshipGroupDTO();
        assertThat(friendshipGroupDTO1).isNotEqualTo(friendshipGroupDTO2);
        friendshipGroupDTO2.setId(friendshipGroupDTO1.getId());
        assertThat(friendshipGroupDTO1).isEqualTo(friendshipGroupDTO2);
        friendshipGroupDTO2.setId(2L);
        assertThat(friendshipGroupDTO1).isNotEqualTo(friendshipGroupDTO2);
        friendshipGroupDTO1.setId(null);
        assertThat(friendshipGroupDTO1).isNotEqualTo(friendshipGroupDTO2);
    }
}
