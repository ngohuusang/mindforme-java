package com.mindforme.app.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.mindforme.app.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

public class MessageItemDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(MessageItemDTO.class);
        MessageItemDTO messageItemDTO1 = new MessageItemDTO();
        messageItemDTO1.setId(1L);
        MessageItemDTO messageItemDTO2 = new MessageItemDTO();
        assertThat(messageItemDTO1).isNotEqualTo(messageItemDTO2);
        messageItemDTO2.setId(messageItemDTO1.getId());
        assertThat(messageItemDTO1).isEqualTo(messageItemDTO2);
        messageItemDTO2.setId(2L);
        assertThat(messageItemDTO1).isNotEqualTo(messageItemDTO2);
        messageItemDTO1.setId(null);
        assertThat(messageItemDTO1).isNotEqualTo(messageItemDTO2);
    }
}
