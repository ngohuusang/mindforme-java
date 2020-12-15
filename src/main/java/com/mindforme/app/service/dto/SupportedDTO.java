package com.mindforme.app.service.dto;

import com.mindforme.app.domain.enumeration.Status;
import java.io.Serializable;
import java.time.Instant;

/**
 * A DTO for the {@link com.mindforme.app.domain.Supported} entity.
 */
public class SupportedDTO implements Serializable {
    private Long id;

    private String firstName;

    private String lastName;

    private String imageUrl;

    private Instant birthday;

    private Status status;

    private Long relationId;

    private String relationName;

    private Long familyId;

    private String familyName;

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

    public void setRelationId(Long supportedRelationId) {
        this.relationId = supportedRelationId;
    }

    public String getRelationName() {
        return relationName;
    }

    public void setRelationName(String supportedRelationName) {
        this.relationName = supportedRelationName;
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
        if (!(o instanceof SupportedDTO)) {
            return false;
        }

        return id != null && id.equals(((SupportedDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "SupportedDTO{" +
            "id=" + getId() +
            ", firstName='" + getFirstName() + "'" +
            ", lastName='" + getLastName() + "'" +
            ", imageUrl='" + getImageUrl() + "'" +
            ", birthday='" + getBirthday() + "'" +
            ", status='" + getStatus() + "'" +
            ", relationId=" + getRelationId() +
            ", relationName='" + getRelationName() + "'" +
            ", familyId=" + getFamilyId() +
            ", familyName='" + getFamilyName() + "'" +
            "}";
    }
}
