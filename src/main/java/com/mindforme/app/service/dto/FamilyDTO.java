package com.mindforme.app.service.dto;

import com.mindforme.app.domain.enumeration.DistanceUnit;
import com.mindforme.app.domain.enumeration.Frequency;
import com.mindforme.app.domain.enumeration.Privacy;
import java.io.Serializable;
import java.time.Instant;
import javax.persistence.Lob;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link com.mindforme.app.domain.Family} entity.
 */
public class FamilyDTO implements Serializable {
    private Long id;

    @Size(max = 45)
    private String name;

    private Integer karmaPoints;

    private String overview;

    private String rating;

    private String imageUrl;

    private Instant planExpire;

    @Lob
    private String rule;

    private String freeMonths;

    private Integer otherVerify;

    private Boolean kc25Paid;

    private Boolean kc75Paid;

    private Privacy privacyFamily;

    private Boolean shareToFacebook;

    private Privacy privacyPersonal;

    private Boolean addToCalendar;

    private Boolean remindEvents;

    private Boolean notifyFacebook;

    private Float distanceRequest;

    private DistanceUnit distanceUnit;

    private Frequency mailRequestFriend;

    private Frequency mailRequestFriendOfFriend;

    private Frequency mailRequest;

    private Long addressId;

    private String addressAddress;

    private Long planId;

    private String planName;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getKarmaPoints() {
        return karmaPoints;
    }

    public void setKarmaPoints(Integer karmaPoints) {
        this.karmaPoints = karmaPoints;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public Instant getPlanExpire() {
        return planExpire;
    }

    public void setPlanExpire(Instant planExpire) {
        this.planExpire = planExpire;
    }

    public String getRule() {
        return rule;
    }

    public void setRule(String rule) {
        this.rule = rule;
    }

    public String getFreeMonths() {
        return freeMonths;
    }

    public void setFreeMonths(String freeMonths) {
        this.freeMonths = freeMonths;
    }

    public Integer getOtherVerify() {
        return otherVerify;
    }

    public void setOtherVerify(Integer otherVerify) {
        this.otherVerify = otherVerify;
    }

    public Boolean isKc25Paid() {
        return kc25Paid;
    }

    public void setKc25Paid(Boolean kc25Paid) {
        this.kc25Paid = kc25Paid;
    }

    public Boolean isKc75Paid() {
        return kc75Paid;
    }

    public void setKc75Paid(Boolean kc75Paid) {
        this.kc75Paid = kc75Paid;
    }

    public Privacy getPrivacyFamily() {
        return privacyFamily;
    }

    public void setPrivacyFamily(Privacy privacyFamily) {
        this.privacyFamily = privacyFamily;
    }

    public Boolean isShareToFacebook() {
        return shareToFacebook;
    }

    public void setShareToFacebook(Boolean shareToFacebook) {
        this.shareToFacebook = shareToFacebook;
    }

    public Privacy getPrivacyPersonal() {
        return privacyPersonal;
    }

    public void setPrivacyPersonal(Privacy privacyPersonal) {
        this.privacyPersonal = privacyPersonal;
    }

    public Boolean isAddToCalendar() {
        return addToCalendar;
    }

    public void setAddToCalendar(Boolean addToCalendar) {
        this.addToCalendar = addToCalendar;
    }

    public Boolean isRemindEvents() {
        return remindEvents;
    }

    public void setRemindEvents(Boolean remindEvents) {
        this.remindEvents = remindEvents;
    }

    public Boolean isNotifyFacebook() {
        return notifyFacebook;
    }

    public void setNotifyFacebook(Boolean notifyFacebook) {
        this.notifyFacebook = notifyFacebook;
    }

    public Float getDistanceRequest() {
        return distanceRequest;
    }

    public void setDistanceRequest(Float distanceRequest) {
        this.distanceRequest = distanceRequest;
    }

    public DistanceUnit getDistanceUnit() {
        return distanceUnit;
    }

    public void setDistanceUnit(DistanceUnit distanceUnit) {
        this.distanceUnit = distanceUnit;
    }

    public Frequency getMailRequestFriend() {
        return mailRequestFriend;
    }

    public void setMailRequestFriend(Frequency mailRequestFriend) {
        this.mailRequestFriend = mailRequestFriend;
    }

    public Frequency getMailRequestFriendOfFriend() {
        return mailRequestFriendOfFriend;
    }

    public void setMailRequestFriendOfFriend(Frequency mailRequestFriendOfFriend) {
        this.mailRequestFriendOfFriend = mailRequestFriendOfFriend;
    }

    public Frequency getMailRequest() {
        return mailRequest;
    }

    public void setMailRequest(Frequency mailRequest) {
        this.mailRequest = mailRequest;
    }

    public Long getAddressId() {
        return addressId;
    }

    public void setAddressId(Long addressId) {
        this.addressId = addressId;
    }

    public String getAddressAddress() {
        return addressAddress;
    }

    public void setAddressAddress(String addressAddress) {
        this.addressAddress = addressAddress;
    }

    public Long getPlanId() {
        return planId;
    }

    public void setPlanId(Long planId) {
        this.planId = planId;
    }

    public String getPlanName() {
        return planName;
    }

    public void setPlanName(String planName) {
        this.planName = planName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof FamilyDTO)) {
            return false;
        }

        return id != null && id.equals(((FamilyDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "FamilyDTO{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", karmaPoints=" + getKarmaPoints() +
            ", overview='" + getOverview() + "'" +
            ", rating='" + getRating() + "'" +
            ", imageUrl='" + getImageUrl() + "'" +
            ", planExpire='" + getPlanExpire() + "'" +
            ", rule='" + getRule() + "'" +
            ", freeMonths='" + getFreeMonths() + "'" +
            ", otherVerify=" + getOtherVerify() +
            ", kc25Paid='" + isKc25Paid() + "'" +
            ", kc75Paid='" + isKc75Paid() + "'" +
            ", privacyFamily='" + getPrivacyFamily() + "'" +
            ", shareToFacebook='" + isShareToFacebook() + "'" +
            ", privacyPersonal='" + getPrivacyPersonal() + "'" +
            ", addToCalendar='" + isAddToCalendar() + "'" +
            ", remindEvents='" + isRemindEvents() + "'" +
            ", notifyFacebook='" + isNotifyFacebook() + "'" +
            ", distanceRequest=" + getDistanceRequest() +
            ", distanceUnit='" + getDistanceUnit() + "'" +
            ", mailRequestFriend='" + getMailRequestFriend() + "'" +
            ", mailRequestFriendOfFriend='" + getMailRequestFriendOfFriend() + "'" +
            ", mailRequest='" + getMailRequest() + "'" +
            ", addressId=" + getAddressId() +
            ", addressAddress='" + getAddressAddress() + "'" +
            ", planId=" + getPlanId() +
            ", planName='" + getPlanName() + "'" +
            "}";
    }
}
