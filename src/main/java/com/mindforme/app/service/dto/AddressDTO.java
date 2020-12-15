package com.mindforme.app.service.dto;

import com.mindforme.app.domain.enumeration.Status;
import java.io.Serializable;
import java.math.BigDecimal;

/**
 * A DTO for the {@link com.mindforme.app.domain.Address} entity.
 */
public class AddressDTO implements Serializable {
    private Long id;

    private String address;

    private String name;

    private BigDecimal latitude;

    private BigDecimal longitude;

    private String line;

    private String unit;

    private String number;

    private String street;

    private String postcode;

    private String suburb;

    private Status status;

    private Long stateId;

    private String stateName;

    private Long countryId;

    private String countryName;

    private Long cityId;

    private String cityName;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
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

    public String getLine() {
        return line;
    }

    public void setLine(String line) {
        this.line = line;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getPostcode() {
        return postcode;
    }

    public void setPostcode(String postcode) {
        this.postcode = postcode;
    }

    public String getSuburb() {
        return suburb;
    }

    public void setSuburb(String suburb) {
        this.suburb = suburb;
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

    public Long getCountryId() {
        return countryId;
    }

    public void setCountryId(Long countryId) {
        this.countryId = countryId;
    }

    public String getCountryName() {
        return countryName;
    }

    public void setCountryName(String countryName) {
        this.countryName = countryName;
    }

    public Long getCityId() {
        return cityId;
    }

    public void setCityId(Long cityId) {
        this.cityId = cityId;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof AddressDTO)) {
            return false;
        }

        return id != null && id.equals(((AddressDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "AddressDTO{" +
            "id=" + getId() +
            ", address='" + getAddress() + "'" +
            ", name='" + getName() + "'" +
            ", latitude=" + getLatitude() +
            ", longitude=" + getLongitude() +
            ", line='" + getLine() + "'" +
            ", unit='" + getUnit() + "'" +
            ", number='" + getNumber() + "'" +
            ", street='" + getStreet() + "'" +
            ", postcode='" + getPostcode() + "'" +
            ", suburb='" + getSuburb() + "'" +
            ", status='" + getStatus() + "'" +
            ", stateId=" + getStateId() +
            ", stateName='" + getStateName() + "'" +
            ", countryId=" + getCountryId() +
            ", countryName='" + getCountryName() + "'" +
            ", cityId=" + getCityId() +
            ", cityName='" + getCityName() + "'" +
            "}";
    }
}
