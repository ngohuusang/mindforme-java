package com.mindforme.app.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.mindforme.app.domain.enumeration.NotificationType;
import java.io.Serializable;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.elasticsearch.annotations.FieldType;

/**
 * A Notification.
 */
@Entity
@Table(name = "notification")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@org.springframework.data.elasticsearch.annotations.Document(indexName = "notification")
public class Notification implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "type")
    private NotificationType type;

    @Column(name = "content")
    private String content;

    @Column(name = "link")
    private String link;

    @Lob
    @Column(name = "meta_data")
    private String metaData;

    @Column(name = "m4m_read")
    private Boolean read;

    @ManyToOne
    @JsonIgnoreProperties(value = "notifications", allowSetters = true)
    private User sender;

    @ManyToOne
    @JsonIgnoreProperties(value = "notifications", allowSetters = true)
    private User receiver;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public NotificationType getType() {
        return type;
    }

    public Notification type(NotificationType type) {
        this.type = type;
        return this;
    }

    public void setType(NotificationType type) {
        this.type = type;
    }

    public String getContent() {
        return content;
    }

    public Notification content(String content) {
        this.content = content;
        return this;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getLink() {
        return link;
    }

    public Notification link(String link) {
        this.link = link;
        return this;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getMetaData() {
        return metaData;
    }

    public Notification metaData(String metaData) {
        this.metaData = metaData;
        return this;
    }

    public void setMetaData(String metaData) {
        this.metaData = metaData;
    }

    public Boolean isRead() {
        return read;
    }

    public Notification read(Boolean read) {
        this.read = read;
        return this;
    }

    public void setRead(Boolean read) {
        this.read = read;
    }

    public User getSender() {
        return sender;
    }

    public Notification sender(User user) {
        this.sender = user;
        return this;
    }

    public void setSender(User user) {
        this.sender = user;
    }

    public User getReceiver() {
        return receiver;
    }

    public Notification receiver(User user) {
        this.receiver = user;
        return this;
    }

    public void setReceiver(User user) {
        this.receiver = user;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Notification)) {
            return false;
        }
        return id != null && id.equals(((Notification) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Notification{" +
            "id=" + getId() +
            ", type='" + getType() + "'" +
            ", content='" + getContent() + "'" +
            ", link='" + getLink() + "'" +
            ", metaData='" + getMetaData() + "'" +
            ", read='" + isRead() + "'" +
            "}";
    }
}
