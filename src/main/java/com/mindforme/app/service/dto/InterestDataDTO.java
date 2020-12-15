package com.mindforme.app.service.dto;

import java.io.Serializable;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link com.mindforme.app.domain.InterestData} entity.
 */
public class InterestDataDTO implements Serializable {
    private Long id;

    @Size(max = 255)
    private String content;

    private Long langId;

    private String langName;

    private Long interestId;

    private String interestName;

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

    public Long getInterestId() {
        return interestId;
    }

    public void setInterestId(Long interestId) {
        this.interestId = interestId;
    }

    public String getInterestName() {
        return interestName;
    }

    public void setInterestName(String interestName) {
        this.interestName = interestName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof InterestDataDTO)) {
            return false;
        }

        return id != null && id.equals(((InterestDataDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "InterestDataDTO{" +
            "id=" + getId() +
            ", content='" + getContent() + "'" +
            ", langId=" + getLangId() +
            ", langName='" + getLangName() + "'" +
            ", interestId=" + getInterestId() +
            ", interestName='" + getInterestName() + "'" +
            "}";
    }
}
