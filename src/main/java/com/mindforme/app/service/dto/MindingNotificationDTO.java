package com.mindforme.app.service.dto;

import com.mindforme.app.domain.enumeration.Frequency;
import com.mindforme.app.domain.enumeration.Privacy;
import com.mindforme.app.domain.enumeration.Status;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import javax.persistence.Lob;

/**
 * A DTO for the {@link com.mindforme.app.domain.MindingNotification} entity.
 */
public class MindingNotificationDTO implements Serializable {
    private Long id;

    /**
     * F for friends, FF for friends of friends, O for others.
     */
    @ApiModelProperty(value = "F for friends, FF for friends of friends, O for others.")
    private Privacy type;

    @Lob
    private String minding;

    private Boolean pushNotification;

    /**
     * D for daily , W for weekly and I for immediately
     */
    @ApiModelProperty(value = "D for daily , W for weekly and I for immediately")
    private Frequency email;

    private Status status;

    private Long familyId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Privacy getType() {
        return type;
    }

    public void setType(Privacy type) {
        this.type = type;
    }

    public String getMinding() {
        return minding;
    }

    public void setMinding(String minding) {
        this.minding = minding;
    }

    public Boolean isPushNotification() {
        return pushNotification;
    }

    public void setPushNotification(Boolean pushNotification) {
        this.pushNotification = pushNotification;
    }

    public Frequency getEmail() {
        return email;
    }

    public void setEmail(Frequency email) {
        this.email = email;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public Long getFamilyId() {
        return familyId;
    }

    public void setFamilyId(Long familyId) {
        this.familyId = familyId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof MindingNotificationDTO)) {
            return false;
        }

        return id != null && id.equals(((MindingNotificationDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "MindingNotificationDTO{" +
            "id=" + getId() +
            ", type='" + getType() + "'" +
            ", minding='" + getMinding() + "'" +
            ", pushNotification='" + isPushNotification() + "'" +
            ", email='" + getEmail() + "'" +
            ", status='" + getStatus() + "'" +
            ", familyId=" + getFamilyId() +
            "}";
    }
}
