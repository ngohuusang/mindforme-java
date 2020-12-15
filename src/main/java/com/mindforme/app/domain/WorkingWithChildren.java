package com.mindforme.app.domain;

import com.mindforme.app.domain.enumeration.VerificationStatus;
import java.io.Serializable;
import java.time.Instant;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.elasticsearch.annotations.FieldType;

/**
 * A WorkingWithChildren.
 */
@Entity
@Table(name = "working_with_children")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@org.springframework.data.elasticsearch.annotations.Document(indexName = "workingwithchildren")
public class WorkingWithChildren implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "other_name")
    private String otherName;

    @Column(name = "family_name")
    private String familyName;

    @Column(name = "birthday")
    private Instant birthday;

    @Column(name = "check_number")
    private String checkNumber;

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

    public String getFirstName() {
        return firstName;
    }

    public WorkingWithChildren firstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getOtherName() {
        return otherName;
    }

    public WorkingWithChildren otherName(String otherName) {
        this.otherName = otherName;
        return this;
    }

    public void setOtherName(String otherName) {
        this.otherName = otherName;
    }

    public String getFamilyName() {
        return familyName;
    }

    public WorkingWithChildren familyName(String familyName) {
        this.familyName = familyName;
        return this;
    }

    public void setFamilyName(String familyName) {
        this.familyName = familyName;
    }

    public Instant getBirthday() {
        return birthday;
    }

    public WorkingWithChildren birthday(Instant birthday) {
        this.birthday = birthday;
        return this;
    }

    public void setBirthday(Instant birthday) {
        this.birthday = birthday;
    }

    public String getCheckNumber() {
        return checkNumber;
    }

    public WorkingWithChildren checkNumber(String checkNumber) {
        this.checkNumber = checkNumber;
        return this;
    }

    public void setCheckNumber(String checkNumber) {
        this.checkNumber = checkNumber;
    }

    public String getFrontImage() {
        return frontImage;
    }

    public WorkingWithChildren frontImage(String frontImage) {
        this.frontImage = frontImage;
        return this;
    }

    public void setFrontImage(String frontImage) {
        this.frontImage = frontImage;
    }

    public String getBackImage() {
        return backImage;
    }

    public WorkingWithChildren backImage(String backImage) {
        this.backImage = backImage;
        return this;
    }

    public void setBackImage(String backImage) {
        this.backImage = backImage;
    }

    public String getNote() {
        return note;
    }

    public WorkingWithChildren note(String note) {
        this.note = note;
        return this;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getVerifier() {
        return verifier;
    }

    public WorkingWithChildren verifier(String verifier) {
        this.verifier = verifier;
        return this;
    }

    public void setVerifier(String verifier) {
        this.verifier = verifier;
    }

    public Instant getVerifiedDate() {
        return verifiedDate;
    }

    public WorkingWithChildren verifiedDate(Instant verifiedDate) {
        this.verifiedDate = verifiedDate;
        return this;
    }

    public void setVerifiedDate(Instant verifiedDate) {
        this.verifiedDate = verifiedDate;
    }

    public VerificationStatus getVerificationStatus() {
        return verificationStatus;
    }

    public WorkingWithChildren verificationStatus(VerificationStatus verificationStatus) {
        this.verificationStatus = verificationStatus;
        return this;
    }

    public void setVerificationStatus(VerificationStatus verificationStatus) {
        this.verificationStatus = verificationStatus;
    }

    public User getUser() {
        return user;
    }

    public WorkingWithChildren user(User user) {
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
        if (!(o instanceof WorkingWithChildren)) {
            return false;
        }
        return id != null && id.equals(((WorkingWithChildren) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "WorkingWithChildren{" +
            "id=" + getId() +
            ", firstName='" + getFirstName() + "'" +
            ", otherName='" + getOtherName() + "'" +
            ", familyName='" + getFamilyName() + "'" +
            ", birthday='" + getBirthday() + "'" +
            ", checkNumber='" + getCheckNumber() + "'" +
            ", frontImage='" + getFrontImage() + "'" +
            ", backImage='" + getBackImage() + "'" +
            ", note='" + getNote() + "'" +
            ", verifier='" + getVerifier() + "'" +
            ", verifiedDate='" + getVerifiedDate() + "'" +
            ", verificationStatus='" + getVerificationStatus() + "'" +
            "}";
    }
}
