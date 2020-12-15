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
 * A ChildRelation.
 */
@Entity
@Table(name = "child_relation")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@org.springframework.data.elasticsearch.annotations.Document(indexName = "childrelation")
public class ChildRelation implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private Status status;

    @OneToMany(mappedBy = "relation")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    private Set<ChildRelationData> relationData = new HashSet<>();

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

    public ChildRelation name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Status getStatus() {
        return status;
    }

    public ChildRelation status(Status status) {
        this.status = status;
        return this;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public Set<ChildRelationData> getRelationData() {
        return relationData;
    }

    public ChildRelation relationData(Set<ChildRelationData> childRelationData) {
        this.relationData = childRelationData;
        return this;
    }

    public ChildRelation addRelationData(ChildRelationData childRelationData) {
        this.relationData.add(childRelationData);
        childRelationData.setRelation(this);
        return this;
    }

    public ChildRelation removeRelationData(ChildRelationData childRelationData) {
        this.relationData.remove(childRelationData);
        childRelationData.setRelation(null);
        return this;
    }

    public void setRelationData(Set<ChildRelationData> childRelationData) {
        this.relationData = childRelationData;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ChildRelation)) {
            return false;
        }
        return id != null && id.equals(((ChildRelation) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ChildRelation{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", status='" + getStatus() + "'" +
            "}";
    }
}
