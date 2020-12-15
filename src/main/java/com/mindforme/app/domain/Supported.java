package com.mindforme.app.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.mindforme.app.domain.enumeration.Status;
import java.io.Serializable;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.elasticsearch.annotations.FieldType;

/**
 * A Supported.
 */
@Entity
@Table(name = "supported")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@org.springframework.data.elasticsearch.annotations.Document(indexName = "supported")
public class Supported implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "image_url")
    private String imageUrl;

    @Column(name = "birthday")
    private Instant birthday;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private Status status;

    @ManyToOne
    @JsonIgnoreProperties(value = "supporteds", allowSetters = true)
    private SupportedRelation relation;

    @ManyToOne
    @JsonIgnoreProperties(value = "supporteds", allowSetters = true)
    private Family family;

    @ManyToMany(mappedBy = "supporteds")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnore
    private Set<SupportedHelpRequest> helpRequests = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public Supported firstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public Supported lastName(String lastName) {
        this.lastName = lastName;
        return this;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public Supported imageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
        return this;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public Instant getBirthday() {
        return birthday;
    }

    public Supported birthday(Instant birthday) {
        this.birthday = birthday;
        return this;
    }

    public void setBirthday(Instant birthday) {
        this.birthday = birthday;
    }

    public Status getStatus() {
        return status;
    }

    public Supported status(Status status) {
        this.status = status;
        return this;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public SupportedRelation getRelation() {
        return relation;
    }

    public Supported relation(SupportedRelation supportedRelation) {
        this.relation = supportedRelation;
        return this;
    }

    public void setRelation(SupportedRelation supportedRelation) {
        this.relation = supportedRelation;
    }

    public Family getFamily() {
        return family;
    }

    public Supported family(Family family) {
        this.family = family;
        return this;
    }

    public void setFamily(Family family) {
        this.family = family;
    }

    public Set<SupportedHelpRequest> getHelpRequests() {
        return helpRequests;
    }

    public Supported helpRequests(Set<SupportedHelpRequest> supportedHelpRequests) {
        this.helpRequests = supportedHelpRequests;
        return this;
    }

    public Supported addHelpRequest(SupportedHelpRequest supportedHelpRequest) {
        this.helpRequests.add(supportedHelpRequest);
        supportedHelpRequest.getSupporteds().add(this);
        return this;
    }

    public Supported removeHelpRequest(SupportedHelpRequest supportedHelpRequest) {
        this.helpRequests.remove(supportedHelpRequest);
        supportedHelpRequest.getSupporteds().remove(this);
        return this;
    }

    public void setHelpRequests(Set<SupportedHelpRequest> supportedHelpRequests) {
        this.helpRequests = supportedHelpRequests;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Supported)) {
            return false;
        }
        return id != null && id.equals(((Supported) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Supported{" +
            "id=" + getId() +
            ", firstName='" + getFirstName() + "'" +
            ", lastName='" + getLastName() + "'" +
            ", imageUrl='" + getImageUrl() + "'" +
            ", birthday='" + getBirthday() + "'" +
            ", status='" + getStatus() + "'" +
            "}";
    }
}
