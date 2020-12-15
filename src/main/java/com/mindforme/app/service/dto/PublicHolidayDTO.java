package com.mindforme.app.service.dto;

import java.io.Serializable;

/**
 * A DTO for the {@link com.mindforme.app.domain.PublicHoliday} entity.
 */
public class PublicHolidayDTO implements Serializable {
    private Long id;

    private Integer day;

    private Integer month;

    private Integer year;

    private String name;

    private Long countryId;

    private String countryName;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getDay() {
        return day;
    }

    public void setDay(Integer day) {
        this.day = day;
    }

    public Integer getMonth() {
        return month;
    }

    public void setMonth(Integer month) {
        this.month = month;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof PublicHolidayDTO)) {
            return false;
        }

        return id != null && id.equals(((PublicHolidayDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "PublicHolidayDTO{" +
            "id=" + getId() +
            ", day=" + getDay() +
            ", month=" + getMonth() +
            ", year=" + getYear() +
            ", name='" + getName() + "'" +
            ", countryId=" + getCountryId() +
            ", countryName='" + getCountryName() + "'" +
            "}";
    }
}
