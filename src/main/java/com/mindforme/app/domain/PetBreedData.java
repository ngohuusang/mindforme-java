package com.mindforme.app.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.elasticsearch.annotations.FieldType;

/**
 * A PetBreedData.
 */
@Entity
@Table(name = "pet_breed_data")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@org.springframework.data.elasticsearch.annotations.Document(indexName = "petbreeddata")
public class PetBreedData implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Size(max = 255)
    @Column(name = "content", length = 255)
    private String content;

    @ManyToOne
    @JsonIgnoreProperties(value = "petBreedData", allowSetters = true)
    private Language lang;

    @ManyToOne
    @JsonIgnoreProperties(value = "petBreedData", allowSetters = true)
    private PetBreed petBreed;

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

    public PetBreedData content(String content) {
        this.content = content;
        return this;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Language getLang() {
        return lang;
    }

    public PetBreedData lang(Language language) {
        this.lang = language;
        return this;
    }

    public void setLang(Language language) {
        this.lang = language;
    }

    public PetBreed getPetBreed() {
        return petBreed;
    }

    public PetBreedData petBreed(PetBreed petBreed) {
        this.petBreed = petBreed;
        return this;
    }

    public void setPetBreed(PetBreed petBreed) {
        this.petBreed = petBreed;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof PetBreedData)) {
            return false;
        }
        return id != null && id.equals(((PetBreedData) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "PetBreedData{" +
            "id=" + getId() +
            ", content='" + getContent() + "'" +
            "}";
    }
}
