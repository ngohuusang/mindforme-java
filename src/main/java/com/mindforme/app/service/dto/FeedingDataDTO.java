package com.mindforme.app.service.dto;

import java.io.Serializable;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link com.mindforme.app.domain.FeedingData} entity.
 */
public class FeedingDataDTO implements Serializable {
    private Long id;

    @Size(max = 255)
    private String content;

    private Long langId;

    private String langName;

    private Long feedingId;

    private String feedingName;

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

    public Long getFeedingId() {
        return feedingId;
    }

    public void setFeedingId(Long feedingId) {
        this.feedingId = feedingId;
    }

    public String getFeedingName() {
        return feedingName;
    }

    public void setFeedingName(String feedingName) {
        this.feedingName = feedingName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof FeedingDataDTO)) {
            return false;
        }

        return id != null && id.equals(((FeedingDataDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "FeedingDataDTO{" +
            "id=" + getId() +
            ", content='" + getContent() + "'" +
            ", langId=" + getLangId() +
            ", langName='" + getLangName() + "'" +
            ", feedingId=" + getFeedingId() +
            ", feedingName='" + getFeedingName() + "'" +
            "}";
    }
}
