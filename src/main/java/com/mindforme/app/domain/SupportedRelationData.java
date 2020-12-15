package com.mindforme.app.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.elasticsearch.annotations.FieldType;

/**
 * A SupportedRelationData.
 */
@Entity
@Table(name = "supported_relation_data")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@org.springframework.data.elasticsearch.annotations.Document(indexName = "supportedrelationdata")
public class SupportedRelationData implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Size(max = 255)
    @Column(name = "content", length = 255)
    private String content;

    @ManyToOne
    @JsonIgnoreProperties(value = "supportedRelationData", allowSetters = true)
    private Language lang;

    @ManyToOne
    @JsonIgnoreProperties(value = "relationData", allowSetters = true)
    private SupportedRelation relation;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public SupportedRelationData content(String content) {
        this.content = content;
        return this;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Language getLang() {
        return lang;
    }

    public SupportedRelationData lang(Language language) {
        this.lang = language;
        return this;
    }

    public void setLang(Language language) {
        this.lang = language;
    }

    public SupportedRelation getRelation() {
        return relation;
    }

    public SupportedRelationData relation(SupportedRelation supportedRelation) {
        this.relation = supportedRelation;
        return this;
    }

    public void setRelation(SupportedRelation supportedRelation) {
        this.relation = supportedRelation;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof SupportedRelationData)) {
            return false;
        }
        return id != null && id.equals(((SupportedRelationData) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "SupportedRelationData{" +
            "id=" + getId() +
            ", content='" + getContent() + "'" +
            "}";
    }
}
