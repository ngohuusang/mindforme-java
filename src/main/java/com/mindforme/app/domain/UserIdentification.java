package com.mindforme.app.domain;

import com.mindforme.app.domain.enumeration.IdType;
import com.mindforme.app.domain.enumeration.VerificationStatus;
import java.io.Serializable;
import java.time.Instant;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.elasticsearch.annotations.FieldType;

/**
 * A UserIdentification.
 */
@Entity
@Table(name = "user_identification")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@org.springframework.data.elasticsearch.annotations.Document(indexName = "useridentification")
public class UserIdentification implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "id_type")
    private IdType idType;

    @Column(name = "name")
    private String name;

    @Column(name = "expired")
    private String expired;

    @Column(name = "id_number")
    private String idNumber;

    @Column(name = "front_image")
    private String frontImage;

    @Column(name = "back_image")
    private String backImage;

    @Column(name = "note")
    private String note;

    @Column(name = "verifier")
    private String verifier;

    @Column(name = "verified_date")
    private Instant verifiedDate;

    @Enumerated(EnumType.STRING)
    @Column(name = "verification_status")
    private VerificationStatus verificationStatus;

    @OneToOne
    @JoinColumn(unique = true)
    private User user;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public IdType getIdType() {
        return idType;
    }

    public UserIdentification idType(IdType idType) {
        this.idType = idType;
        return this;
    }

    public void setIdType(IdType idType) {
        this.idType = idType;
    }

    public String getName() {
        return name;
    }

    public UserIdentification name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getExpired() {
        return expired;
    }

    public UserIdentification expired(String expired) {
        this.expired = expired;
        return this;
    }

    public void setExpired(String expired) {
        this.expired = expired;
    }

    public String getIdNumber() {
        return idNumber;
    }

    public UserIdentification idNumber(String idNumber) {
        this.idNumber = idNumber;
        return this;
    }

    public void setIdNumber(String idNumber) {
        this.idNumber = idNumber;
    }

    public String getFrontImage() {
        return frontImage;
    }

    public UserIdentification frontImage(String frontImage) {
        this.frontImage = frontImage;
        return this;
    }

    public void setFrontImage(String frontImage) {
        this.frontImage = frontImage;
    }

    public String getBackImage() {
        return backImage;
    }

    public UserIdentification backImage(String backImage) {
        this.backImage = backImage;
        return this;
    }

    public void setBackImage(String backImage) {
        this.backImage = backImage;
    }

    public String getNote() {
        return note;
    }

    public UserIdentification note(String note) {
        this.note = note;
        return this;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getVerifier() {
        return verifier;
    }

    public UserIdentification verifier(String verifier) {
        this.verifier = verifier;
        return this;
    }

    public void setVerifier(String verifier) {
        this.verifier = verifier;
    }

    public Instant getVerifiedDate() {
        return verifiedDate;
    }

    public UserIdentification verifiedDate(Instant verifiedDate) {
        this.verifiedDate = verifiedDate;
        return this;
    }

    public void setVerifiedDate(Instant verifiedDate) {
        this.verifiedDate = verifiedDate;
    }

    public VerificationStatus getVerificationStatus() {
        return verificationStatus;
    }

    public UserIdentification verificationStatus(VerificationStatus verificationStatus) {
        this.verificationStatus = verificationStatus;
        return this;
    }

    public void setVerificationStatus(VerificationStatus verificationStatus) {
        this.verificationStatus = verificationStatus;
    }

    public User getUser() {
        return user;
    }

    public UserIdentification user(User user) {
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
        if (!(o instanceof UserIdentification)) {
            return false;
        }
        return id != null && id.equals(((UserIdentification) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "UserIdentification{" +
            "id=" + getId() +
            ", idType='" + getIdType() + "'" +
            ", name='" + getName() + "'" +
            ", expired='" + getExpired() + "'" +
            ", idNumber='" + getIdNumber() + "'" +
            ", frontImage='" + getFrontImage() + "'" +
            ", backImage='" + getBackImage() + "'" +
            ", note='" + getNote() + "'" +
            ", verifier='" + getVerifier() + "'" +
            ", verifiedDate='" + getVerifiedDate() + "'" +
            ", verificationStatus='" + getVerificationStatus() + "'" +
            "}";
    }
}
