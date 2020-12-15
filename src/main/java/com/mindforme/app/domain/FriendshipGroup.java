package com.mindforme.app.domain;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.elasticsearch.annotations.FieldType;

/**
 * A FriendshipGroup.
 */
@Entity
@Table(name = "friendship_group")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@org.springframework.data.elasticsearch.annotations.Document(indexName = "friendshipgroup")
public class FriendshipGroup implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "description")
    private String description;

    @Column(name = "m4m_order")
    private Integer order;

    @Column(name = "number_of_members")
    private Integer numberOfMembers;

    @OneToMany(mappedBy = "group")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    private Set<Friendship> friendships = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public FriendshipGroup name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public FriendshipGroup description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getOrder() {
        return order;
    }

    public FriendshipGroup order(Integer order) {
        this.order = order;
        return this;
    }

    public void setOrder(Integer order) {
        this.order = order;
    }

    public Integer getNumberOfMembers() {
        return numberOfMembers;
    }

    public FriendshipGroup numberOfMembers(Integer numberOfMembers) {
        this.numberOfMembers = numberOfMembers;
        return this;
    }

    public void setNumberOfMembers(Integer numberOfMembers) {
        this.numberOfMembers = numberOfMembers;
    }

    public Set<Friendship> getFriendships() {
        return friendships;
    }

    public FriendshipGroup friendships(Set<Friendship> friendships) {
        this.friendships = friendships;
        return this;
    }

    public FriendshipGroup addFriendship(Friendship friendship) {
        this.friendships.add(friendship);
        friendship.setGroup(this);
        return this;
    }

    public FriendshipGroup removeFriendship(Friendship friendship) {
        this.friendships.remove(friendship);
        friendship.setGroup(null);
        return this;
    }

    public void setFriendships(Set<Friendship> friendships) {
        this.friendships = friendships;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof FriendshipGroup)) {
            return false;
        }
        return id != null && id.equals(((FriendshipGroup) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "FriendshipGroup{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", description='" + getDescription() + "'" +
            ", order=" + getOrder() +
            ", numberOfMembers=" + getNumberOfMembers() +
            "}";
    }
}
