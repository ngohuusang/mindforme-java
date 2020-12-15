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
 * A Rule.
 */
@Entity
@Table(name = "rule")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@org.springframework.data.elasticsearch.annotations.Document(indexName = "rule")
public class Rule implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private Status status;

    @OneToMany(mappedBy = "rule")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    private Set<RuleData> ruleData = new HashSet<>();

    @ManyToMany(mappedBy = "rules")
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

    public Rule name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Status getStatus() {
        return status;
    }

    public Rule status(Status status) {
        this.status = status;
        return this;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public Set<RuleData> getRuleData() {
        return ruleData;
    }

    public Rule ruleData(Set<RuleData> ruleData) {
        this.ruleData = ruleData;
        return this;
    }

    public Rule addRuleData(RuleData ruleData) {
        this.ruleData.add(ruleData);
        ruleData.setRule(this);
        return this;
    }

    public Rule removeRuleData(RuleData ruleData) {
        this.ruleData.remove(ruleData);
        ruleData.setRule(null);
        return this;
    }

    public void setRuleData(Set<RuleData> ruleData) {
        this.ruleData = ruleData;
    }

    public Set<Pet> getPets() {
        return pets;
    }

    public Rule pets(Set<Pet> pets) {
        this.pets = pets;
        return this;
    }

    public Rule addPet(Pet pet) {
        this.pets.add(pet);
        pet.getRules().add(this);
        return this;
    }

    public Rule removePet(Pet pet) {
        this.pets.remove(pet);
        pet.getRules().remove(this);
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
        if (!(o instanceof Rule)) {
            return false;
        }
        return id != null && id.equals(((Rule) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Rule{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", status='" + getStatus() + "'" +
            "}";
    }
}
