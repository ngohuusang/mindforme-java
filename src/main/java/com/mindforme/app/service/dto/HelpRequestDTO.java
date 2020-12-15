package com.mindforme.app.service.dto;

import com.mindforme.app.domain.enumeration.HelpLocation;
import com.mindforme.app.domain.enumeration.HelpRequestStatus;
import com.mindforme.app.domain.enumeration.HelpType;
import com.mindforme.app.domain.enumeration.Privacy;
import com.mindforme.app.domain.enumeration.Status;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import java.time.Instant;
import javax.persistence.Lob;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link com.mindforme.app.domain.HelpRequest} entity.
 */
public class HelpRequestDTO implements Serializable {
    private Long id;

    private String title;

    private HelpType type;

    @Size(max = 255)
    private String acceptance;

    /**
     * N for New , A for accepted, AP for approved and c for complete.,R-rated/Completelu done
     */
    @ApiModelProperty(value = "N for New , A for accepted, AP for approved and c for complete.,R-rated/Completelu done")
    private HelpRequestStatus requestStatus;

    private Boolean isOffer;

    private Privacy offerTo;

    @Lob
    private String message;

    @Lob
    private String instruction;

    private Status status;

    /**
     * 0 for at our home or your home1 for at our home2 for at your home3 for Other
     */
    @ApiModelProperty(value = "0 for at our home or your home1 for at our home2 for at your home3 for Other")
    private HelpLocation locationType;

    private Instant realStart;

    private Instant realEnd;

    private Integer rating;

    private String requesterComment;

    private String helperComment;

    private Integer flagged;

    private Float coins;

    private Float bonus;

    private Long helpLocationId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public HelpType getType() {
        return type;
    }

    public void setType(HelpType type) {
        this.type = type;
    }

    public String getAcceptance() {
        return acceptance;
    }

    public void setAcceptance(String acceptance) {
        this.acceptance = acceptance;
    }

    public HelpRequestStatus getRequestStatus() {
        return requestStatus;
    }

    public void setRequestStatus(HelpRequestStatus requestStatus) {
        this.requestStatus = requestStatus;
    }

    public Boolean isIsOffer() {
        return isOffer;
    }

    public void setIsOffer(Boolean isOffer) {
        this.isOffer = isOffer;
    }

    public Privacy getOfferTo() {
        return offerTo;
    }

    public void setOfferTo(Privacy offerTo) {
        this.offerTo = offerTo;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getInstruction() {
        return instruction;
    }

    public void setInstruction(String instruction) {
        this.instruction = instruction;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public HelpLocation getLocationType() {
        return locationType;
    }

    public void setLocationType(HelpLocation locationType) {
        this.locationType = locationType;
    }

    public Instant getRealStart() {
        return realStart;
    }

    public void setRealStart(Instant realStart) {
        this.realStart = realStart;
    }

    public Instant getRealEnd() {
        return realEnd;
    }

    public void setRealEnd(Instant realEnd) {
        this.realEnd = realEnd;
    }

    public Integer getRating() {
        return rating;
    }

    public void setRating(Integer rating) {
        this.rating = rating;
    }

    public String getRequesterComment() {
        return requesterComment;
    }

    public void setRequesterComment(String requesterComment) {
        this.requesterComment = requesterComment;
    }

    public String getHelperComment() {
        return helperComment;
    }

    public void setHelperComment(String helperComment) {
        this.helperComment = helperComment;
    }

    public Integer getFlagged() {
        return flagged;
    }

    public void setFlagged(Integer flagged) {
        this.flagged = flagged;
    }

    public Float getCoins() {
        return coins;
    }

    public void setCoins(Float coins) {
        this.coins = coins;
    }

    public Float getBonus() {
        return bonus;
    }

    public void setBonus(Float bonus) {
        this.bonus = bonus;
    }

    public Long getHelpLocationId() {
        return helpLocationId;
    }

    public void setHelpLocationId(Long addressId) {
        this.helpLocationId = addressId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof HelpRequestDTO)) {
            return false;
        }

        return id != null && id.equals(((HelpRequestDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "HelpRequestDTO{" +
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
            ", helpLocationId=" + getHelpLocationId() +
            "}";
    }
}
