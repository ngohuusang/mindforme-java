package com.mindforme.app.domain;

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
 * A ChildHelpRequest.
 */
@Entity
@Table(name = "child_help_request")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@org.springframework.data.elasticsearch.annotations.Document(indexName = "childhelprequest")
public class ChildHelpRequest implements Serializable {
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

    @Column(name = "content")
    private String content;

    @Column(name = "other_task")
    private String otherTask;

    @ManyToMany
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JoinTable(
        name = "child_help_request_child",
        joinColumns = @JoinColumn(name = "child_help_request_id", referencedColumnName = "id"),
        inverseJoinColumns = @JoinColumn(name = "child_id", referencedColumnName = "id")
    )
    private Set<Child> children = new HashSet<>();

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

    public ChildHelpRequest totalHelpTime(Long totalHelpTime) {
        this.totalHelpTime = totalHelpTime;
        return this;
    }

    public void setTotalHelpTime(Long totalHelpTime) {
        this.totalHelpTime = totalHelpTime;
    }

    public Instant getDateFrom() {
        return dateFrom;
    }

    public ChildHelpRequest dateFrom(Instant dateFrom) {
        this.dateFrom = dateFrom;
        return this;
    }

    public void setDateFrom(Instant dateFrom) {
        this.dateFrom = dateFrom;
    }

    public Instant getDateTo() {
        return dateTo;
    }

    public ChildHelpRequest dateTo(Instant dateTo) {
        this.dateTo = dateTo;
        return this;
    }

    public void setDateTo(Instant dateTo) {
        this.dateTo = dateTo;
    }

    public String getTimeFrom() {
        return timeFrom;
    }

    public ChildHelpRequest timeFrom(String timeFrom) {
        this.timeFrom = timeFrom;
        return this;
    }

    public void setTimeFrom(String timeFrom) {
        this.timeFrom = timeFrom;
    }

    public String getTimeTo() {
        return timeTo;
    }

    public ChildHelpRequest timeTo(String timeTo) {
        this.timeTo = timeTo;
        return this;
    }

    public void setTimeTo(String timeTo) {
        this.timeTo = timeTo;
    }

    public TimeType getTimeType() {
        return timeType;
    }

    public ChildHelpRequest timeType(TimeType timeType) {
        this.timeType = timeType;
        return this;
    }

    public void setTimeType(TimeType timeType) {
        this.timeType = timeType;
    }

    public String getContent() {
        return content;
    }

    public ChildHelpRequest content(String content) {
        this.content = content;
        return this;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getOtherTask() {
        return otherTask;
    }

    public ChildHelpRequest otherTask(String otherTask) {
        this.otherTask = otherTask;
        return this;
    }

    public void setOtherTask(String otherTask) {
        this.otherTask = otherTask;
    }

    public Set<Child> getChildren() {
        return children;
    }

    public ChildHelpRequest children(Set<Child> children) {
        this.children = children;
        return this;
    }

    public ChildHelpRequest addChild(Child child) {
        this.children.add(child);
        child.getHelpRequests().add(this);
        return this;
    }

    public ChildHelpRequest removeChild(Child child) {
        this.children.remove(child);
        child.getHelpRequests().remove(this);
        return this;
    }

    public void setChildren(Set<Child> children) {
        this.children = children;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ChildHelpRequest)) {
            return false;
        }
        return id != null && id.equals(((ChildHelpRequest) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ChildHelpRequest{" +
            "id=" + getId() +
            ", totalHelpTime=" + getTotalHelpTime() +
            ", dateFrom='" + getDateFrom() + "'" +
            ", dateTo='" + getDateTo() + "'" +
            ", timeFrom='" + getTimeFrom() + "'" +
            ", timeTo='" + getTimeTo() + "'" +
            ", timeType='" + getTimeType() + "'" +
            ", content='" + getContent() + "'" +
            ", otherTask='" + getOtherTask() + "'" +
            "}";
    }
}
