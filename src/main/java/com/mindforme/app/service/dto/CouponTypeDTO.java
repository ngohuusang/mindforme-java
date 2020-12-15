package com.mindforme.app.service.dto;

import java.io.Serializable;

/**
 * A DTO for the {@link com.mindforme.app.domain.CouponType} entity.
 */
public class CouponTypeDTO implements Serializable {
    private Long id;

    private String name;

    private Integer value;

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

    public Integer getValue() {
        return value;
    }

    public void setValue(Integer value) {
        this.value = value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof CouponTypeDTO)) {
            return false;
        }

        return id != null && id.equals(((CouponTypeDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "CouponTypeDTO{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", value=" + getValue() +
            "}";
    }
}
