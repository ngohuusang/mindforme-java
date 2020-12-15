package com.mindforme.app.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.elasticsearch.annotations.FieldType;

/**
 * A Friendship.
 */
@Entity
@Table(name = "friendship")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@org.springframework.data.elasticsearch.annotations.Document(indexName = "friendship")
public class Friendship implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JsonIgnoreProperties(value = "friendships", allowSetters = true)
    private User friend;

    @ManyToOne
    @JsonIgnoreProperties(value = "friendships", allowSetters = true)
    private User user;

    @ManyToOne
    @JsonIgnoreProperties(value = "friendships", allowSetters = true)
    private FriendshipGroup group;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getFriend() {
        return friend;
    }

    public Friendship friend(User user) {
        this.friend = user;
        return this;
    }

    public void setFriend(User user) {
        this.friend = user;
    }

    public User getUser() {
        return user;
    }

    public Friendship user(User user) {
        this.user = user;
        return this;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public FriendshipGroup getGroup() {
        return group;
    }

    public Friendship group(FriendshipGroup friendshipGroup) {
        this.group = friendshipGroup;
        return this;
    }

    public void setGroup(FriendshipGroup friendshipGroup) {
        this.group = friendshipGroup;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Friendship)) {
            return false;
        }
        return id != null && id.equals(((Friendship) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Friendship{" +
            "id=" + getId() +
            "}";
    }
}
