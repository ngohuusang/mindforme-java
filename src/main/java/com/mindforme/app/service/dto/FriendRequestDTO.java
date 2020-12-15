package com.mindforme.app.service.dto;

import com.mindforme.app.domain.enumeration.FriendRequestStatus;
import java.io.Serializable;

/**
 * A DTO for the {@link com.mindforme.app.domain.FriendRequest} entity.
 */
public class FriendRequestDTO implements Serializable {
    private Long id;

    private FriendRequestStatus status;

    private Long friendId;

    private String friendLogin;

    private Long userId;

    private String userLogin;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public FriendRequestStatus getStatus() {
        return status;
    }

    public void setStatus(FriendRequestStatus status) {
        this.status = status;
    }

    public Long getFriendId() {
        return friendId;
    }

    public void setFriendId(Long userId) {
        this.friendId = userId;
    }

    public String getFriendLogin() {
        return friendLogin;
    }

    public void setFriendLogin(String userLogin) {
        this.friendLogin = userLogin;
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
        if (!(o instanceof FriendRequestDTO)) {
            return false;
        }

        return id != null && id.equals(((FriendRequestDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "FriendRequestDTO{" +
            "id=" + getId() +
            ", status='" + getStatus() + "'" +
            ", friendId=" + getFriendId() +
            ", friendLogin='" + getFriendLogin() + "'" +
            ", userId=" + getUserId() +
            ", userLogin='" + getUserLogin() + "'" +
            "}";
    }
}
