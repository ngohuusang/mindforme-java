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
 * A Allergy.
 */
@Entity
@Table(name = "allergy")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@org.springframework.data.elasticsearch.annotations.Document(indexName = "allergy")
public class Allergy implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private Status status;

    @OneToMany(mappedBy = "allergy")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    private Set<AllergyData> allergyData = new HashSet<>();

    @ManyToMany(mappedBy = "allergies")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnore
    private Set<Pet> pets = new HashSet<>();

    @ManyToMany(mappedBy = "allergies")
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

    public Allergy name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Status getStatus() {
        return status;
    }

    public Allergy status(Status status) {
        this.status = status;
        return this;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public Set<AllergyData> getAllergyData() {
        return allergyData;
    }

    public Allergy allergyData(Set<AllergyData> allergyData) {
        this.allergyData = allergyData;
        return this;
    }

    public Allergy addAllergyData(AllergyData allergyData) {
        this.allergyData.add(allergyData);
        allergyData.setAllergy(this);
        return this;
    }

    public Allergy removeAllergyData(AllergyData allergyData) {
        this.allergyData.remove(allergyData);
        allergyData.setAllergy(null);
        return this;
    }

    public void setAllergyData(Set<AllergyData> allergyData) {
        this.allergyData = allergyData;
    }

    public Set<Pet> getPets() {
        return pets;
    }

    public Allergy pets(Set<Pet> pets) {
        this.pets = pets;
        return this;
    }

    public Allergy addPet(Pet pet) {
        this.pets.add(pet);
        pet.getAllergies().add(this);
        return this;
    }

    public Allergy removePet(Pet pet) {
        this.pets.remove(pet);
        pet.getAllergies().remove(this);
        return this;
    }

    public void setPets(Set<Pet> pets) {
        this.pets = pets;
    }

    public Set<Child> getChildren() {
        return children;
    }

    public Allergy children(Set<Child> children) {
        this.children = children;
        return this;
    }

    public Allergy addChild(Child child) {
        this.children.add(child);
        child.getAllergies().add(this);
        return this;
    }

    public Allergy removeChild(Child child) {
        this.children.remove(child);
        child.getAllergies().remove(this);
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
        if (!(o instanceof Allergy)) {
            return false;
        }
        return id != null && id.equals(((Allergy) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Allergy{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", status='" + getStatus() + "'" +
            "}";
    }
}
