package com.mindforme.app.service.dto;

import com.mindforme.app.domain.enumeration.Equipment;
import com.mindforme.app.domain.enumeration.HouseProvided;
import com.mindforme.app.domain.enumeration.RubbishLoadType;
import java.io.Serializable;
import java.time.Instant;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link com.mindforme.app.domain.GardenHelpRequest} entity.
 */
public class GardenHelpRequestDTO implements Serializable {
    private Long id;

    private Long totalHelpTime;

    private Instant dateFrom;

    private Instant dateTo;

    @Size(max = 10)
    private String timeFrom;

    @Size(max = 10)
    private String timeTo;

    private String services;

    private Boolean edgeTrimming;

    private String mowingTime;

    private Equipment mowingEquipment;

    private String lawnTime;

    private Equipment lawnEquipment;

    private Integer rubbishLoad;

    private RubbishLoadType rubbishLoadType;

    private String otherDescription;

    @Size(max = 45)
    private String otherHours;

    private Equipment otherEquipment;

    private HouseProvided provideType;

    private Integer provideFor;

    private Long requestId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getTotalHelpTime() {
        return totalHelpTime;
    }

    public void setTotalHelpTime(Long totalHelpTime) {
        this.totalHelpTime = totalHelpTime;
    }

    public Instant getDateFrom() {
        return dateFrom;
    }

    public void setDateFrom(Instant dateFrom) {
        this.dateFrom = dateFrom;
    }

    public Instant getDateTo() {
        return dateTo;
    }

    public void setDateTo(Instant dateTo) {
        this.dateTo = dateTo;
    }

    public String getTimeFrom() {
        return timeFrom;
    }

    public void setTimeFrom(String timeFrom) {
        this.timeFrom = timeFrom;
    }

    public String getTimeTo() {
        return timeTo;
    }

    public void setTimeTo(String timeTo) {
        this.timeTo = timeTo;
    }

    public String getServices() {
        return services;
    }

    public void setServices(String services) {
        this.services = services;
    }

    public Boolean isEdgeTrimming() {
        return edgeTrimming;
    }

    public void setEdgeTrimming(Boolean edgeTrimming) {
        this.edgeTrimming = edgeTrimming;
    }

    public String getMowingTime() {
        return mowingTime;
    }

    public void setMowingTime(String mowingTime) {
        this.mowingTime = mowingTime;
    }

    public Equipment getMowingEquipment() {
        return mowingEquipment;
    }

    public void setMowingEquipment(Equipment mowingEquipment) {
        this.mowingEquipment = mowingEquipment;
    }

    public String getLawnTime() {
        return lawnTime;
    }

    public void setLawnTime(String lawnTime) {
        this.lawnTime = lawnTime;
    }

    public Equipment getLawnEquipment() {
        return lawnEquipment;
    }

    public void setLawnEquipment(Equipment lawnEquipment) {
        this.lawnEquipment = lawnEquipment;
    }

    public Integer getRubbishLoad() {
        return rubbishLoad;
    }

    public void setRubbishLoad(Integer rubbishLoad) {
        this.rubbishLoad = rubbishLoad;
    }

    public RubbishLoadType getRubbishLoadType() {
        return rubbishLoadType;
    }

    public void setRubbishLoadType(RubbishLoadType rubbishLoadType) {
        this.rubbishLoadType = rubbishLoadType;
    }

    public String getOtherDescription() {
        return otherDescription;
    }

    public void setOtherDescription(String otherDescription) {
        this.otherDescription = otherDescription;
    }

    public String getOtherHours() {
        return otherHours;
    }

    public void setOtherHours(String otherHours) {
        this.otherHours = otherHours;
    }

    public Equipment getOtherEquipment() {
        return otherEquipment;
    }

    public void setOtherEquipment(Equipment otherEquipment) {
        this.otherEquipment = otherEquipment;
    }

    public HouseProvided getProvideType() {
        return provideType;
    }

    public void setProvideType(HouseProvided provideType) {
        this.provideType = provideType;
    }

    public Integer getProvideFor() {
        return provideFor;
    }

    public void setProvideFor(Integer provideFor) {
        this.provideFor = provideFor;
    }

    public Long getRequestId() {
        return requestId;
    }

    public void setRequestId(Long helpRequestId) {
        this.requestId = helpRequestId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof GardenHelpRequestDTO)) {
            return false;
        }

        return id != null && id.equals(((GardenHelpRequestDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "GardenHelpRequestDTO{" +
            "id=" + getId() +
            ", totalHelpTime=" + getTotalHelpTime() +
            ", dateFrom='" + getDateFrom() + "'" +
            ", dateTo='" + getDateTo() + "'" +
            ", timeFrom='" + getTimeFrom() + "'" +
            ", timeTo='" + getTimeTo() + "'" +
            ", services='" + getServices() + "'" +
            ", edgeTrimming='" + isEdgeTrimming() + "'" +
            ", mowingTime='" + getMowingTime() + "'" +
            ", mowingEquipment='" + getMowingEquipment() + "'" +
            ", lawnTime='" + getLawnTime() + "'" +
            ", lawnEquipment='" + getLawnEquipment() + "'" +
            ", rubbishLoad=" + getRubbishLoad() +
            ", rubbishLoadType='" + getRubbishLoadType() + "'" +
            ", otherDescription='" + getOtherDescription() + "'" +
            ", otherHours='" + getOtherHours() + "'" +
            ", otherEquipment='" + getOtherEquipment() + "'" +
            ", provideType='" + getProvideType() + "'" +
            ", provideFor=" + getProvideFor() +
            ", requestId=" + getRequestId() +
            "}";
    }
}
