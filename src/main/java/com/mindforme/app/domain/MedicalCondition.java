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
 * A MedicalCondition.
 */
@Entity
@Table(name = "medical_condition")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@org.springframework.data.elasticsearch.annotations.Document(indexName = "medicalcondition")
public class MedicalCondition implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private Status status;

    @OneToMany(mappedBy = "medicalCondition")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    private Set<MedicalConditionData> medicalConditionData = new HashSet<>();

    @ManyToMany(mappedBy = "medicalConditions")
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

    public MedicalCondition name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Status getStatus() {
        return status;
    }

    public MedicalCondition status(Status status) {
        this.status = status;
        return this;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public Set<MedicalConditionData> getMedicalConditionData() {
        return medicalConditionData;
    }

    public MedicalCondition medicalConditionData(Set<MedicalConditionData> medicalConditionData) {
        this.medicalConditionData = medicalConditionData;
        return this;
    }

    public MedicalCondition addMedicalConditionData(MedicalConditionData medicalConditionData) {
        this.medicalConditionData.add(medicalConditionData);
        medicalConditionData.setMedicalCondition(this);
        return this;
    }

    public MedicalCondition removeMedicalConditionData(MedicalConditionData medicalConditionData) {
        this.medicalConditionData.remove(medicalConditionData);
        medicalConditionData.setMedicalCondition(null);
        return this;
    }

    public void setMedicalConditionData(Set<MedicalConditionData> medicalConditionData) {
        this.medicalConditionData = medicalConditionData;
    }

    public Set<Child> getChildren() {
        return children;
    }

    public MedicalCondition children(Set<Child> children) {
        this.children = children;
        return this;
    }

    public MedicalCondition addChild(Child child) {
        this.children.add(child);
        child.getMedicalConditions().add(this);
        return this;
    }

    public MedicalCondition removeChild(Child child) {
        this.children.remove(child);
        child.getMedicalConditions().remove(this);
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
        if (!(o instanceof MedicalCondition)) {
            return false;
        }
        return id != null && id.equals(((MedicalCondition) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "MedicalCondition{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", status='" + getStatus() + "'" +
            "}";
    }
}
