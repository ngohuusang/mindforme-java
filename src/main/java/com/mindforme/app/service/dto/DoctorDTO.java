package com.mindforme.app.service.dto;

import java.io.Serializable;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link com.mindforme.app.domain.Doctor} entity.
 */
public class DoctorDTO implements Serializable {
    private Long id;

    @Size(max = 15)
    private String doctorName;

    @Size(max = 15)
    private String phoneNumber;

    @NotNull
    @Size(max = 100)
    private String email;

    @NotNull
    @Size(max = 100)
    private String medicalPractice;

    private Long addressId;

    private String addressAddress;

    private Long familyId;

    private String familyName;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDoctorName() {
        return doctorName;
    }

    public void setDoctorName(String doctorName) {
        this.doctorName = doctorName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMedicalPractice() {
        return medicalPractice;
    }

    public void setMedicalPractice(String medicalPractice) {
        this.medicalPractice = medicalPractice;
    }

    public Long getAddressId() {
        return addressId;
    }

    public void setAddressId(Long addressId) {
        this.addressId = addressId;
    }

    public String getAddressAddress() {
        return addressAddress;
    }

    public void setAddressAddress(String addressAddress) {
        this.addressAddress = addressAddress;
    }

    public Long getFamilyId() {
        return familyId;
    }

    public void setFamilyId(Long familyId) {
        this.familyId = familyId;
    }

    public String getFamilyName() {
        return familyName;
    }

    public void setFamilyName(String familyName) {
        this.familyName = familyName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof DoctorDTO)) {
            return false;
        }

        return id != null && id.equals(((DoctorDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "DoctorDTO{" +
            "id=" + getId() +
            ", doctorName='" + getDoctorName() + "'" +
            ", phoneNumber='" + getPhoneNumber() + "'" +
            ", email='" + getEmail() + "'" +
            ", medicalPractice='" + getMedicalPractice() + "'" +
            ", addressId=" + getAddressId() +
            ", addressAddress='" + getAddressAddress() + "'" +
            ", familyId=" + getFamilyId() +
            ", familyName='" + getFamilyName() + "'" +
            "}";
    }
}
