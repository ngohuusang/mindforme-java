package com.mindforme.app.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.mindforme.app.domain.enumeration.Status;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.elasticsearch.annotations.FieldType;

/**
 * A City.
 */
@Entity
@Table(name = "city")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@org.springframework.data.elasticsearch.annotations.Document(indexName = "city")
public class City implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "latitude", precision = 21, scale = 2)
    private BigDecimal latitude;

    @Column(name = "longitude", precision = 21, scale = 2)
    private BigDecimal longitude;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private Status status;

    @OneToMany(mappedBy = "city")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    private Set<CityData> cityData = new HashSet<>();

    @ManyToOne
    @JsonIgnoreProperties(value = "cities", allowSetters = true)
    private State state;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public City name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getLatitude() {
        return latitude;
    }

    public City latitude(BigDecimal latitude) {
        this.latitude = latitude;
        return this;
    }

    public void setLatitude(BigDecimal latitude) {
        this.latitude = latitude;
    }

    public BigDecimal getLongitude() {
        return longitude;
    }

    public City longitude(BigDecimal longitude) {
        this.longitude = longitude;
        return this;
    }

    public void setLongitude(BigDecimal longitude) {
        this.longitude = longitude;
    }

    public Status getStatus() {
        return status;
    }

    public City status(Status status) {
        this.status = status;
        return this;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public Set<CityData> getCityData() {
        return cityData;
    }

    public City cityData(Set<CityData> cityData) {
        this.cityData = cityData;
        return this;
    }

    public City addCityData(CityData cityData) {
        this.cityData.add(cityData);
        cityData.setCity(this);
        return this;
    }

    public City removeCityData(CityData cityData) {
        this.cityData.remove(cityData);
        cityData.setCity(null);
        return this;
    }

    public void setCityData(Set<CityData> cityData) {
        this.cityData = cityData;
    }

    public State getState() {
        return state;
    }

    public City state(State state) {
        this.state = state;
        return this;
    }

    public void setState(State state) {
        this.state = state;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof City)) {
            return false;
        }
        return id != null && id.equals(((City) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "City{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", latitude=" + getLatitude() +
            ", longitude=" + getLongitude() +
            ", status='" + getStatus() + "'" +
            "}";
    }
}
