package com.mindforme.app.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.mindforme.app.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

public class FriendRequestDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(FriendRequestDTO.class);
        FriendRequestDTO friendRequestDTO1 = new FriendRequestDTO();
        friendRequestDTO1.setId(1L);
        FriendRequestDTO friendRequestDTO2 = new FriendRequestDTO();
        assertThat(friendRequestDTO1).isNotEqualTo(friendRequestDTO2);
        friendRequestDTO2.setId(friendRequestDTO1.getId());
        assertThat(friendRequestDTO1).isEqualTo(friendRequestDTO2);
        friendRequestDTO2.setId(2L);
        assertThat(friendRequestDTO1).isNotEqualTo(friendRequestDTO2);
        friendRequestDTO1.setId(null);
        assertThat(friendRequestDTO1).isNotEqualTo(friendRequestDTO2);
    }
}
