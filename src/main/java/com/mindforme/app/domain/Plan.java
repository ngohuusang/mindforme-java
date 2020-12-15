package com.mindforme.app.domain;

import com.mindforme.app.domain.enumeration.PlanType;
import com.mindforme.app.domain.enumeration.Status;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.elasticsearch.annotations.FieldType;

/**
 * A Plan.
 */
@Entity
@Table(name = "plan")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@org.springframework.data.elasticsearch.annotations.Document(indexName = "plan")
public class Plan implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @Size(max = 10)
    @Column(name = "amount", length = 10)
    private String amount;

    @Column(name = "months")
    private Integer months;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private Status status;

    @Enumerated(EnumType.STRING)
    @Column(name = "type")
    private PlanType type;

    @OneToMany(mappedBy = "plan")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    private Set<PlanData> plansData = new HashSet<>();

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

    public Plan name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAmount() {
        return amount;
    }

    public Plan amount(String amount) {
        this.amount = amount;
        return this;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public Integer getMonths() {
        return months;
    }

    public Plan months(Integer months) {
        this.months = months;
        return this;
    }

    public void setMonths(Integer months) {
        this.months = months;
    }

    public Status getStatus() {
        return status;
    }

    public Plan status(Status status) {
        this.status = status;
        return this;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public PlanType getType() {
        return type;
    }

    public Plan type(PlanType type) {
        this.type = type;
        return this;
    }

    public void setType(PlanType type) {
        this.type = type;
    }

    public Set<PlanData> getPlansData() {
        return plansData;
    }

    public Plan plansData(Set<PlanData> planData) {
        this.plansData = planData;
        return this;
    }

    public Plan addPlansData(PlanData planData) {
        this.plansData.add(planData);
        planData.setPlan(this);
        return this;
    }

    public Plan removePlansData(PlanData planData) {
        this.plansData.remove(planData);
        planData.setPlan(null);
        return this;
    }

    public void setPlansData(Set<PlanData> planData) {
        this.plansData = planData;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Plan)) {
            return false;
        }
        return id != null && id.equals(((Plan) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Plan{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", amount='" + getAmount() + "'" +
            ", months=" + getMonths() +
            ", status='" + getStatus() + "'" +
            ", type='" + getType() + "'" +
            "}";
    }
}
