package com.mindforme.app.service.dto;

import com.mindforme.app.domain.enumeration.IdType;
import com.mindforme.app.domain.enumeration.VerificationStatus;
import java.io.Serializable;
import java.time.Instant;

/**
 * A DTO for the {@link com.mindforme.app.domain.UserIdentification} entity.
 */
public class UserIdentificationDTO implements Serializable {
    private Long id;

    private IdType idType;

    private String name;

    private String expired;

    private String idNumber;

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

    public IdType getIdType() {
        return idType;
    }

    public void setIdType(IdType idType) {
        this.idType = idType;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getExpired() {
        return expired;
    }

    public void setExpired(String expired) {
        this.expired = expired;
    }

    public String getIdNumber() {
        return idNumber;
    }

    public void setIdNumber(String idNumber) {
        this.idNumber = idNumber;
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
        if (!(o instanceof UserIdentificationDTO)) {
            return false;
        }

        return id != null && id.equals(((UserIdentificationDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "UserIdentificationDTO{" +
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
            ", userId=" + getUserId() +
            ", userLogin='" + getUserLogin() + "'" +
            "}";
    }
}
