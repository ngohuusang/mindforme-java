package com.mindforme.app.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.mindforme.app.domain.enumeration.Privacy;
import java.io.Serializable;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.elasticsearch.annotations.FieldType;

/**
 * A FamilyGallery.
 */
@Entity
@Table(name = "family_gallery")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@org.springframework.data.elasticsearch.annotations.Document(indexName = "familygallery")
public class FamilyGallery implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "image_url")
    private String imageUrl;

    @Column(name = "sort_order")
    private Integer sortOrder;

    @Column(name = "is_default")
    private Boolean isDefault;

    @Enumerated(EnumType.STRING)
    @Column(name = "privacy")
    private Privacy privacy;

    @ManyToOne
    @JsonIgnoreProperties(value = "galleries", allowSetters = true)
    private Family family;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public FamilyGallery imageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
        return this;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public Integer getSortOrder() {
        return sortOrder;
    }

    public FamilyGallery sortOrder(Integer sortOrder) {
        this.sortOrder = sortOrder;
        return this;
    }

    public void setSortOrder(Integer sortOrder) {
        this.sortOrder = sortOrder;
    }

    public Boolean isIsDefault() {
        return isDefault;
    }

    public FamilyGallery isDefault(Boolean isDefault) {
        this.isDefault = isDefault;
        return this;
    }

    public void setIsDefault(Boolean isDefault) {
        this.isDefault = isDefault;
    }

    public Privacy getPrivacy() {
        return privacy;
    }

    public FamilyGallery privacy(Privacy privacy) {
        this.privacy = privacy;
        return this;
    }

    public void setPrivacy(Privacy privacy) {
        this.privacy = privacy;
    }

    public Family getFamily() {
        return family;
    }

    public FamilyGallery family(Family family) {
        this.family = family;
        return this;
    }

    public void setFamily(Family family) {
        this.family = family;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof FamilyGallery)) {
            return false;
        }
        return id != null && id.equals(((FamilyGallery) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "FamilyGallery{" +
            "id=" + getId() +
            ", imageUrl='" + getImageUrl() + "'" +
            ", sortOrder=" + getSortOrder() +
            ", isDefault='" + isIsDefault() + "'" +
            ", privacy='" + getPrivacy() + "'" +
            "}";
    }
}
