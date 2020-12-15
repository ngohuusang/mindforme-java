package com.mindforme.app.service.dto;

import java.io.Serializable;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link com.mindforme.app.domain.PlanData} entity.
 */
public class PlanDataDTO implements Serializable {
    private Long id;

    @Size(max = 255)
    private String content;

    private Long langId;

    private String langName;

    private Long planId;

    private String planName;

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

    public Long getPlanId() {
        return planId;
    }

    public void setPlanId(Long planId) {
        this.planId = planId;
    }

    public String getPlanName() {
        return planName;
    }

    public void setPlanName(String planName) {
        this.planName = planName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof PlanDataDTO)) {
            return false;
        }

        return id != null && id.equals(((PlanDataDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "PlanDataDTO{" +
            "id=" + getId() +
            ", content='" + getContent() + "'" +
            ", langId=" + getLangId() +
            ", langName='" + getLangName() + "'" +
            ", planId=" + getPlanId() +
            ", planName='" + getPlanName() + "'" +
            "}";
    }
}
