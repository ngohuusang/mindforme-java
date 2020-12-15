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
 * A SupportedRelation.
 */
@Entity
@Table(name = "supported_relation")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@org.springframework.data.elasticsearch.annotations.Document(indexName = "supportedrelation")
public class SupportedRelation implements Serializable {
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
    private Set<SupportedRelationData> relationData = new HashSet<>();

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

    public SupportedRelation name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Status getStatus() {
        return status;
    }

    public SupportedRelation status(Status status) {
        this.status = status;
        return this;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public Set<SupportedRelationData> getRelationData() {
        return relationData;
    }

    public SupportedRelation relationData(Set<SupportedRelationData> supportedRelationData) {
        this.relationData = supportedRelationData;
        return this;
    }

    public SupportedRelation addRelationData(SupportedRelationData supportedRelationData) {
        this.relationData.add(supportedRelationData);
        supportedRelationData.setRelation(this);
        return this;
    }

    public SupportedRelation removeRelationData(SupportedRelationData supportedRelationData) {
        this.relationData.remove(supportedRelationData);
        supportedRelationData.setRelation(null);
        return this;
    }

    public void setRelationData(Set<SupportedRelationData> supportedRelationData) {
        this.relationData = supportedRelationData;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof SupportedRelation)) {
            return false;
        }
        return id != null && id.equals(((SupportedRelation) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "SupportedRelation{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", status='" + getStatus() + "'" +
            "}";
    }
}
