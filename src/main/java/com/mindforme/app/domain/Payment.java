package com.mindforme.app.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.mindforme.app.domain.enumeration.CardType;
import java.io.Serializable;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.elasticsearch.annotations.FieldType;

/**
 * A Payment.
 */
@Entity
@Table(name = "payment")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@org.springframework.data.elasticsearch.annotations.Document(indexName = "payment")
public class Payment implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "last_four")
    private String lastFour;

    @Column(name = "expire_date")
    private String expireDate;

    @Column(name = "tripe_customer_id")
    private String tripeCustomerId;

    @Enumerated(EnumType.STRING)
    @Column(name = "card_type")
    private CardType cardType;

    @ManyToOne
    @JsonIgnoreProperties(value = "payments", allowSetters = true)
    private Family family;

    @ManyToOne
    @JsonIgnoreProperties(value = "payments", allowSetters = true)
    private User user;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLastFour() {
        return lastFour;
    }

    public Payment lastFour(String lastFour) {
        this.lastFour = lastFour;
        return this;
    }

    public void setLastFour(String lastFour) {
        this.lastFour = lastFour;
    }

    public String getExpireDate() {
        return expireDate;
    }

    public Payment expireDate(String expireDate) {
        this.expireDate = expireDate;
        return this;
    }

    public void setExpireDate(String expireDate) {
        this.expireDate = expireDate;
    }

    public String getTripeCustomerId() {
        return tripeCustomerId;
    }

    public Payment tripeCustomerId(String tripeCustomerId) {
        this.tripeCustomerId = tripeCustomerId;
        return this;
    }

    public void setTripeCustomerId(String tripeCustomerId) {
        this.tripeCustomerId = tripeCustomerId;
    }

    public CardType getCardType() {
        return cardType;
    }

    public Payment cardType(CardType cardType) {
        this.cardType = cardType;
        return this;
    }

    public void setCardType(CardType cardType) {
        this.cardType = cardType;
    }

    public Family getFamily() {
        return family;
    }

    public Payment family(Family family) {
        this.family = family;
        return this;
    }

    public void setFamily(Family family) {
        this.family = family;
    }

    public User getUser() {
        return user;
    }

    public Payment user(User user) {
        this.user = user;
        return this;
    }

    public void setUser(User user) {
        this.user = user;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Payment)) {
            return false;
        }
        return id != null && id.equals(((Payment) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Payment{" +
            "id=" + getId() +
            ", lastFour='" + getLastFour() + "'" +
            ", expireDate='" + getExpireDate() + "'" +
            ", tripeCustomerId='" + getTripeCustomerId() + "'" +
            ", cardType='" + getCardType() + "'" +
            "}";
    }
}
