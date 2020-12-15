package com.mindforme.app.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.mindforme.app.domain.enumeration.Equipment;
import com.mindforme.app.domain.enumeration.HouseProvided;
import com.mindforme.app.domain.enumeration.RubbishLoadType;
import java.io.Serializable;
import java.time.Instant;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.elasticsearch.annotations.FieldType;

/**
 * A GardenHelpRequest.
 */
@Entity
@Table(name = "garden_help_request")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@org.springframework.data.elasticsearch.annotations.Document(indexName = "gardenhelprequest")
public class GardenHelpRequest implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "total_help_time")
    private Long totalHelpTime;

    @Column(name = "date_from")
    private Instant dateFrom;

    @Column(name = "date_to")
    private Instant dateTo;

    @Size(max = 10)
    @Column(name = "time_from", length = 10)
    private String timeFrom;

    @Size(max = 10)
    @Column(name = "time_to", length = 10)
    private String timeTo;

    @Column(name = "services")
    private String services;

    @Column(name = "edge_trimming")
    private Boolean edgeTrimming;

    @Column(name = "mowing_time")
    private String mowingTime;

    @Enumerated(EnumType.STRING)
    @Column(name = "mowing_equipment")
    private Equipment mowingEquipment;

    @Column(name = "lawn_time")
    private String lawnTime;

    @Enumerated(EnumType.STRING)
    @Column(name = "lawn_equipment")
    private Equipment lawnEquipment;

    @Column(name = "rubbish_load")
    private Integer rubbishLoad;

    @Enumerated(EnumType.STRING)
    @Column(name = "rubbish_load_type")
    private RubbishLoadType rubbishLoadType;

    @Column(name = "other_description")
    private String otherDescription;

    @Size(max = 45)
    @Column(name = "other_hours", length = 45)
    private String otherHours;

    @Enumerated(EnumType.STRING)
    @Column(name = "other_equipment")
    private Equipment otherEquipment;

    @Enumerated(EnumType.STRING)
    @Column(name = "provide_type")
    private HouseProvided provideType;

    @Column(name = "provide_for")
    private Integer provideFor;

    @ManyToOne
    @JsonIgnoreProperties(value = "gardenRequests", allowSetters = true)
    private HelpRequest request;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getTotalHelpTime() {
        return totalHelpTime;
    }

    public GardenHelpRequest totalHelpTime(Long totalHelpTime) {
        this.totalHelpTime = totalHelpTime;
        return this;
    }

    public void setTotalHelpTime(Long totalHelpTime) {
        this.totalHelpTime = totalHelpTime;
    }

    public Instant getDateFrom() {
        return dateFrom;
    }

    public GardenHelpRequest dateFrom(Instant dateFrom) {
        this.dateFrom = dateFrom;
        return this;
    }

    public void setDateFrom(Instant dateFrom) {
        this.dateFrom = dateFrom;
    }

    public Instant getDateTo() {
        return dateTo;
    }

    public GardenHelpRequest dateTo(Instant dateTo) {
        this.dateTo = dateTo;
        return this;
    }

    public void setDateTo(Instant dateTo) {
        this.dateTo = dateTo;
    }

    public String getTimeFrom() {
        return timeFrom;
    }

    public GardenHelpRequest timeFrom(String timeFrom) {
        this.timeFrom = timeFrom;
        return this;
    }

    public void setTimeFrom(String timeFrom) {
        this.timeFrom = timeFrom;
    }

    public String getTimeTo() {
        return timeTo;
    }

    public GardenHelpRequest timeTo(String timeTo) {
        this.timeTo = timeTo;
        return this;
    }

    public void setTimeTo(String timeTo) {
        this.timeTo = timeTo;
    }

    public String getServices() {
        return services;
    }

    public GardenHelpRequest services(String services) {
        this.services = services;
        return this;
    }

    public void setServices(String services) {
        this.services = services;
    }

    public Boolean isEdgeTrimming() {
        return edgeTrimming;
    }

    public GardenHelpRequest edgeTrimming(Boolean edgeTrimming) {
        this.edgeTrimming = edgeTrimming;
        return this;
    }

    public void setEdgeTrimming(Boolean edgeTrimming) {
        this.edgeTrimming = edgeTrimming;
    }

    public String getMowingTime() {
        return mowingTime;
    }

    public GardenHelpRequest mowingTime(String mowingTime) {
        this.mowingTime = mowingTime;
        return this;
    }

    public void setMowingTime(String mowingTime) {
        this.mowingTime = mowingTime;
    }

    public Equipment getMowingEquipment() {
        return mowingEquipment;
    }

    public GardenHelpRequest mowingEquipment(Equipment mowingEquipment) {
        this.mowingEquipment = mowingEquipment;
        return this;
    }

    public void setMowingEquipment(Equipment mowingEquipment) {
        this.mowingEquipment = mowingEquipment;
    }

    public String getLawnTime() {
        return lawnTime;
    }

    public GardenHelpRequest lawnTime(String lawnTime) {
        this.lawnTime = lawnTime;
        return this;
    }

    public void setLawnTime(String lawnTime) {
        this.lawnTime = lawnTime;
    }

    public Equipment getLawnEquipment() {
        return lawnEquipment;
    }

    public GardenHelpRequest lawnEquipment(Equipment lawnEquipment) {
        this.lawnEquipment = lawnEquipment;
        return this;
    }

    public void setLawnEquipment(Equipment lawnEquipment) {
        this.lawnEquipment = lawnEquipment;
    }

    public Integer getRubbishLoad() {
        return rubbishLoad;
    }

    public GardenHelpRequest rubbishLoad(Integer rubbishLoad) {
        this.rubbishLoad = rubbishLoad;
        return this;
    }

    public void setRubbishLoad(Integer rubbishLoad) {
        this.rubbishLoad = rubbishLoad;
    }

    public RubbishLoadType getRubbishLoadType() {
        return rubbishLoadType;
    }

    public GardenHelpRequest rubbishLoadType(RubbishLoadType rubbishLoadType) {
        this.rubbishLoadType = rubbishLoadType;
        return this;
    }

    public void setRubbishLoadType(RubbishLoadType rubbishLoadType) {
        this.rubbishLoadType = rubbishLoadType;
    }

    public String getOtherDescription() {
        return otherDescription;
    }

    public GardenHelpRequest otherDescription(String otherDescription) {
        this.otherDescription = otherDescription;
        return this;
    }

    public void setOtherDescription(String otherDescription) {
        this.otherDescription = otherDescription;
    }

    public String getOtherHours() {
        return otherHours;
    }

    public GardenHelpRequest otherHours(String otherHours) {
        this.otherHours = otherHours;
        return this;
    }

    public void setOtherHours(String otherHours) {
        this.otherHours = otherHours;
    }

    public Equipment getOtherEquipment() {
        return otherEquipment;
    }

    public GardenHelpRequest otherEquipment(Equipment otherEquipment) {
        this.otherEquipment = otherEquipment;
        return this;
    }

    public void setOtherEquipment(Equipment otherEquipment) {
        this.otherEquipment = otherEquipment;
    }

    public HouseProvided getProvideType() {
        return provideType;
    }

    public GardenHelpRequest provideType(HouseProvided provideType) {
        this.provideType = provideType;
        return this;
    }

    public void setProvideType(HouseProvided provideType) {
        this.provideType = provideType;
    }

    public Integer getProvideFor() {
        return provideFor;
    }

    public GardenHelpRequest provideFor(Integer provideFor) {
        this.provideFor = provideFor;
        return this;
    }

    public void setProvideFor(Integer provideFor) {
        this.provideFor = provideFor;
    }

    public HelpRequest getRequest() {
        return request;
    }

    public GardenHelpRequest request(HelpRequest helpRequest) {
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
        if (!(o instanceof GardenHelpRequest)) {
            return false;
        }
        return id != null && id.equals(((GardenHelpRequest) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "GardenHelpRequest{" +
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
            "}";
    }
}
