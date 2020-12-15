package com.mindforme.app.service.dto;

import java.io.Serializable;
import java.time.Instant;

/**
 * A DTO for the {@link com.mindforme.app.domain.Invitation} entity.
 */
public class InvitationDTO implements Serializable {
    private Long id;

    private String email;

    private String facebook;

    private Instant freePeriod;

    private Long userId;

    private String userLogin;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFacebook() {
        return facebook;
    }

    public void setFacebook(String facebook) {
        this.facebook = facebook;
    }

    public Instant getFreePeriod() {
        return freePeriod;
    }

    public void setFreePeriod(Instant freePeriod) {
        this.freePeriod = freePeriod;
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
        if (!(o instanceof InvitationDTO)) {
            return false;
        }

        return id != null && id.equals(((InvitationDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "InvitationDTO{" +
            "id=" + getId() +
            ", email='" + getEmail() + "'" +
            ", facebook='" + getFacebook() + "'" +
            ", freePeriod='" + getFreePeriod() + "'" +
            ", userId=" + getUserId() +
            ", userLogin='" + getUserLogin() + "'" +
            "}";
    }
}
