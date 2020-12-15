package com.mindforme.app.service.dto;

import com.mindforme.app.domain.enumeration.Status;
import java.io.Serializable;
import java.math.BigDecimal;

/**
 * A DTO for the {@link com.mindforme.app.domain.City} entity.
 */
public class CityDTO implements Serializable {
    private Long id;

    private String name;

    private BigDecimal latitude;

    private BigDecimal longitude;

    private Status status;

    private Long stateId;

    private String stateName;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getLatitude() {
        return latitude;
    }

    public void setLatitude(BigDecimal latitude) {
        this.latitude = latitude;
    }

    public BigDecimal getLongitude() {
        return longitude;
    }

    public void setLongitude(BigDecimal longitude) {
        this.longitude = longitude;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public Long getStateId() {
        return stateId;
    }

    public void setStateId(Long stateId) {
        this.stateId = stateId;
    }

    public String getStateName() {
        return stateName;
    }

    public void setStateName(String stateName) {
        this.stateName = stateName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof CityDTO)) {
            return false;
        }

        return id != null && id.equals(((CityDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "CityDTO{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", latitude=" + getLatitude() +
            ", longitude=" + getLongitude() +
            ", status='" + getStatus() + "'" +
            ", stateId=" + getStateId() +
            ", stateName='" + getStateName() + "'" +
            "}";
    }
}
