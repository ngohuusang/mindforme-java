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
 * A WalkingOther.
 */
@Entity
@Table(name = "walking_other")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@org.springframework.data.elasticsearch.annotations.Document(indexName = "walkingother")
public class WalkingOther implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private Status status;

    @OneToMany(mappedBy = "walkingOther")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    private Set<WalkingOtherData> walkingOtherData = new HashSet<>();

    @ManyToMany(mappedBy = "walkings")
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

    public WalkingOther name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Status getStatus() {
        return status;
    }

    public WalkingOther status(Status status) {
        this.status = status;
        return this;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public Set<WalkingOtherData> getWalkingOtherData() {
        return walkingOtherData;
    }

    public WalkingOther walkingOtherData(Set<WalkingOtherData> walkingOtherData) {
        this.walkingOtherData = walkingOtherData;
        return this;
    }

    public WalkingOther addWalkingOtherData(WalkingOtherData walkingOtherData) {
        this.walkingOtherData.add(walkingOtherData);
        walkingOtherData.setWalkingOther(this);
        return this;
    }

    public WalkingOther removeWalkingOtherData(WalkingOtherData walkingOtherData) {
        this.walkingOtherData.remove(walkingOtherData);
        walkingOtherData.setWalkingOther(null);
        return this;
    }

    public void setWalkingOtherData(Set<WalkingOtherData> walkingOtherData) {
        this.walkingOtherData = walkingOtherData;
    }

    public Set<Pet> getPets() {
        return pets;
    }

    public WalkingOther pets(Set<Pet> pets) {
        this.pets = pets;
        return this;
    }

    public WalkingOther addPet(Pet pet) {
        this.pets.add(pet);
        pet.getWalkings().add(this);
        return this;
    }

    public WalkingOther removePet(Pet pet) {
        this.pets.remove(pet);
        pet.getWalkings().remove(this);
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
        if (!(o instanceof WalkingOther)) {
            return false;
        }
        return id != null && id.equals(((WalkingOther) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "WalkingOther{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", status='" + getStatus() + "'" +
            "}";
    }
}
