package com.mindforme.app.domain;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.elasticsearch.annotations.FieldType;

/**
 * A CouponType.
 */
@Entity
@Table(name = "coupon_type")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@org.springframework.data.elasticsearch.annotations.Document(indexName = "coupontype")
public class CouponType implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "value")
    private Integer value;

    @OneToMany(mappedBy = "type")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    private Set<Coupon> coupons = new HashSet<>();

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

    public CouponType name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getValue() {
        return value;
    }

    public CouponType value(Integer value) {
        this.value = value;
        return this;
    }

    public void setValue(Integer value) {
        this.value = value;
    }

    public Set<Coupon> getCoupons() {
        return coupons;
    }

    public CouponType coupons(Set<Coupon> coupons) {
        this.coupons = coupons;
        return this;
    }

    public CouponType addCoupon(Coupon coupon) {
        this.coupons.add(coupon);
        coupon.setType(this);
        return this;
    }

    public CouponType removeCoupon(Coupon coupon) {
        this.coupons.remove(coupon);
        coupon.setType(null);
        return this;
    }

    public void setCoupons(Set<Coupon> coupons) {
        this.coupons = coupons;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof CouponType)) {
            return false;
        }
        return id != null && id.equals(((CouponType) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "CouponType{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", value=" + getValue() +
            "}";
    }
}
