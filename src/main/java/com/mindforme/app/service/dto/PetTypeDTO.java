package com.mindforme.app.service.dto;

import com.mindforme.app.domain.enumeration.Status;
import java.io.Serializable;

/**
 * A DTO for the {@link com.mindforme.app.domain.PetType} entity.
 */
public class PetTypeDTO implements Serializable {
    private Long id;

    private String name;

    private Status status;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof PetTypeDTO)) {
            return false;
        }

        return id != null && id.equals(((PetTypeDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "PetTypeDTO{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", status='" + getStatus() + "'" +
            "}";
    }
}
