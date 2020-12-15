package com.mindforme.app.service.dto;

import java.io.Serializable;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link com.mindforme.app.domain.WalkingOtherData} entity.
 */
public class WalkingOtherDataDTO implements Serializable {
    private Long id;

    @Size(max = 255)
    private String content;

    private Long langId;

    private String langName;

    private Long walkingOtherId;

    private String walkingOtherName;

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

    public Long getWalkingOtherId() {
        return walkingOtherId;
    }

    public void setWalkingOtherId(Long walkingOtherId) {
        this.walkingOtherId = walkingOtherId;
    }

    public String getWalkingOtherName() {
        return walkingOtherName;
    }

    public void setWalkingOtherName(String walkingOtherName) {
        this.walkingOtherName = walkingOtherName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof WalkingOtherDataDTO)) {
            return false;
        }

        return id != null && id.equals(((WalkingOtherDataDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "WalkingOtherDataDTO{" +
            "id=" + getId() +
            ", content='" + getContent() + "'" +
            ", langId=" + getLangId() +
            ", langName='" + getLangName() + "'" +
            ", walkingOtherId=" + getWalkingOtherId() +
            ", walkingOtherName='" + getWalkingOtherName() + "'" +
            "}";
    }
}
