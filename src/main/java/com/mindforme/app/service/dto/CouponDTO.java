package com.mindforme.app.service.dto;

import java.io.Serializable;
import java.time.Instant;
import javax.persistence.Lob;

/**
 * A DTO for the {@link com.mindforme.app.domain.Coupon} entity.
 */
public class CouponDTO implements Serializable {
    private Long id;

    private String name;

    private Instant expireDate;

    private String email;

    @Lob
    private String message;

    private String code;

    private Long usedBy;

    private Long typeId;

    private String typeName;

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

    public Instant getExpireDate() {
        return expireDate;
    }

    public void setExpireDate(Instant expireDate) {
        this.expireDate = expireDate;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Long getUsedBy() {
        return usedBy;
    }

    public void setUsedBy(Long usedBy) {
        this.usedBy = usedBy;
    }

    public Long getTypeId() {
        return typeId;
    }

    public void setTypeId(Long couponTypeId) {
        this.typeId = couponTypeId;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String couponTypeName) {
        this.typeName = couponTypeName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof CouponDTO)) {
            return false;
        }

        return id != null && id.equals(((CouponDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "CouponDTO{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", expireDate='" + getExpireDate() + "'" +
            ", email='" + getEmail() + "'" +
            ", message='" + getMessage() + "'" +
            ", code='" + getCode() + "'" +
            ", usedBy=" + getUsedBy() +
            ", typeId=" + getTypeId() +
            ", typeName='" + getTypeName() + "'" +
            "}";
    }
}
