package com.mindforme.app.service.dto;

import com.mindforme.app.domain.enumeration.Status;
import java.io.Serializable;

/**
 * A DTO for the {@link com.mindforme.app.domain.PetBreed} entity.
 */
public class PetBreedDTO implements Serializable {
    private Long id;

    private String name;

    private Status status;

    private Long petTypeId;

    private String petTypeName;

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

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof PetBreedDTO)) {
            return false;
        }

        return id != null && id.equals(((PetBreedDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "PetBreedDTO{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", status='" + getStatus() + "'" +
            ", petTypeId=" + getPetTypeId() +
            ", petTypeName='" + getPetTypeName() + "'" +
            "}";
    }
}
