package com.mindforme.app.service.dto;

import com.mindforme.app.domain.enumeration.VerificationStatus;
import java.io.Serializable;
import java.time.Instant;

/**
 * A DTO for the {@link com.mindforme.app.domain.WorkingWithChildren} entity.
 */
public class WorkingWithChildrenDTO implements Serializable {
    private Long id;

    private String firstName;

    private String otherName;

    private String familyName;

    private Instant birthday;

    private String checkNumber;

    private String frontImage;

    private String backImage;

    private String note;

    private String verifier;

    private Instant verifiedDate;

    private VerificationStatus verificationStatus;

    private Long userId;

    private String userLogin;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getOtherName() {
        return otherName;
    }

    public void setOtherName(String otherName) {
        this.otherName = otherName;
    }

    public String getFamilyName() {
        return familyName;
    }

    public void setFamilyName(String familyName) {
        this.familyName = familyName;
    }

    public Instant getBirthday() {
        return birthday;
    }

    public void setBirthday(Instant birthday) {
        this.birthday = birthday;
    }

    public String getCheckNumber() {
        return checkNumber;
    }

    public void setCheckNumber(String checkNumber) {
        this.checkNumber = checkNumber;
    }

    public String getFrontImage() {
        return frontImage;
    }

    public void setFrontImage(String frontImage) {
        this.frontImage = frontImage;
    }

    public String getBackImage() {
        return backImage;
    }

    public void setBackImage(String backImage) {
        this.backImage = backImage;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getVerifier() {
        return verifier;
    }

    public void setVerifier(String verifier) {
        this.verifier = verifier;
    }

    public Instant getVerifiedDate() {
        return verifiedDate;
    }

    public void setVerifiedDate(Instant verifiedDate) {
        this.verifiedDate = verifiedDate;
    }

    public VerificationStatus getVerificationStatus() {
        return verificationStatus;
    }

    public void setVerificationStatus(VerificationStatus verificationStatus) {
        this.verificationStatus = verificationStatus;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getUserLogin() {
        return userLogin;
    }

    public void setUserLogin(String userLogin) {
        this.userLogin = userLogin;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof WorkingWithChildrenDTO)) {
            return false;
        }

        return id != null && id.equals(((WorkingWithChildrenDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "WorkingWithChildrenDTO{" +
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
            ", userId=" + getUserId() +
            ", userLogin='" + getUserLogin() + "'" +
            "}";
    }
}
