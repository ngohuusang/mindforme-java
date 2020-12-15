package com.mindforme.app.service.dto;

import com.mindforme.app.domain.enumeration.CardType;
import java.io.Serializable;

/**
 * A DTO for the {@link com.mindforme.app.domain.Payment} entity.
 */
public class PaymentDTO implements Serializable {
    private Long id;

    private String lastFour;

    private String expireDate;

    private String tripeCustomerId;

    private CardType cardType;

    private Long familyId;

    private String familyName;

    private Long userId;

    private String userLogin;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLastFour() {
        return lastFour;
    }

    public void setLastFour(String lastFour) {
        this.lastFour = lastFour;
    }

    public String getExpireDate() {
        return expireDate;
    }

    public void setExpireDate(String expireDate) {
        this.expireDate = expireDate;
    }

    public String getTripeCustomerId() {
        return tripeCustomerId;
    }

    public void setTripeCustomerId(String tripeCustomerId) {
        this.tripeCustomerId = tripeCustomerId;
    }

    public CardType getCardType() {
        return cardType;
    }

    public void setCardType(CardType cardType) {
        this.cardType = cardType;
    }

    public Long getFamilyId() {
        return familyId;
    }

    public void setFamilyId(Long familyId) {
        this.familyId = familyId;
    }

    public String getFamilyName() {
        return familyName;
    }

    public void setFamilyName(String familyName) {
        this.familyName = familyName;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getUserLogin() {
        return userLogin;
    }

    public void setUserLogin(String userLogin) {
        this.userLogin = userLogin;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof PaymentDTO)) {
            return false;
        }

        return id != null && id.equals(((PaymentDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "PaymentDTO{" +
            "id=" + getId() +
            ", lastFour='" + getLastFour() + "'" +
            ", expireDate='" + getExpireDate() + "'" +
            ", tripeCustomerId='" + getTripeCustomerId() + "'" +
            ", cardType='" + getCardType() + "'" +
            ", familyId=" + getFamilyId() +
            ", familyName='" + getFamilyName() + "'" +
            ", userId=" + getUserId() +
            ", userLogin='" + getUserLogin() + "'" +
            "}";
    }
}
