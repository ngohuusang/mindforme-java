package com.mindforme.app.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.mindforme.app.domain.enumeration.FriendRequestStatus;
import java.io.Serializable;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.elasticsearch.annotations.FieldType;

/**
 * A FriendRequest.
 */
@Entity
@Table(name = "friend_request")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@org.springframework.data.elasticsearch.annotations.Document(indexName = "friendrequest")
public class FriendRequest implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private FriendRequestStatus status;

    @ManyToOne
    @JsonIgnoreProperties(value = "friendRequests", allowSetters = true)
    private User friend;

    @ManyToOne
    @JsonIgnoreProperties(value = "friendRequests", allowSetters = true)
    private User user;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public FriendRequestStatus getStatus() {
        return status;
    }

    public FriendRequest status(FriendRequestStatus status) {
        this.status = status;
        return this;
    }

    public void setStatus(FriendRequestStatus status) {
        this.status = status;
    }

    public User getFriend() {
        return friend;
    }

    public FriendRequest friend(User user) {
        this.friend = user;
        return this;
    }

    public void setFriend(User user) {
        this.friend = user;
    }

    public User getUser() {
        return user;
    }

    public FriendRequest user(User user) {
        this.user = user;
        return this;
    }

    public void setUser(User user) {
        this.user = user;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof FriendRequest)) {
            return false;
        }
        return id != null && id.equals(((FriendRequest) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "FriendRequest{" +
            "id=" + getId() +
            ", status='" + getStatus() + "'" +
            "}";
    }
}
