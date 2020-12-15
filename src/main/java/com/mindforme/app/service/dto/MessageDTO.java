package com.mindforme.app.service.dto;

import java.io.Serializable;

/**
 * A DTO for the {@link com.mindforme.app.domain.Message} entity.
 */
public class MessageDTO implements Serializable {
    private Long id;

    private Long senderId;

    private String senderLogin;

    private Long receiverId;

    private String receiverLogin;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public Long getReceiverId() {
        return receiverId;
    }

    public void setReceiverId(Long userId) {
        this.receiverId = userId;
    }

    public String getReceiverLogin() {
        return receiverLogin;
    }

    public void setReceiverLogin(String userLogin) {
        this.receiverLogin = userLogin;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof MessageDTO)) {
            return false;
        }

        return id != null && id.equals(((MessageDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "MessageDTO{" +
            "id=" + getId() +
            ", senderId=" + getSenderId() +
            ", senderLogin='" + getSenderLogin() + "'" +
            ", receiverId=" + getReceiverId() +
            ", receiverLogin='" + getReceiverLogin() + "'" +
            "}";
    }
}
