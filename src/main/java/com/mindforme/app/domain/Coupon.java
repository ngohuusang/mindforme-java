package com.mindforme.app.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.time.Instant;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.elasticsearch.annotations.FieldType;

/**
 * A Coupon.
 */
@Entity
@Table(name = "coupon")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@org.springframework.data.elasticsearch.annotations.Document(indexName = "coupon")
public class Coupon implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "expire_date")
    private Instant expireDate;

    @Column(name = "email")
    private String email;

    @Lob
    @Column(name = "message")
    private String message;

    @Column(name = "code")
    private String code;

    @Column(name = "used_by")
    private Long usedBy;

    @ManyToOne
    @JsonIgnoreProperties(value = "coupons", allowSetters = true)
    private CouponType type;

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

    public Coupon name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Instant getExpireDate() {
        return expireDate;
    }

    public Coupon expireDate(Instant expireDate) {
        this.expireDate = expireDate;
        return this;
    }

    public void setExpireDate(Instant expireDate) {
        this.expireDate = expireDate;
    }

    public String getEmail() {
        return email;
    }

    public Coupon email(String email) {
        this.email = email;
        return this;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMessage() {
        return message;
    }

    public Coupon message(String message) {
        this.message = message;
        return this;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getCode() {
        return code;
    }

    public Coupon code(String code) {
        this.code = code;
        return this;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Long getUsedBy() {
        return usedBy;
    }

    public Coupon usedBy(Long usedBy) {
        this.usedBy = usedBy;
        return this;
    }

    public void setUsedBy(Long usedBy) {
        this.usedBy = usedBy;
    }

    public CouponType getType() {
        return type;
    }

    public Coupon type(CouponType couponType) {
        this.type = couponType;
        return this;
    }

    public void setType(CouponType couponType) {
        this.type = couponType;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Coupon)) {
            return false;
        }
        return id != null && id.equals(((Coupon) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Coupon{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", expireDate='" + getExpireDate() + "'" +
            ", email='" + getEmail() + "'" +
            ", message='" + getMessage() + "'" +
            ", code='" + getCode() + "'" +
            ", usedBy=" + getUsedBy() +
            "}";
    }
}
