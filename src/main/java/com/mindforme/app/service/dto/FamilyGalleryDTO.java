package com.mindforme.app.service.dto;

import com.mindforme.app.domain.enumeration.Privacy;
import java.io.Serializable;

/**
 * A DTO for the {@link com.mindforme.app.domain.FamilyGallery} entity.
 */
public class FamilyGalleryDTO implements Serializable {
    private Long id;

    private String imageUrl;

    private Integer sortOrder;

    private Boolean isDefault;

    private Privacy privacy;

    private Long familyId;

    private String familyName;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public Integer getSortOrder() {
        return sortOrder;
    }

    public void setSortOrder(Integer sortOrder) {
        this.sortOrder = sortOrder;
    }

    public Boolean isIsDefault() {
        return isDefault;
    }

    public void setIsDefault(Boolean isDefault) {
        this.isDefault = isDefault;
    }

    public Privacy getPrivacy() {
        return privacy;
    }

    public void setPrivacy(Privacy privacy) {
        this.privacy = privacy;
    }

    public Long getFamilyId() {
        return familyId;
    }

    public void setFamilyId(Long familyId) {
        this.familyId = familyId;
    }

    public String getFamilyName() {
        return familyName;
    }

    public void setFamilyName(String familyName) {
        this.familyName = familyName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof FamilyGalleryDTO)) {
            return false;
        }

        return id != null && id.equals(((FamilyGalleryDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "FamilyGalleryDTO{" +
            "id=" + getId() +
            ", imageUrl='" + getImageUrl() + "'" +
            ", sortOrder=" + getSortOrder() +
            ", isDefault='" + isIsDefault() + "'" +
            ", privacy='" + getPrivacy() + "'" +
            ", familyId=" + getFamilyId() +
            ", familyName='" + getFamilyName() + "'" +
            "}";
    }
}
