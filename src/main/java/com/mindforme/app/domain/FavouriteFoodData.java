package com.mindforme.app.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.elasticsearch.annotations.FieldType;

/**
 * A FavouriteFoodData.
 */
@Entity
@Table(name = "favourite_food_data")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@org.springframework.data.elasticsearch.annotations.Document(indexName = "favouritefooddata")
public class FavouriteFoodData implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Size(max = 255)
    @Column(name = "content", length = 255)
    private String content;

    @ManyToOne
    @JsonIgnoreProperties(value = "favouriteFoodData", allowSetters = true)
    private Language lang;

    @ManyToOne
    @JsonIgnoreProperties(value = "favouriteFoodData", allowSetters = true)
    private FavouriteFood favouriteFood;

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

    public FavouriteFoodData content(String content) {
        this.content = content;
        return this;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Language getLang() {
        return lang;
    }

    public FavouriteFoodData lang(Language language) {
        this.lang = language;
        return this;
    }

    public void setLang(Language language) {
        this.lang = language;
    }

    public FavouriteFood getFavouriteFood() {
        return favouriteFood;
    }

    public FavouriteFoodData favouriteFood(FavouriteFood favouriteFood) {
        this.favouriteFood = favouriteFood;
        return this;
    }

    public void setFavouriteFood(FavouriteFood favouriteFood) {
        this.favouriteFood = favouriteFood;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof FavouriteFoodData)) {
            return false;
        }
        return id != null && id.equals(((FavouriteFoodData) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "FavouriteFoodData{" +
            "id=" + getId() +
            ", content='" + getContent() + "'" +
            "}";
    }
}
