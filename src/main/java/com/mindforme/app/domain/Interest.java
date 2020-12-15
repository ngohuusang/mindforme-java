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
 * A Interest.
 */
@Entity
@Table(name = "interest")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@org.springframework.data.elasticsearch.annotations.Document(indexName = "interest")
public class Interest implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private Status status;

    @OneToMany(mappedBy = "interest")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    private Set<InterestData> interestData = new HashSet<>();

    @ManyToMany(mappedBy = "interests")
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

    public Interest name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Status getStatus() {
        return status;
    }

    public Interest status(Status status) {
        this.status = status;
        return this;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public Set<InterestData> getInterestData() {
        return interestData;
    }

    public Interest interestData(Set<InterestData> interestData) {
        this.interestData = interestData;
        return this;
    }

    public Interest addInterestData(InterestData interestData) {
        this.interestData.add(interestData);
        interestData.setInterest(this);
        return this;
    }

    public Interest removeInterestData(InterestData interestData) {
        this.interestData.remove(interestData);
        interestData.setInterest(null);
        return this;
    }

    public void setInterestData(Set<InterestData> interestData) {
        this.interestData = interestData;
    }

    public Set<Child> getChildren() {
        return children;
    }

    public Interest children(Set<Child> children) {
        this.children = children;
        return this;
    }

    public Interest addChild(Child child) {
        this.children.add(child);
        child.getInterests().add(this);
        return this;
    }

    public Interest removeChild(Child child) {
        this.children.remove(child);
        child.getInterests().remove(this);
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
        if (!(o instanceof Interest)) {
            return false;
        }
        return id != null && id.equals(((Interest) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Interest{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", status='" + getStatus() + "'" +
            "}";
    }
}
