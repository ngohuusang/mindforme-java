package com.mindforme.app.domain;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.elasticsearch.annotations.FieldType;

/**
 * A State.
 */
@Entity
@Table(name = "state")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@org.springframework.data.elasticsearch.annotations.Document(indexName = "state")
public class State implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @NotNull
    @Column(name = "status", nullable = false)
    private Integer status;

    @OneToMany(mappedBy = "state")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    private Set<City> cities = new HashSet<>();

    @OneToMany(mappedBy = "state")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    private Set<StateData> stateData = new HashSet<>();

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

    public State name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getStatus() {
        return status;
    }

    public State status(Integer status) {
        this.status = status;
        return this;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Set<City> getCities() {
        return cities;
    }

    public State cities(Set<City> cities) {
        this.cities = cities;
        return this;
    }

    public State addCity(City city) {
        this.cities.add(city);
        city.setState(this);
        return this;
    }

    public State removeCity(City city) {
        this.cities.remove(city);
        city.setState(null);
        return this;
    }

    public void setCities(Set<City> cities) {
        this.cities = cities;
    }

    public Set<StateData> getStateData() {
        return stateData;
    }

    public State stateData(Set<StateData> stateData) {
        this.stateData = stateData;
        return this;
    }

    public State addStateData(StateData stateData) {
        this.stateData.add(stateData);
        stateData.setState(this);
        return this;
    }

    public State removeStateData(StateData stateData) {
        this.stateData.remove(stateData);
        stateData.setState(null);
        return this;
    }

    public void setStateData(Set<StateData> stateData) {
        this.stateData = stateData;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof State)) {
            return false;
        }
        return id != null && id.equals(((State) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "State{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", status=" + getStatus() +
            "}";
    }
}
