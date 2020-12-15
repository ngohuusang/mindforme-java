package com.mindforme.app.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.mindforme.app.domain.enumeration.SupportedHelpType;
import com.mindforme.app.domain.enumeration.TimeType;
import java.io.Serializable;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.elasticsearch.annotations.FieldType;

/**
 * A SupportedHelpRequest.
 */
@Entity
@Table(name = "supported_help_request")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@org.springframework.data.elasticsearch.annotations.Document(indexName = "supportedhelprequest")
public class SupportedHelpRequest implements Serializable {
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

    /**
     * 1= hours, 2= specific time
     */
    @Enumerated(EnumType.STRING)
    @Column(name = "time_type")
    private TimeType timeType;

    @Enumerated(EnumType.STRING)
    @Column(name = "supported_help_type")
    private SupportedHelpType supportedHelpType;

    @Column(name = "content")
    private String content;

    @Column(name = "other_task")
    private String otherTask;

    @ManyToMany
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JoinTable(
        name = "supported_help_request_supported",
        joinColumns = @JoinColumn(name = "supported_help_request_id", referencedColumnName = "id"),
        inverseJoinColumns = @JoinColumn(name = "supported_id", referencedColumnName = "id")
    )
    private Set<Supported> supporteds = new HashSet<>();

    @ManyToOne
    @JsonIgnoreProperties(value = "supportedRequests", allowSetters = true)
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

    public SupportedHelpRequest totalHelpTime(Long totalHelpTime) {
        this.totalHelpTime = totalHelpTime;
        return this;
    }

    public void setTotalHelpTime(Long totalHelpTime) {
        this.totalHelpTime = totalHelpTime;
    }

    public Instant getDateFrom() {
        return dateFrom;
    }

    public SupportedHelpRequest dateFrom(Instant dateFrom) {
        this.dateFrom = dateFrom;
        return this;
    }

    public void setDateFrom(Instant dateFrom) {
        this.dateFrom = dateFrom;
    }

    public Instant getDateTo() {
        return dateTo;
    }

    public SupportedHelpRequest dateTo(Instant dateTo) {
        this.dateTo = dateTo;
        return this;
    }

    public void setDateTo(Instant dateTo) {
        this.dateTo = dateTo;
    }

    public String getTimeFrom() {
        return timeFrom;
    }

    public SupportedHelpRequest timeFrom(String timeFrom) {
        this.timeFrom = timeFrom;
        return this;
    }

    public void setTimeFrom(String timeFrom) {
        this.timeFrom = timeFrom;
    }

    public String getTimeTo() {
        return timeTo;
    }

    public SupportedHelpRequest timeTo(String timeTo) {
        this.timeTo = timeTo;
        return this;
    }

    public void setTimeTo(String timeTo) {
        this.timeTo = timeTo;
    }

    public TimeType getTimeType() {
        return timeType;
    }

    public SupportedHelpRequest timeType(TimeType timeType) {
        this.timeType = timeType;
        return this;
    }

    public void setTimeType(TimeType timeType) {
        this.timeType = timeType;
    }

    public SupportedHelpType getSupportedHelpType() {
        return supportedHelpType;
    }

    public SupportedHelpRequest supportedHelpType(SupportedHelpType supportedHelpType) {
        this.supportedHelpType = supportedHelpType;
        return this;
    }

    public void setSupportedHelpType(SupportedHelpType supportedHelpType) {
        this.supportedHelpType = supportedHelpType;
    }

    public String getContent() {
        return content;
    }

    public SupportedHelpRequest content(String content) {
        this.content = content;
        return this;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getOtherTask() {
        return otherTask;
    }

    public SupportedHelpRequest otherTask(String otherTask) {
        this.otherTask = otherTask;
        return this;
    }

    public void setOtherTask(String otherTask) {
        this.otherTask = otherTask;
    }

    public Set<Supported> getSupporteds() {
        return supporteds;
    }

    public SupportedHelpRequest supporteds(Set<Supported> supporteds) {
        this.supporteds = supporteds;
        return this;
    }

    public SupportedHelpRequest addSupported(Supported supported) {
        this.supporteds.add(supported);
        supported.getHelpRequests().add(this);
        return this;
    }

    public SupportedHelpRequest removeSupported(Supported supported) {
        this.supporteds.remove(supported);
        supported.getHelpRequests().remove(this);
        return this;
    }

    public void setSupporteds(Set<Supported> supporteds) {
        this.supporteds = supporteds;
    }

    public HelpRequest getRequest() {
        return request;
    }

    public SupportedHelpRequest request(HelpRequest helpRequest) {
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
        if (!(o instanceof SupportedHelpRequest)) {
            return false;
        }
        return id != null && id.equals(((SupportedHelpRequest) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "SupportedHelpRequest{" +
            "id=" + getId() +
            ", totalHelpTime=" + getTotalHelpTime() +
            ", dateFrom='" + getDateFrom() + "'" +
            ", dateTo='" + getDateTo() + "'" +
            ", timeFrom='" + getTimeFrom() + "'" +
            ", timeTo='" + getTimeTo() + "'" +
            ", timeType='" + getTimeType() + "'" +
            ", supportedHelpType='" + getSupportedHelpType() + "'" +
            ", content='" + getContent() + "'" +
            ", otherTask='" + getOtherTask() + "'" +
            "}";
    }
}
