package com.mindforme.app.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.mindforme.app.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

public class MessageItemTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(MessageItem.class);
        MessageItem messageItem1 = new MessageItem();
        messageItem1.setId(1L);
        MessageItem messageItem2 = new MessageItem();
        messageItem2.setId(messageItem1.getId());
        assertThat(messageItem1).isEqualTo(messageItem2);
        messageItem2.setId(2L);
        assertThat(messageItem1).isNotEqualTo(messageItem2);
        messageItem1.setId(null);
        assertThat(messageItem1).isNotEqualTo(messageItem2);
    }
}
