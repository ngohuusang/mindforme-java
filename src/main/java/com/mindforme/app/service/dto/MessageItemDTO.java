package com.mindforme.app.service.dto;

import java.io.Serializable;

/**
 * A DTO for the {@link com.mindforme.app.domain.MessageItem} entity.
 */
public class MessageItemDTO implements Serializable {
    private Long id;

    private String content;

    private Boolean read;

    private Long senderId;

    private String senderLogin;

    private Long messageId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Boolean isRead() {
        return read;
    }

    public void setRead(Boolean read) {
        this.read = read;
    }

    public Long getSenderId() {
        return senderId;
    }

    public void setSenderId(Long userId) {
        this.senderId = userId;
    }

    public String getSenderLogin() {
        return senderLogin;
    }

    public void setSenderLogin(String userLogin) {
        this.senderLogin = userLogin;
    }

    public Long getMessageId() {
        return messageId;
    }

    public void setMessageId(Long messageId) {
        this.messageId = messageId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof MessageItemDTO)) {
            return false;
        }

        return id != null && id.equals(((MessageItemDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "MessageItemDTO{" +
            "id=" + getId() +
            ", content='" + getContent() + "'" +
            ", read='" + isRead() + "'" +
            ", senderId=" + getSenderId() +
            ", senderLogin='" + getSenderLogin() + "'" +
            ", messageId=" + getMessageId() +
            "}";
    }
}
