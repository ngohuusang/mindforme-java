package com.mindforme.app.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.mindforme.app.domain.enumeration.Status;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.elasticsearch.annotations.FieldType;

/**
 * A FavouriteFood.
 */
@Entity
@Table(name = "favourite_food")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@org.springframework.data.elasticsearch.annotations.Document(indexName = "favouritefood")
public class FavouriteFood implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private Status status;

    @OneToMany(mappedBy = "favouriteFood")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    private Set<FavouriteFoodData> favouriteFoodData = new HashSet<>();

    @ManyToMany(mappedBy = "favouriteFoods")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnore
    private Set<Child> children = new HashSet<>();

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

    public FavouriteFood name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Status getStatus() {
        return status;
    }

    public FavouriteFood status(Status status) {
        this.status = status;
        return this;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public Set<FavouriteFoodData> getFavouriteFoodData() {
        return favouriteFoodData;
    }

    public FavouriteFood favouriteFoodData(Set<FavouriteFoodData> favouriteFoodData) {
        this.favouriteFoodData = favouriteFoodData;
        return this;
    }

    public FavouriteFood addFavouriteFoodData(FavouriteFoodData favouriteFoodData) {
        this.favouriteFoodData.add(favouriteFoodData);
        favouriteFoodData.setFavouriteFood(this);
        return this;
    }

    public FavouriteFood removeFavouriteFoodData(FavouriteFoodData favouriteFoodData) {
        this.favouriteFoodData.remove(favouriteFoodData);
        favouriteFoodData.setFavouriteFood(null);
        return this;
    }

    public void setFavouriteFoodData(Set<FavouriteFoodData> favouriteFoodData) {
        this.favouriteFoodData = favouriteFoodData;
    }

    public Set<Child> getChildren() {
        return children;
    }

    public FavouriteFood children(Set<Child> children) {
        this.children = children;
        return this;
    }

    public FavouriteFood addChild(Child child) {
        this.children.add(child);
        child.getFavouriteFoods().add(this);
        return this;
    }

    public FavouriteFood removeChild(Child child) {
        this.children.remove(child);
        child.getFavouriteFoods().remove(this);
        return this;
    }

    public void setChildren(Set<Child> children) {
        this.children = children;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof FavouriteFood)) {
            return false;
        }
        return id != null && id.equals(((FavouriteFood) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "FavouriteFood{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", status='" + getStatus() + "'" +
            "}";
    }
}
