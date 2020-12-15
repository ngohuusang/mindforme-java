package com.mindforme.app.service.dto;

import java.io.Serializable;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link com.mindforme.app.domain.PetHelpRequest} entity.
 */
public class PetHelpRequestDTO implements Serializable {
    private Long id;

    private Long totalHelpTime;

    private Instant dateFrom;

    private Instant dateTo;

    @Size(max = 10)
    private String timeFrom;

    @Size(max = 10)
    private String timeTo;

    @Size(max = 255)
    private String content;

    private Set<PetDTO> pets = new HashSet<>();

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

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Set<PetDTO> getPets() {
        return pets;
    }

    public void setPets(Set<PetDTO> pets) {
        this.pets = pets;
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
        if (!(o instanceof PetHelpRequestDTO)) {
            return false;
        }

        return id != null && id.equals(((PetHelpRequestDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "PetHelpRequestDTO{" +
            "id=" + getId() +
            ", totalHelpTime=" + getTotalHelpTime() +
            ", dateFrom='" + getDateFrom() + "'" +
            ", dateTo='" + getDateTo() + "'" +
            ", timeFrom='" + getTimeFrom() + "'" +
            ", timeTo='" + getTimeTo() + "'" +
            ", content='" + getContent() + "'" +
            ", pets='" + getPets() + "'" +
            ", requestId=" + getRequestId() +
            "}";
    }
}
