package com.mindforme.app.service.dto;

import java.io.Serializable;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link com.mindforme.app.domain.FavouriteFoodData} entity.
 */
public class FavouriteFoodDataDTO implements Serializable {
    private Long id;

    @Size(max = 255)
    private String content;

    private Long langId;

    private String langName;

    private Long favouriteFoodId;

    private String favouriteFoodName;

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

    public Long getFavouriteFoodId() {
        return favouriteFoodId;
    }

    public void setFavouriteFoodId(Long favouriteFoodId) {
        this.favouriteFoodId = favouriteFoodId;
    }

    public String getFavouriteFoodName() {
        return favouriteFoodName;
    }

    public void setFavouriteFoodName(String favouriteFoodName) {
        this.favouriteFoodName = favouriteFoodName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof FavouriteFoodDataDTO)) {
            return false;
        }

        return id != null && id.equals(((FavouriteFoodDataDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "FavouriteFoodDataDTO{" +
            "id=" + getId() +
            ", content='" + getContent() + "'" +
            ", langId=" + getLangId() +
            ", langName='" + getLangName() + "'" +
            ", favouriteFoodId=" + getFavouriteFoodId() +
            ", favouriteFoodName='" + getFavouriteFoodName() + "'" +
            "}";
    }
}
