package com.mindforme.app.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.mindforme.app.domain.enumeration.HelpLocation;
import com.mindforme.app.domain.enumeration.HelpRequestStatus;
import com.mindforme.app.domain.enumeration.HelpType;
import com.mindforme.app.domain.enumeration.Privacy;
import com.mindforme.app.domain.enumeration.Status;
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
 * A HelpRequest.
 */
@Entity
@Table(name = "help_request")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@org.springframework.data.elasticsearch.annotations.Document(indexName = "helprequest")
public class HelpRequest implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "title")
    private String title;

    @Enumerated(EnumType.STRING)
    @Column(name = "type")
    private HelpType type;

    @Size(max = 255)
    @Column(name = "acceptance", length = 255)
    private String acceptance;

    /**
     * N for New , A for accepted, AP for approved and c for complete.,R-rated/Completelu done
     */
    @Enumerated(EnumType.STRING)
    @Column(name = "request_status")
    private HelpRequestStatus requestStatus;

    @Column(name = "is_offer")
    private Boolean isOffer;

    @Enumerated(EnumType.STRING)
    @Column(name = "offer_to")
    private Privacy offerTo;

    @Lob
    @Column(name = "message")
    private String message;

    @Lob
    @Column(name = "instruction")
    private String instruction;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private Status status;

    /**
     * 0 for at our home or your home1 for at our home2 for at your home3 for Other
     */
    @Enumerated(EnumType.STRING)
    @Column(name = "location_type")
    private HelpLocation locationType;

    @Column(name = "real_start")
    private Instant realStart;

    @Column(name = "real_end")
    private Instant realEnd;

    @Column(name = "rating")
    private Integer rating;

    @Column(name = "requester_comment")
    private String requesterComment;

    @Column(name = "helper_comment")
    private String helperComment;

    @Column(name = "flagged")
    private Integer flagged;

    @Column(name = "coins")
    private Float coins;

    @Column(name = "bonus")
    private Float bonus;

    @OneToMany(mappedBy = "request")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    private Set<GardenHelpRequest> gardenRequests = new HashSet<>();

    @OneToMany(mappedBy = "request")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    private Set<HouseHelpRequest> houseRequests = new HashSet<>();

    @OneToMany(mappedBy = "request")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    private Set<PetHelpRequest> petRequests = new HashSet<>();

    @OneToMany(mappedBy = "request")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    private Set<SupportedHelpRequest> supportedRequests = new HashSet<>();

    @OneToMany(mappedBy = "request")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    private Set<Child> childRequests = new HashSet<>();

    @ManyToOne
    @JsonIgnoreProperties(value = "helpRequests", allowSetters = true)
    private Address helpLocation;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public HelpRequest title(String title) {
        this.title = title;
        return this;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public HelpType getType() {
        return type;
    }

    public HelpRequest type(HelpType type) {
        this.type = type;
        return this;
    }

    public void setType(HelpType type) {
        this.type = type;
    }

    public String getAcceptance() {
        return acceptance;
    }

    public HelpRequest acceptance(String acceptance) {
        this.acceptance = acceptance;
        return this;
    }

    public void setAcceptance(String acceptance) {
        this.acceptance = acceptance;
    }

    public HelpRequestStatus getRequestStatus() {
        return requestStatus;
    }

    public HelpRequest requestStatus(HelpRequestStatus requestStatus) {
        this.requestStatus = requestStatus;
        return this;
    }

    public void setRequestStatus(HelpRequestStatus requestStatus) {
        this.requestStatus = requestStatus;
    }

    public Boolean isIsOffer() {
        return isOffer;
    }

    public HelpRequest isOffer(Boolean isOffer) {
        this.isOffer = isOffer;
        return this;
    }

    public void setIsOffer(Boolean isOffer) {
        this.isOffer = isOffer;
    }

    public Privacy getOfferTo() {
        return offerTo;
    }

    public HelpRequest offerTo(Privacy offerTo) {
        this.offerTo = offerTo;
        return this;
    }

    public void setOfferTo(Privacy offerTo) {
        this.offerTo = offerTo;
    }

    public String getMessage() {
        return message;
    }

    public HelpRequest message(String message) {
        this.message = message;
        return this;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getInstruction() {
        return instruction;
    }

    public HelpRequest instruction(String instruction) {
        this.instruction = instruction;
        return this;
    }

    public void setInstruction(String instruction) {
        this.instruction = instruction;
    }

    public Status getStatus() {
        return status;
    }

    public HelpRequest status(Status status) {
        this.status = status;
        return this;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public HelpLocation getLocationType() {
        return locationType;
    }

    public HelpRequest locationType(HelpLocation locationType) {
        this.locationType = locationType;
        return this;
    }

    public void setLocationType(HelpLocation locationType) {
        this.locationType = locationType;
    }

    public Instant getRealStart() {
        return realStart;
    }

    public HelpRequest realStart(Instant realStart) {
        this.realStart = realStart;
        return this;
    }

    public void setRealStart(Instant realStart) {
        this.realStart = realStart;
    }

    public Instant getRealEnd() {
        return realEnd;
    }

    public HelpRequest realEnd(Instant realEnd) {
        this.realEnd = realEnd;
        return this;
    }

    public void setRealEnd(Instant realEnd) {
        this.realEnd = realEnd;
    }

    public Integer getRating() {
        return rating;
    }

    public HelpRequest rating(Integer rating) {
        this.rating = rating;
        return this;
    }

    public void setRating(Integer rating) {
        this.rating = rating;
    }

    public String getRequesterComment() {
        return requesterComment;
    }

    public HelpRequest requesterComment(String requesterComment) {
        this.requesterComment = requesterComment;
        return this;
    }

    public void setRequesterComment(String requesterComment) {
        this.requesterComment = requesterComment;
    }

    public String getHelperComment() {
        return helperComment;
    }

    public HelpRequest helperComment(String helperComment) {
        this.helperComment = helperComment;
        return this;
    }

    public void setHelperComment(String helperComment) {
        this.helperComment = helperComment;
    }

    public Integer getFlagged() {
        return flagged;
    }

    public HelpRequest flagged(Integer flagged) {
        this.flagged = flagged;
        return this;
    }

    public void setFlagged(Integer flagged) {
        this.flagged = flagged;
    }

    public Float getCoins() {
        return coins;
    }

    public HelpRequest coins(Float coins) {
        this.coins = coins;
        return this;
    }

    public void setCoins(Float coins) {
        this.coins = coins;
    }

    public Float getBonus() {
        return bonus;
    }

    public HelpRequest bonus(Float bonus) {
        this.bonus = bonus;
        return this;
    }

    public void setBonus(Float bonus) {
        this.bonus = bonus;
    }

    public Set<GardenHelpRequest> getGardenRequests() {
        return gardenRequests;
    }

    public HelpRequest gardenRequests(Set<GardenHelpRequest> gardenHelpRequests) {
        this.gardenRequests = gardenHelpRequests;
        return this;
    }

    public HelpRequest addGardenRequest(GardenHelpRequest gardenHelpRequest) {
        this.gardenRequests.add(gardenHelpRequest);
        gardenHelpRequest.setRequest(this);
        return this;
    }

    public HelpRequest removeGardenRequest(GardenHelpRequest gardenHelpRequest) {
        this.gardenRequests.remove(gardenHelpRequest);
        gardenHelpRequest.setRequest(null);
        return this;
    }

    public void setGardenRequests(Set<GardenHelpRequest> gardenHelpRequests) {
        this.gardenRequests = gardenHelpRequests;
    }

    public Set<HouseHelpRequest> getHouseRequests() {
        return houseRequests;
    }

    public HelpRequest houseRequests(Set<HouseHelpRequest> houseHelpRequests) {
        this.houseRequests = houseHelpRequests;
        return this;
    }

    public HelpRequest addHouseRequest(HouseHelpRequest houseHelpRequest) {
        this.houseRequests.add(houseHelpRequest);
        houseHelpRequest.setRequest(this);
        return this;
    }

    public HelpRequest removeHouseRequest(HouseHelpRequest houseHelpRequest) {
        this.houseRequests.remove(houseHelpRequest);
        houseHelpRequest.setRequest(null);
        return this;
    }

    public void setHouseRequests(Set<HouseHelpRequest> houseHelpRequests) {
        this.houseRequests = houseHelpRequests;
    }

    public Set<PetHelpRequest> getPetRequests() {
        return petRequests;
    }

    public HelpRequest petRequests(Set<PetHelpRequest> petHelpRequests) {
        this.petRequests = petHelpRequests;
        return this;
    }

    public HelpRequest addPetRequest(PetHelpRequest petHelpRequest) {
        this.petRequests.add(petHelpRequest);
        petHelpRequest.setRequest(this);
        return this;
    }

    public HelpRequest removePetRequest(PetHelpRequest petHelpRequest) {
        this.petRequests.remove(petHelpRequest);
        petHelpRequest.setRequest(null);
        return this;
    }

    public void setPetRequests(Set<PetHelpRequest> petHelpRequests) {
        this.petRequests = petHelpRequests;
    }

    public Set<SupportedHelpRequest> getSupportedRequests() {
        return supportedRequests;
    }

    public HelpRequest supportedRequests(Set<SupportedHelpRequest> supportedHelpRequests) {
        this.supportedRequests = supportedHelpRequests;
        return this;
    }

    public HelpRequest addSupportedRequest(SupportedHelpRequest supportedHelpRequest) {
        this.supportedRequests.add(supportedHelpRequest);
        supportedHelpRequest.setRequest(this);
        return this;
    }

    public HelpRequest removeSupportedRequest(SupportedHelpRequest supportedHelpRequest) {
        this.supportedRequests.remove(supportedHelpRequest);
        supportedHelpRequest.setRequest(null);
        return this;
    }

    public void setSupportedRequests(Set<SupportedHelpRequest> supportedHelpRequests) {
        this.supportedRequests = supportedHelpRequests;
    }

    public Set<Child> getChildRequests() {
        return childRequests;
    }

    public HelpRequest childRequests(Set<Child> children) {
        this.childRequests = children;
        return this;
    }

    public HelpRequest addChildRequest(Child child) {
        this.childRequests.add(child);
        child.setRequest(this);
        return this;
    }

    public HelpRequest removeChildRequest(Child child) {
        this.childRequests.remove(child);
        child.setRequest(null);
        return this;
    }

    public void setChildRequests(Set<Child> children) {
        this.childRequests = children;
    }

    public Address getHelpLocation() {
        return helpLocation;
    }

    public HelpRequest helpLocation(Address address) {
        this.helpLocation = address;
        return this;
    }

    public void setHelpLocation(Address address) {
        this.helpLocation = address;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof HelpRequest)) {
            return false;
        }
        return id != null && id.equals(((HelpRequest) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "HelpRequest{" +
            "id=" + getId() +
            ", title='" + getTitle() + "'" +
            ", type='" + getType() + "'" +
            ", acceptance='" + getAcceptance() + "'" +
            ", requestStatus='" + getRequestStatus() + "'" +
            ", isOffer='" + isIsOffer() + "'" +
            ", offerTo='" + getOfferTo() + "'" +
            ", message='" + getMessage() + "'" +
            ", instruction='" + getInstruction() + "'" +
            ", status='" + getStatus() + "'" +
            ", locationType='" + getLocationType() + "'" +
            ", realStart='" + getRealStart() + "'" +
            ", realEnd='" + getRealEnd() + "'" +
            ", rating=" + getRating() +
            ", requesterComment='" + getRequesterComment() + "'" +
            ", helperComment='" + getHelperComment() + "'" +
            ", flagged=" + getFlagged() +
            ", coins=" + getCoins() +
            ", bonus=" + getBonus() +
            "}";
    }
}
