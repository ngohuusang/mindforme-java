package com.mindforme.app.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.mindforme.app.domain.enumeration.Status;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.elasticsearch.annotations.FieldType;

/**
 * A PetBreed.
 */
@Entity
@Table(name = "pet_breed")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@org.springframework.data.elasticsearch.annotations.Document(indexName = "petbreed")
public class PetBreed implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private Status status;

    @OneToMany(mappedBy = "petBreed")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    private Set<PetBreedData> petBreedData = new HashSet<>();

    @ManyToOne
    @JsonIgnoreProperties(value = "petBreeds", allowSetters = true)
    private PetType petType;

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

    public PetBreed name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Status getStatus() {
        return status;
    }

    public PetBreed status(Status status) {
        this.status = status;
        return this;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public Set<PetBreedData> getPetBreedData() {
        return petBreedData;
    }

    public PetBreed petBreedData(Set<PetBreedData> petBreedData) {
        this.petBreedData = petBreedData;
        return this;
    }

    public PetBreed addPetBreedData(PetBreedData petBreedData) {
        this.petBreedData.add(petBreedData);
        petBreedData.setPetBreed(this);
        return this;
    }

    public PetBreed removePetBreedData(PetBreedData petBreedData) {
        this.petBreedData.remove(petBreedData);
        petBreedData.setPetBreed(null);
        return this;
    }

    public void setPetBreedData(Set<PetBreedData> petBreedData) {
        this.petBreedData = petBreedData;
    }

    public PetType getPetType() {
        return petType;
    }

    public PetBreed petType(PetType petType) {
        this.petType = petType;
        return this;
    }

    public void setPetType(PetType petType) {
        this.petType = petType;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof PetBreed)) {
            return false;
        }
        return id != null && id.equals(((PetBreed) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "PetBreed{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", status='" + getStatus() + "'" +
            "}";
    }
}
