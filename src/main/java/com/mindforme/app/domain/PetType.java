package com.mindforme.app.domain;

import com.mindforme.app.domain.enumeration.Status;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.elasticsearch.annotations.FieldType;

/**
 * A PetType.
 */
@Entity
@Table(name = "pet_type")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@org.springframework.data.elasticsearch.annotations.Document(indexName = "pettype")
public class PetType implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private Status status;

    @OneToMany(mappedBy = "petType")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    private Set<PetBreed> petBreeds = new HashSet<>();

    @OneToMany(mappedBy = "petType")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    private Set<PetTypeData> petTypeData = new HashSet<>();

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

    public PetType name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Status getStatus() {
        return status;
    }

    public PetType status(Status status) {
        this.status = status;
        return this;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public Set<PetBreed> getPetBreeds() {
        return petBreeds;
    }

    public PetType petBreeds(Set<PetBreed> petBreeds) {
        this.petBreeds = petBreeds;
        return this;
    }

    public PetType addPetBreed(PetBreed petBreed) {
        this.petBreeds.add(petBreed);
        petBreed.setPetType(this);
        return this;
    }

    public PetType removePetBreed(PetBreed petBreed) {
        this.petBreeds.remove(petBreed);
        petBreed.setPetType(null);
        return this;
    }

    public void setPetBreeds(Set<PetBreed> petBreeds) {
        this.petBreeds = petBreeds;
    }

    public Set<PetTypeData> getPetTypeData() {
        return petTypeData;
    }

    public PetType petTypeData(Set<PetTypeData> petTypeData) {
        this.petTypeData = petTypeData;
        return this;
    }

    public PetType addPetTypeData(PetTypeData petTypeData) {
        this.petTypeData.add(petTypeData);
        petTypeData.setPetType(this);
        return this;
    }

    public PetType removePetTypeData(PetTypeData petTypeData) {
        this.petTypeData.remove(petTypeData);
        petTypeData.setPetType(null);
        return this;
    }

    public void setPetTypeData(Set<PetTypeData> petTypeData) {
        this.petTypeData = petTypeData;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof PetType)) {
            return false;
        }
        return id != null && id.equals(((PetType) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "PetType{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", status='" + getStatus() + "'" +
            "}";
    }
}
