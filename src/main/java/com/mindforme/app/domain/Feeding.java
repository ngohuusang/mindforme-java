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
 * A Feeding.
 */
@Entity
@Table(name = "feeding")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@org.springframework.data.elasticsearch.annotations.Document(indexName = "feeding")
public class Feeding implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private Status status;

    @OneToMany(mappedBy = "feeding")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    private Set<FeedingData> feedingData = new HashSet<>();

    @ManyToMany(mappedBy = "feedings")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnore
    private Set<Pet> pets = new HashSet<>();

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

    public Feeding name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Status getStatus() {
        return status;
    }

    public Feeding status(Status status) {
        this.status = status;
        return this;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public Set<FeedingData> getFeedingData() {
        return feedingData;
    }

    public Feeding feedingData(Set<FeedingData> feedingData) {
        this.feedingData = feedingData;
        return this;
    }

    public Feeding addFeedingData(FeedingData feedingData) {
        this.feedingData.add(feedingData);
        feedingData.setFeeding(this);
        return this;
    }

    public Feeding removeFeedingData(FeedingData feedingData) {
        this.feedingData.remove(feedingData);
        feedingData.setFeeding(null);
        return this;
    }

    public void setFeedingData(Set<FeedingData> feedingData) {
        this.feedingData = feedingData;
    }

    public Set<Pet> getPets() {
        return pets;
    }

    public Feeding pets(Set<Pet> pets) {
        this.pets = pets;
        return this;
    }

    public Feeding addPet(Pet pet) {
        this.pets.add(pet);
        pet.getFeedings().add(this);
        return this;
    }

    public Feeding removePet(Pet pet) {
        this.pets.remove(pet);
        pet.getFeedings().remove(this);
        return this;
    }

    public void setPets(Set<Pet> pets) {
        this.pets = pets;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Feeding)) {
            return false;
        }
        return id != null && id.equals(((Feeding) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Feeding{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", status='" + getStatus() + "'" +
            "}";
    }
}
