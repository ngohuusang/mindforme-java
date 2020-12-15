package com.mindforme.app.service.dto;

import java.io.Serializable;

/**
 * A DTO for the {@link com.mindforme.app.domain.Language} entity.
 */
public class LanguageDTO implements Serializable {
    private Long id;

    private String code;

    private String name;

    private Boolean isDefault;

    private Boolean active;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Boolean isIsDefault() {
        return isDefault;
    }

    public void setIsDefault(Boolean isDefault) {
        this.isDefault = isDefault;
    }

    public Boolean isActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof LanguageDTO)) {
            return false;
        }

        return id != null && id.equals(((LanguageDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "LanguageDTO{" +
            "id=" + getId() +
            ", code='" + getCode() + "'" +
            ", name='" + getName() + "'" +
            ", isDefault='" + isIsDefault() + "'" +
            ", active='" + isActive() + "'" +
            "}";
    }
}
