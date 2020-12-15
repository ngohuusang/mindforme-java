package com.mindforme.app.service.dto;

import com.mindforme.app.domain.enumeration.Equipment;
import com.mindforme.app.domain.enumeration.HouseProvided;
import java.io.Serializable;
import java.time.Instant;
import javax.persistence.Lob;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link com.mindforme.app.domain.HouseHelpRequest} entity.
 */
public class HouseHelpRequestDTO implements Serializable {
    private Long id;

    @Lob
    private String services;

    private Long cleaningTime;

    private Instant cleaningFromTime;

    private Instant cleaningToTime;

    private Equipment cleaningEquipment;

    private String cleaningDescription;

    private Instant cookingFromTime;

    private Instant cookingToTime;

    private Integer cookingServes;

    private String cookingData;

    @NotNull
    private Integer pickupType;

    @Lob
    private String houseMindingDetail;

    private Instant mailFromDate;

    private Instant mailToDate;

    @Size(max = 45)
    private String mailAfter;

    @Size(max = 255)
    private String mailCollectionDays;

    @Size(max = 255)
    private String otherDescription;

    private Long otherHours;

    private Instant otherFromTime;

    private Instant otherToTime;

    private Equipment otherEquipment;

    private Long provideFor;

    private HouseProvided provideType;

    private Long requestId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getServices() {
        return services;
    }

    public void setServices(String services) {
        this.services = services;
    }

    public Long getCleaningTime() {
        return cleaningTime;
    }

    public void setCleaningTime(Long cleaningTime) {
        this.cleaningTime = cleaningTime;
    }

    public Instant getCleaningFromTime() {
        return cleaningFromTime;
    }

    public void setCleaningFromTime(Instant cleaningFromTime) {
        this.cleaningFromTime = cleaningFromTime;
    }

    public Instant getCleaningToTime() {
        return cleaningToTime;
    }

    public void setCleaningToTime(Instant cleaningToTime) {
        this.cleaningToTime = cleaningToTime;
    }

    public Equipment getCleaningEquipment() {
        return cleaningEquipment;
    }

    public void setCleaningEquipment(Equipment cleaningEquipment) {
        this.cleaningEquipment = cleaningEquipment;
    }

    public String getCleaningDescription() {
        return cleaningDescription;
    }

    public void setCleaningDescription(String cleaningDescription) {
        this.cleaningDescription = cleaningDescription;
    }

    public Instant getCookingFromTime() {
        return cookingFromTime;
    }

    public void setCookingFromTime(Instant cookingFromTime) {
        this.cookingFromTime = cookingFromTime;
    }

    public Instant getCookingToTime() {
        return cookingToTime;
    }

    public void setCookingToTime(Instant cookingToTime) {
        this.cookingToTime = cookingToTime;
    }

    public Integer getCookingServes() {
        return cookingServes;
    }

    public void setCookingServes(Integer cookingServes) {
        this.cookingServes = cookingServes;
    }

    public String getCookingData() {
        return cookingData;
    }

    public void setCookingData(String cookingData) {
        this.cookingData = cookingData;
    }

    public Integer getPickupType() {
        return pickupType;
    }

    public void setPickupType(Integer pickupType) {
        this.pickupType = pickupType;
    }

    public String getHouseMindingDetail() {
        return houseMindingDetail;
    }

    public void setHouseMindingDetail(String houseMindingDetail) {
        this.houseMindingDetail = houseMindingDetail;
    }

    public Instant getMailFromDate() {
        return mailFromDate;
    }

    public void setMailFromDate(Instant mailFromDate) {
        this.mailFromDate = mailFromDate;
    }

    public Instant getMailToDate() {
        return mailToDate;
    }

    public void setMailToDate(Instant mailToDate) {
        this.mailToDate = mailToDate;
    }

    public String getMailAfter() {
        return mailAfter;
    }

    public void setMailAfter(String mailAfter) {
        this.mailAfter = mailAfter;
    }

    public String getMailCollectionDays() {
        return mailCollectionDays;
    }

    public void setMailCollectionDays(String mailCollectionDays) {
        this.mailCollectionDays = mailCollectionDays;
    }

    public String getOtherDescription() {
        return otherDescription;
    }

    public void setOtherDescription(String otherDescription) {
        this.otherDescription = otherDescription;
    }

    public Long getOtherHours() {
        return otherHours;
    }

    public void setOtherHours(Long otherHours) {
        this.otherHours = otherHours;
    }

    public Instant getOtherFromTime() {
        return otherFromTime;
    }

    public void setOtherFromTime(Instant otherFromTime) {
        this.otherFromTime = otherFromTime;
    }

    public Instant getOtherToTime() {
        return otherToTime;
    }

    public void setOtherToTime(Instant otherToTime) {
        this.otherToTime = otherToTime;
    }

    public Equipment getOtherEquipment() {
        return otherEquipment;
    }

    public void setOtherEquipment(Equipment otherEquipment) {
        this.otherEquipment = otherEquipment;
    }

    public Long getProvideFor() {
        return provideFor;
    }

    public void setProvideFor(Long provideFor) {
        this.provideFor = provideFor;
    }

    public HouseProvided getProvideType() {
        return provideType;
    }

    public void setProvideType(HouseProvided provideType) {
        this.provideType = provideType;
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
        if (!(o instanceof HouseHelpRequestDTO)) {
            return false;
        }

        return id != null && id.equals(((HouseHelpRequestDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "HouseHelpRequestDTO{" +
            "id=" + getId() +
            ", services='" + getServices() + "'" +
            ", cleaningTime=" + getCleaningTime() +
            ", cleaningFromTime='" + getCleaningFromTime() + "'" +
            ", cleaningToTime='" + getCleaningToTime() + "'" +
            ", cleaningEquipment='" + getCleaningEquipment() + "'" +
            ", cleaningDescription='" + getCleaningDescription() + "'" +
            ", cookingFromTime='" + getCookingFromTime() + "'" +
            ", cookingToTime='" + getCookingToTime() + "'" +
            ", cookingServes=" + getCookingServes() +
            ", cookingData='" + getCookingData() + "'" +
            ", pickupType=" + getPickupType() +
            ", houseMindingDetail='" + getHouseMindingDetail() + "'" +
            ", mailFromDate='" + getMailFromDate() + "'" +
            ", mailToDate='" + getMailToDate() + "'" +
            ", mailAfter='" + getMailAfter() + "'" +
            ", mailCollectionDays='" + getMailCollectionDays() + "'" +
            ", otherDescription='" + getOtherDescription() + "'" +
            ", otherHours=" + getOtherHours() +
            ", otherFromTime='" + getOtherFromTime() + "'" +
            ", otherToTime='" + getOtherToTime() + "'" +
            ", otherEquipment='" + getOtherEquipment() + "'" +
            ", provideFor=" + getProvideFor() +
            ", provideType='" + getProvideType() + "'" +
            ", requestId=" + getRequestId() +
            "}";
    }
}
