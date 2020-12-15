package com.mindforme.app.service.dto;

import java.io.Serializable;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link com.mindforme.app.domain.MedicalConditionData} entity.
 */
public class MedicalConditionDataDTO implements Serializable {
    private Long id;

    @Size(max = 255)
    private String content;

    private Long langId;

    private String langName;

    private Long medicalConditionId;

    private String medicalConditionName;

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

    public Long getMedicalConditionId() {
        return medicalConditionId;
    }

    public void setMedicalConditionId(Long medicalConditionId) {
        this.medicalConditionId = medicalConditionId;
    }

    public String getMedicalConditionName() {
        return medicalConditionName;
    }

    public void setMedicalConditionName(String medicalConditionName) {
        this.medicalConditionName = medicalConditionName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof MedicalConditionDataDTO)) {
            return false;
        }

        return id != null && id.equals(((MedicalConditionDataDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "MedicalConditionDataDTO{" +
            "id=" + getId() +
            ", content='" + getContent() + "'" +
            ", langId=" + getLangId() +
            ", langName='" + getLangName() + "'" +
            ", medicalConditionId=" + getMedicalConditionId() +
            ", medicalConditionName='" + getMedicalConditionName() + "'" +
            "}";
    }
}
