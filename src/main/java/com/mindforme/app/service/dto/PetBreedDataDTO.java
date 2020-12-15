package com.mindforme.app.service.dto;

import java.io.Serializable;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link com.mindforme.app.domain.PetBreedData} entity.
 */
public class PetBreedDataDTO implements Serializable {
    private Long id;

    @Size(max = 255)
    private String content;

    private Long langId;

    private String langName;

    private Long petBreedId;

    private String petBreedName;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Long getLangId() {
        return langId;
    }

    public void setLangId(Long languageId) {
        this.langId = languageId;
    }

    public String getLangName() {
        return langName;
    }

    public void setLangName(String languageName) {
        this.langName = languageName;
    }

    public Long getPetBreedId() {
        return petBreedId;
    }

    public void setPetBreedId(Long petBreedId) {
        this.petBreedId = petBreedId;
    }

    public String getPetBreedName() {
        return petBreedName;
    }

    public void setPetBreedName(String petBreedName) {
        this.petBreedName = petBreedName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof PetBreedDataDTO)) {
            return false;
        }

        return id != null && id.equals(((PetBreedDataDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "PetBreedDataDTO{" +
            "id=" + getId() +
            ", content='" + getContent() + "'" +
            ", langId=" + getLangId() +
            ", langName='" + getLangName() + "'" +
            ", petBreedId=" + getPetBreedId() +
            ", petBreedName='" + getPetBreedName() + "'" +
            "}";
    }
}
