package com.mindforme.app.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.mindforme.app.domain.enumeration.Equipment;
import com.mindforme.app.domain.enumeration.HouseProvided;
import java.io.Serializable;
import java.time.Instant;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.elasticsearch.annotations.FieldType;

/**
 * A HouseHelpRequest.
 */
@Entity
@Table(name = "house_help_request")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@org.springframework.data.elasticsearch.annotations.Document(indexName = "househelprequest")
public class HouseHelpRequest implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Lob
    @Column(name = "services")
    private String services;

    @Column(name = "cleaning_time")
    private Long cleaningTime;

    @Column(name = "cleaning_from_time")
    private Instant cleaningFromTime;

    @Column(name = "cleaning_to_time")
    private Instant cleaningToTime;

    @Enumerated(EnumType.STRING)
    @Column(name = "cleaning_equipment")
    private Equipment cleaningEquipment;

    @Column(name = "cleaning_description")
    private String cleaningDescription;

    @Column(name = "cooking_from_time")
    private Instant cookingFromTime;

    @Column(name = "cooking_to_time")
    private Instant cookingToTime;

    @Column(name = "cooking_serves")
    private Integer cookingServes;

    @Column(name = "cooking_data")
    private String cookingData;

    @NotNull
    @Column(name = "pickup_type", nullable = false)
    private Integer pickupType;

    @Lob
    @Column(name = "house_minding_detail")
    private String houseMindingDetail;

    @Column(name = "mail_from_date")
    private Instant mailFromDate;

    @Column(name = "mail_to_date")
    private Instant mailToDate;

    @Size(max = 45)
    @Column(name = "mail_after", length = 45)
    private String mailAfter;

    @Size(max = 255)
    @Column(name = "mail_collection_days", length = 255)
    private String mailCollectionDays;

    @Size(max = 255)
    @Column(name = "other_description", length = 255)
    private String otherDescription;

    @Column(name = "other_hours")
    private Long otherHours;

    @Column(name = "other_from_time")
    private Instant otherFromTime;

    @Column(name = "other_to_time")
    private Instant otherToTime;

    @Enumerated(EnumType.STRING)
    @Column(name = "other_equipment")
    private Equipment otherEquipment;

    @Column(name = "provide_for")
    private Long provideFor;

    @Enumerated(EnumType.STRING)
    @Column(name = "provide_type")
    private HouseProvided provideType;

    @ManyToOne
    @JsonIgnoreProperties(value = "houseRequests", allowSetters = true)
    private HelpRequest request;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getServices() {
        return services;
    }

    public HouseHelpRequest services(String services) {
        this.services = services;
        return this;
    }

    public void setServices(String services) {
        this.services = services;
    }

    public Long getCleaningTime() {
        return cleaningTime;
    }

    public HouseHelpRequest cleaningTime(Long cleaningTime) {
        this.cleaningTime = cleaningTime;
        return this;
    }

    public void setCleaningTime(Long cleaningTime) {
        this.cleaningTime = cleaningTime;
    }

    public Instant getCleaningFromTime() {
        return cleaningFromTime;
    }

    public HouseHelpRequest cleaningFromTime(Instant cleaningFromTime) {
        this.cleaningFromTime = cleaningFromTime;
        return this;
    }

    public void setCleaningFromTime(Instant cleaningFromTime) {
        this.cleaningFromTime = cleaningFromTime;
    }

    public Instant getCleaningToTime() {
        return cleaningToTime;
    }

    public HouseHelpRequest cleaningToTime(Instant cleaningToTime) {
        this.cleaningToTime = cleaningToTime;
        return this;
    }

    public void setCleaningToTime(Instant cleaningToTime) {
        this.cleaningToTime = cleaningToTime;
    }

    public Equipment getCleaningEquipment() {
        return cleaningEquipment;
    }

    public HouseHelpRequest cleaningEquipment(Equipment cleaningEquipment) {
        this.cleaningEquipment = cleaningEquipment;
        return this;
    }

    public void setCleaningEquipment(Equipment cleaningEquipment) {
        this.cleaningEquipment = cleaningEquipment;
    }

    public String getCleaningDescription() {
        return cleaningDescription;
    }

    public HouseHelpRequest cleaningDescription(String cleaningDescription) {
        this.cleaningDescription = cleaningDescription;
        return this;
    }

    public void setCleaningDescription(String cleaningDescription) {
        this.cleaningDescription = cleaningDescription;
    }

    public Instant getCookingFromTime() {
        return cookingFromTime;
    }

    public HouseHelpRequest cookingFromTime(Instant cookingFromTime) {
        this.cookingFromTime = cookingFromTime;
        return this;
    }

    public void setCookingFromTime(Instant cookingFromTime) {
        this.cookingFromTime = cookingFromTime;
    }

    public Instant getCookingToTime() {
        return cookingToTime;
    }

    public HouseHelpRequest cookingToTime(Instant cookingToTime) {
        this.cookingToTime = cookingToTime;
        return this;
    }

    public void setCookingToTime(Instant cookingToTime) {
        this.cookingToTime = cookingToTime;
    }

    public Integer getCookingServes() {
        return cookingServes;
    }

    public HouseHelpRequest cookingServes(Integer cookingServes) {
        this.cookingServes = cookingServes;
        return this;
    }

    public void setCookingServes(Integer cookingServes) {
        this.cookingServes = cookingServes;
    }

    public String getCookingData() {
        return cookingData;
    }

    public HouseHelpRequest cookingData(String cookingData) {
        this.cookingData = cookingData;
        return this;
    }

    public void setCookingData(String cookingData) {
        this.cookingData = cookingData;
    }

    public Integer getPickupType() {
        return pickupType;
    }

    public HouseHelpRequest pickupType(Integer pickupType) {
        this.pickupType = pickupType;
        return this;
    }

    public void setPickupType(Integer pickupType) {
        this.pickupType = pickupType;
    }

    public String getHouseMindingDetail() {
        return houseMindingDetail;
    }

    public HouseHelpRequest houseMindingDetail(String houseMindingDetail) {
        this.houseMindingDetail = houseMindingDetail;
        return this;
    }

    public void setHouseMindingDetail(String houseMindingDetail) {
        this.houseMindingDetail = houseMindingDetail;
    }

    public Instant getMailFromDate() {
        return mailFromDate;
    }

    public HouseHelpRequest mailFromDate(Instant mailFromDate) {
        this.mailFromDate = mailFromDate;
        return this;
    }

    public void setMailFromDate(Instant mailFromDate) {
        this.mailFromDate = mailFromDate;
    }

    public Instant getMailToDate() {
        return mailToDate;
    }

    public HouseHelpRequest mailToDate(Instant mailToDate) {
        this.mailToDate = mailToDate;
        return this;
    }

    public void setMailToDate(Instant mailToDate) {
        this.mailToDate = mailToDate;
    }

    public String getMailAfter() {
        return mailAfter;
    }

    public HouseHelpRequest mailAfter(String mailAfter) {
        this.mailAfter = mailAfter;
        return this;
    }

    public void setMailAfter(String mailAfter) {
        this.mailAfter = mailAfter;
    }

    public String getMailCollectionDays() {
        return mailCollectionDays;
    }

    public HouseHelpRequest mailCollectionDays(String mailCollectionDays) {
        this.mailCollectionDays = mailCollectionDays;
        return this;
    }

    public void setMailCollectionDays(String mailCollectionDays) {
        this.mailCollectionDays = mailCollectionDays;
    }

    public String getOtherDescription() {
        return otherDescription;
    }

    public HouseHelpRequest otherDescription(String otherDescription) {
        this.otherDescription = otherDescription;
        return this;
    }

    public void setOtherDescription(String otherDescription) {
        this.otherDescription = otherDescription;
    }

    public Long getOtherHours() {
        return otherHours;
    }

    public HouseHelpRequest otherHours(Long otherHours) {
        this.otherHours = otherHours;
        return this;
    }

    public void setOtherHours(Long otherHours) {
        this.otherHours = otherHours;
    }

    public Instant getOtherFromTime() {
        return otherFromTime;
    }

    public HouseHelpRequest otherFromTime(Instant otherFromTime) {
        this.otherFromTime = otherFromTime;
        return this;
    }

    public void setOtherFromTime(Instant otherFromTime) {
        this.otherFromTime = otherFromTime;
    }

    public Instant getOtherToTime() {
        return otherToTime;
    }

    public HouseHelpRequest otherToTime(Instant otherToTime) {
        this.otherToTime = otherToTime;
        return this;
    }

    public void setOtherToTime(Instant otherToTime) {
        this.otherToTime = otherToTime;
    }

    public Equipment getOtherEquipment() {
        return otherEquipment;
    }

    public HouseHelpRequest otherEquipment(Equipment otherEquipment) {
        this.otherEquipment = otherEquipment;
        return this;
    }

    public void setOtherEquipment(Equipment otherEquipment) {
        this.otherEquipment = otherEquipment;
    }

    public Long getProvideFor() {
        return provideFor;
    }

    public HouseHelpRequest provideFor(Long provideFor) {
        this.provideFor = provideFor;
        return this;
    }

    public void setProvideFor(Long provideFor) {
        this.provideFor = provideFor;
    }

    public HouseProvided getProvideType() {
        return provideType;
    }

    public HouseHelpRequest provideType(HouseProvided provideType) {
        this.provideType = provideType;
        return this;
    }

    public void setProvideType(HouseProvided provideType) {
        this.provideType = provideType;
    }

    public HelpRequest getRequest() {
        return request;
    }

    public HouseHelpRequest request(HelpRequest helpRequest) {
        this.request = helpRequest;
        return this;
    }

    public void setRequest(HelpRequest helpRequest) {
        this.request = helpRequest;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof HouseHelpRequest)) {
            return false;
        }
        return id != null && id.equals(((HouseHelpRequest) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "HouseHelpRequest{" +
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
            "}";
    }
}
