package com.mindforme.app.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.time.Instant;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.elasticsearch.annotations.FieldType;

/**
 * A Invitation.
 */
@Entity
@Table(name = "invitation")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@org.springframework.data.elasticsearch.annotations.Document(indexName = "invitation")
public class Invitation implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "email")
    private String email;

    @Column(name = "facebook")
    private String facebook;

    @Column(name = "free_period")
    private Instant freePeriod;

    @ManyToOne
    @JsonIgnoreProperties(value = "invitations", allowSetters = true)
    private User user;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public Invitation email(String email) {
        this.email = email;
        return this;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFacebook() {
        return facebook;
    }

    public Invitation facebook(String facebook) {
        this.facebook = facebook;
        return this;
    }

    public void setFacebook(String facebook) {
        this.facebook = facebook;
    }

    public Instant getFreePeriod() {
        return freePeriod;
    }

    public Invitation freePeriod(Instant freePeriod) {
        this.freePeriod = freePeriod;
        return this;
    }

    public void setFreePeriod(Instant freePeriod) {
        this.freePeriod = freePeriod;
    }

    public User getUser() {
        return user;
    }

    public Invitation user(User user) {
        this.user = user;
        return this;
    }

    public void setUser(User user) {
        this.user = user;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Invitation)) {
            return false;
        }
        return id != null && id.equals(((Invitation) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Invitation{" +
            "id=" + getId() +
            ", email='" + getEmail() + "'" +
            ", facebook='" + getFacebook() + "'" +
            ", freePeriod='" + getFreePeriod() + "'" +
            "}";
    }
}
