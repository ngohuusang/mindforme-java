package com.mindforme.app.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
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
 * A PetHelpRequest.
 */
@Entity
@Table(name = "pet_help_request")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@org.springframework.data.elasticsearch.annotations.Document(indexName = "pethelprequest")
public class PetHelpRequest implements Serializable {
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

    @Size(max = 255)
    @Column(name = "content", length = 255)
    private String content;

    @ManyToMany
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JoinTable(
        name = "pet_help_request_pet",
        joinColumns = @JoinColumn(name = "pet_help_request_id", referencedColumnName = "id"),
        inverseJoinColumns = @JoinColumn(name = "pet_id", referencedColumnName = "id")
    )
    private Set<Pet> pets = new HashSet<>();

    @ManyToOne
    @JsonIgnoreProperties(value = "petRequests", allowSetters = true)
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

    public PetHelpRequest totalHelpTime(Long totalHelpTime) {
        this.totalHelpTime = totalHelpTime;
        return this;
    }

    public void setTotalHelpTime(Long totalHelpTime) {
        this.totalHelpTime = totalHelpTime;
    }

    public Instant getDateFrom() {
        return dateFrom;
    }

    public PetHelpRequest dateFrom(Instant dateFrom) {
        this.dateFrom = dateFrom;
        return this;
    }

    public void setDateFrom(Instant dateFrom) {
        this.dateFrom = dateFrom;
    }

    public Instant getDateTo() {
        return dateTo;
    }

    public PetHelpRequest dateTo(Instant dateTo) {
        this.dateTo = dateTo;
        return this;
    }

    public void setDateTo(Instant dateTo) {
        this.dateTo = dateTo;
    }

    public String getTimeFrom() {
        return timeFrom;
    }

    public PetHelpRequest timeFrom(String timeFrom) {
        this.timeFrom = timeFrom;
        return this;
    }

    public void setTimeFrom(String timeFrom) {
        this.timeFrom = timeFrom;
    }

    public String getTimeTo() {
        return timeTo;
    }

    public PetHelpRequest timeTo(String timeTo) {
        this.timeTo = timeTo;
        return this;
    }

    public void setTimeTo(String timeTo) {
        this.timeTo = timeTo;
    }

    public String getContent() {
        return content;
    }

    public PetHelpRequest content(String content) {
        this.content = content;
        return this;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Set<Pet> getPets() {
        return pets;
    }

    public PetHelpRequest pets(Set<Pet> pets) {
        this.pets = pets;
        return this;
    }

    public PetHelpRequest addPet(Pet pet) {
        this.pets.add(pet);
        pet.getHelpRequests().add(this);
        return this;
    }

    public PetHelpRequest removePet(Pet pet) {
        this.pets.remove(pet);
        pet.getHelpRequests().remove(this);
        return this;
    }

    public void setPets(Set<Pet> pets) {
        this.pets = pets;
    }

    public HelpRequest getRequest() {
        return request;
    }

    public PetHelpRequest request(HelpRequest helpRequest) {
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
        if (!(o instanceof PetHelpRequest)) {
            return false;
        }
        return id != null && id.equals(((PetHelpRequest) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "PetHelpRequest{" +
            "id=" + getId() +
            ", totalHelpTime=" + getTotalHelpTime() +
            ", dateFrom='" + getDateFrom() + "'" +
            ", dateTo='" + getDateTo() + "'" +
            ", timeFrom='" + getTimeFrom() + "'" +
            ", timeTo='" + getTimeTo() + "'" +
            ", content='" + getContent() + "'" +
            "}";
    }
}
