package com.mindforme.app.service.dto;

import java.io.Serializable;

/**
 * A DTO for the {@link com.mindforme.app.domain.Friendship} entity.
 */
public class FriendshipDTO implements Serializable {
    private Long id;

    private Long friendId;

    private String friendLogin;

    private Long userId;

    private String userLogin;

    private Long groupId;

    private String groupName;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public Long getGroupId() {
        return groupId;
    }

    public void setGroupId(Long friendshipGroupId) {
        this.groupId = friendshipGroupId;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String friendshipGroupName) {
        this.groupName = friendshipGroupName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof FriendshipDTO)) {
            return false;
        }

        return id != null && id.equals(((FriendshipDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "FriendshipDTO{" +
            "id=" + getId() +
            ", friendId=" + getFriendId() +
            ", friendLogin='" + getFriendLogin() + "'" +
            ", userId=" + getUserId() +
            ", userLogin='" + getUserLogin() + "'" +
            ", groupId=" + getGroupId() +
            ", groupName='" + getGroupName() + "'" +
            "}";
    }
}
