package com.mindforme.app.service.dto;

import com.mindforme.app.domain.enumeration.Status;
import java.io.Serializable;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;

/**
 * A DTO for the {@link com.mindforme.app.domain.Child} entity.
 */
public class ChildDTO implements Serializable {
    private Long id;

    private String firstName;

    private String lastName;

    private String imageUrl;

    private Instant birthday;

    private Status status;

    private Long relationId;

    private String relationName;
    private Set<InterestDTO> interests = new HashSet<>();
    private Set<AllergyDTO> allergies = new HashSet<>();
    private Set<FavouriteFoodDTO> favouriteFoods = new HashSet<>();
    private Set<MedicalConditionDTO> medicalConditions = new HashSet<>();

    private Long familyId;

    private String familyName;

    private Long requestId;

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

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public Instant getBirthday() {
        return birthday;
    }

    public void setBirthday(Instant birthday) {
        this.birthday = birthday;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public Long getRelationId() {
        return relationId;
    }

    public void setRelationId(Long childRelationId) {
        this.relationId = childRelationId;
    }

    public String getRelationName() {
        return relationName;
    }

    public void setRelationName(String childRelationName) {
        this.relationName = childRelationName;
    }

    public Set<InterestDTO> getInterests() {
        return interests;
    }

    public void setInterests(Set<InterestDTO> interests) {
        this.interests = interests;
    }

    public Set<AllergyDTO> getAllergies() {
        return allergies;
    }

    public void setAllergies(Set<AllergyDTO> allergies) {
        this.allergies = allergies;
    }

    public Set<FavouriteFoodDTO> getFavouriteFoods() {
        return favouriteFoods;
    }

    public void setFavouriteFoods(Set<FavouriteFoodDTO> favouriteFoods) {
        this.favouriteFoods = favouriteFoods;
    }

    public Set<MedicalConditionDTO> getMedicalConditions() {
        return medicalConditions;
    }

    public void setMedicalConditions(Set<MedicalConditionDTO> medicalConditions) {
        this.medicalConditions = medicalConditions;
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

    public Long getRequestId() {
        return requestId;
    }

    public void setRequestId(Long helpRequestId) {
        this.requestId = helpRequestId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ChildDTO)) {
            return false;
        }

        return id != null && id.equals(((ChildDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ChildDTO{" +
            "id=" + getId() +
            ", firstName='" + getFirstName() + "'" +
            ", lastName='" + getLastName() + "'" +
            ", imageUrl='" + getImageUrl() + "'" +
            ", birthday='" + getBirthday() + "'" +
            ", status='" + getStatus() + "'" +
            ", relationId=" + getRelationId() +
            ", relationName='" + getRelationName() + "'" +
            ", interests='" + getInterests() + "'" +
            ", allergies='" + getAllergies() + "'" +
            ", favouriteFoods='" + getFavouriteFoods() + "'" +
            ", medicalConditions='" + getMedicalConditions() + "'" +
            ", familyId=" + getFamilyId() +
            ", familyName='" + getFamilyName() + "'" +
            ", requestId=" + getRequestId() +
            "}";
    }
}
