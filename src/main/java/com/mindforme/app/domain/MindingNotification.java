package com.mindforme.app.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.mindforme.app.domain.enumeration.Frequency;
import com.mindforme.app.domain.enumeration.Privacy;
import com.mindforme.app.domain.enumeration.Status;
import java.io.Serializable;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.elasticsearch.annotations.FieldType;

/**
 * A MindingNotification.
 */
@Entity
@Table(name = "minding_notification")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@org.springframework.data.elasticsearch.annotations.Document(indexName = "mindingnotification")
public class MindingNotification implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * F for friends, FF for friends of friends, O for others.
     */
    @Enumerated(EnumType.STRING)
    @Column(name = "type")
    private Privacy type;

    @Lob
    @Column(name = "minding")
    private String minding;

    @Column(name = "push_notification")
    private Boolean pushNotification;

    /**
     * D for daily , W for weekly and I for immediately
     */
    @Enumerated(EnumType.STRING)
    @Column(name = "email")
    private Frequency email;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private Status status;

    @ManyToOne
    @JsonIgnoreProperties(value = "mindingNotifications", allowSetters = true)
    private Family family;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Privacy getType() {
        return type;
    }

    public MindingNotification type(Privacy type) {
        this.type = type;
        return this;
    }

    public void setType(Privacy type) {
        this.type = type;
    }

    public String getMinding() {
        return minding;
    }

    public MindingNotification minding(String minding) {
        this.minding = minding;
        return this;
    }

    public void setMinding(String minding) {
        this.minding = minding;
    }

    public Boolean isPushNotification() {
        return pushNotification;
    }

    public MindingNotification pushNotification(Boolean pushNotification) {
        this.pushNotification = pushNotification;
        return this;
    }

    public void setPushNotification(Boolean pushNotification) {
        this.pushNotification = pushNotification;
    }

    public Frequency getEmail() {
        return email;
    }

    public MindingNotification email(Frequency email) {
        this.email = email;
        return this;
    }

    public void setEmail(Frequency email) {
        this.email = email;
    }

    public Status getStatus() {
        return status;
    }

    public MindingNotification status(Status status) {
        this.status = status;
        return this;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public Family getFamily() {
        return family;
    }

    public MindingNotification family(Family family) {
        this.family = family;
        return this;
    }

    public void setFamily(Family family) {
        this.family = family;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof MindingNotification)) {
            return false;
        }
        return id != null && id.equals(((MindingNotification) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "MindingNotification{" +
            "id=" + getId() +
            ", type='" + getType() + "'" +
            ", minding='" + getMinding() + "'" +
            ", pushNotification='" + isPushNotification() + "'" +
            ", email='" + getEmail() + "'" +
            ", status='" + getStatus() + "'" +
            "}";
    }
}
