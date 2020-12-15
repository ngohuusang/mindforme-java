package com.mindforme.app.service.dto;

import java.io.Serializable;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link com.mindforme.app.domain.EmergencyContact} entity.
 */
public class EmergencyContactDTO implements Serializable {
    private Long id;

    @Size(max = 15)
    private String name;

    @Size(max = 150)
    private String relationToYou;

    @Size(max = 15)
    private String phoneNumber;

    @NotNull
    @Size(max = 100)
    private String email;

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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRelationToYou() {
        return relationToYou;
    }

    public void setRelationToYou(String relationToYou) {
        this.relationToYou = relationToYou;
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
        if (!(o instanceof EmergencyContactDTO)) {
            return false;
        }

        return id != null && id.equals(((EmergencyContactDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "EmergencyContactDTO{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", relationToYou='" + getRelationToYou() + "'" +
            ", phoneNumber='" + getPhoneNumber() + "'" +
            ", email='" + getEmail() + "'" +
            ", addressId=" + getAddressId() +
            ", addressAddress='" + getAddressAddress() + "'" +
            ", familyId=" + getFamilyId() +
            ", familyName='" + getFamilyName() + "'" +
            "}";
    }
}
