package com.mindforme.app.service.dto;

import com.mindforme.app.domain.enumeration.NotificationType;
import java.io.Serializable;
import javax.persistence.Lob;

/**
 * A DTO for the {@link com.mindforme.app.domain.Notification} entity.
 */
public class NotificationDTO implements Serializable {
    private Long id;

    private NotificationType type;

    private String content;

    private String link;

    @Lob
    private String metaData;

    private Boolean read;

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

    public NotificationType getType() {
        return type;
    }

    public void setType(NotificationType type) {
        this.type = type;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getMetaData() {
        return metaData;
    }

    public void setMetaData(String metaData) {
        this.metaData = metaData;
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
        if (!(o instanceof NotificationDTO)) {
            return false;
        }

        return id != null && id.equals(((NotificationDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "NotificationDTO{" +
            "id=" + getId() +
            ", type='" + getType() + "'" +
            ", content='" + getContent() + "'" +
            ", link='" + getLink() + "'" +
            ", metaData='" + getMetaData() + "'" +
            ", read='" + isRead() + "'" +
            ", senderId=" + getSenderId() +
            ", senderLogin='" + getSenderLogin() + "'" +
            ", receiverId=" + getReceiverId() +
            ", receiverLogin='" + getReceiverLogin() + "'" +
            "}";
    }
}
