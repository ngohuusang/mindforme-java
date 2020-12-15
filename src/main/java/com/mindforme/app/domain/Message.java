package com.mindforme.app.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.elasticsearch.annotations.FieldType;

/**
 * A Message.
 */
@Entity
@Table(name = "message")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@org.springframework.data.elasticsearch.annotations.Document(indexName = "message")
public class Message implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToMany(mappedBy = "message")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    private Set<MessageItem> items = new HashSet<>();

    @ManyToOne
    @JsonIgnoreProperties(value = "messages", allowSetters = true)
    private User sender;

    @ManyToOne
    @JsonIgnoreProperties(value = "messages", allowSetters = true)
    private User receiver;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Set<MessageItem> getItems() {
        return items;
    }

    public Message items(Set<MessageItem> messageItems) {
        this.items = messageItems;
        return this;
    }

    public Message addItem(MessageItem messageItem) {
        this.items.add(messageItem);
        messageItem.setMessage(this);
        return this;
    }

    public Message removeItem(MessageItem messageItem) {
        this.items.remove(messageItem);
        messageItem.setMessage(null);
        return this;
    }

    public void setItems(Set<MessageItem> messageItems) {
        this.items = messageItems;
    }

    public User getSender() {
        return sender;
    }

    public Message sender(User user) {
        this.sender = user;
        return this;
    }

    public void setSender(User user) {
        this.sender = user;
    }

    public User getReceiver() {
        return receiver;
    }

    public Message receiver(User user) {
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
        if (!(o instanceof Message)) {
            return false;
        }
        return id != null && id.equals(((Message) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Message{" +
            "id=" + getId() +
            "}";
    }
}
