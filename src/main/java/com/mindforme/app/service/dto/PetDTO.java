package com.mindforme.app.service.dto;

import com.mindforme.app.domain.enumeration.Status;
import java.io.Serializable;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;

/**
 * A DTO for the {@link com.mindforme.app.domain.Pet} entity.
 */
public class PetDTO implements Serializable {
    private Long id;

    private String name;

    private String imageUrl;

    private Instant birthday;

    private Status status;

    private Long breedId;

    private String breedName;

    private Long petTypeId;

    private String petTypeName;
    private Set<FeedingDTO> feedings = new HashSet<>();
    private Set<RuleDTO> rules = new HashSet<>();
    private Set<AllergyDTO> allergies = new HashSet<>();
    private Set<WalkingOtherDTO> walkings = new HashSet<>();

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

    public Long getBreedId() {
        return breedId;
    }

    public void setBreedId(Long petBreedId) {
        this.breedId = petBreedId;
    }

    public String getBreedName() {
        return breedName;
    }

    public void setBreedName(String petBreedName) {
        this.breedName = petBreedName;
    }

    public Long getPetTypeId() {
        return petTypeId;
    }

    public void setPetTypeId(Long petTypeId) {
        this.petTypeId = petTypeId;
    }

    public String getPetTypeName() {
        return petTypeName;
    }

    public void setPetTypeName(String petTypeName) {
        this.petTypeName = petTypeName;
    }

    public Set<FeedingDTO> getFeedings() {
        return feedings;
    }

    public void setFeedings(Set<FeedingDTO> feedings) {
        this.feedings = feedings;
    }

    public Set<RuleDTO> getRules() {
        return rules;
    }

    public void setRules(Set<RuleDTO> rules) {
        this.rules = rules;
    }

    public Set<AllergyDTO> getAllergies() {
        return allergies;
    }

    public void setAllergies(Set<AllergyDTO> allergies) {
        this.allergies = allergies;
    }

    public Set<WalkingOtherDTO> getWalkings() {
        return walkings;
    }

    public void setWalkings(Set<WalkingOtherDTO> walkingOthers) {
        this.walkings = walkingOthers;
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
        if (!(o instanceof PetDTO)) {
            return false;
        }

        return id != null && id.equals(((PetDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "PetDTO{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", imageUrl='" + getImageUrl() + "'" +
            ", birthday='" + getBirthday() + "'" +
            ", status='" + getStatus() + "'" +
            ", breedId=" + getBreedId() +
            ", breedName='" + getBreedName() + "'" +
            ", petTypeId=" + getPetTypeId() +
            ", petTypeName='" + getPetTypeName() + "'" +
            ", feedings='" + getFeedings() + "'" +
            ", rules='" + getRules() + "'" +
            ", allergies='" + getAllergies() + "'" +
            ", walkings='" + getWalkings() + "'" +
            ", familyId=" + getFamilyId() +
            ", familyName='" + getFamilyName() + "'" +
            "}";
    }
}
