package com.mindforme.app.service.dto;

import com.mindforme.app.domain.enumeration.PlanType;
import com.mindforme.app.domain.enumeration.Status;
import java.io.Serializable;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link com.mindforme.app.domain.Plan} entity.
 */
public class PlanDTO implements Serializable {
    private Long id;

    private String name;

    @Size(max = 10)
    private String amount;

    private Integer months;

    private Status status;

    private PlanType type;

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

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public Integer getMonths() {
        return months;
    }

    public void setMonths(Integer months) {
        this.months = months;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public PlanType getType() {
        return type;
    }

    public void setType(PlanType type) {
        this.type = type;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof PlanDTO)) {
            return false;
        }

        return id != null && id.equals(((PlanDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "PlanDTO{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", amount='" + getAmount() + "'" +
            ", months=" + getMonths() +
            ", status='" + getStatus() + "'" +
            ", type='" + getType() + "'" +
            "}";
    }
}
