package com.mindforme.app.service.dto;

import java.io.Serializable;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link com.mindforme.app.domain.ChildRelationData} entity.
 */
public class ChildRelationDataDTO implements Serializable {
    private Long id;

    @Size(max = 255)
    private String content;

    private Long langId;

    private String langName;

    private Long relationId;

    private String relationName;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ChildRelationDataDTO)) {
            return false;
        }

        return id != null && id.equals(((ChildRelationDataDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ChildRelationDataDTO{" +
            "id=" + getId() +
            ", content='" + getContent() + "'" +
            ", langId=" + getLangId() +
            ", langName='" + getLangName() + "'" +
            ", relationId=" + getRelationId() +
            ", relationName='" + getRelationName() + "'" +
            "}";
    }
}
