package com.mindforme.app.service.dto;

import com.mindforme.app.domain.enumeration.SupportedHelpType;
import com.mindforme.app.domain.enumeration.TimeType;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link com.mindforme.app.domain.SupportedHelpRequest} entity.
 */
public class SupportedHelpRequestDTO implements Serializable {
    private Long id;

    private Long totalHelpTime;

    private Instant dateFrom;

    private Instant dateTo;

    @Size(max = 10)
    private String timeFrom;

    @Size(max = 10)
    private String timeTo;

    /**
     * 1= hours, 2= specific time
     */
    @ApiModelProperty(value = "1= hours, 2= specific time")
    private TimeType timeType;

    private SupportedHelpType supportedHelpType;

    private String content;

    private String otherTask;

    private Set<SupportedDTO> supporteds = new HashSet<>();

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

    public TimeType getTimeType() {
        return timeType;
    }

    public void setTimeType(TimeType timeType) {
        this.timeType = timeType;
    }

    public SupportedHelpType getSupportedHelpType() {
        return supportedHelpType;
    }

    public void setSupportedHelpType(SupportedHelpType supportedHelpType) {
        this.supportedHelpType = supportedHelpType;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getOtherTask() {
        return otherTask;
    }

    public void setOtherTask(String otherTask) {
        this.otherTask = otherTask;
    }

    public Set<SupportedDTO> getSupporteds() {
        return supporteds;
    }

    public void setSupporteds(Set<SupportedDTO> supporteds) {
        this.supporteds = supporteds;
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
        if (!(o instanceof SupportedHelpRequestDTO)) {
            return false;
        }

        return id != null && id.equals(((SupportedHelpRequestDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "SupportedHelpRequestDTO{" +
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
            ", supporteds='" + getSupporteds() + "'" +
            ", requestId=" + getRequestId() +
            "}";
    }
}
