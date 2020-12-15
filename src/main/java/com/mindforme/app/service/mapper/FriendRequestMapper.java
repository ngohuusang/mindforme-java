package com.mindforme.app.service.mapper;

import com.mindforme.app.domain.*;
import com.mindforme.app.service.dto.FriendRequestDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link FriendRequest} and its DTO {@link FriendRequestDTO}.
 */
@Mapper(componentModel = "spring", uses = { UserMapper.class })
public interface FriendRequestMapper extends EntityMapper<FriendRequestDTO, FriendRequest> {
    @Mapping(source = "friend.id", target = "friendId")
    @Mapping(source = "friend.login", target = "friendLogin")
    @Mapping(source = "user.id", target = "userId")
    @Mapping(source = "user.login", target = "userLogin")
    FriendRequestDTO toDto(FriendRequest friendRequest);

    @Mapping(source = "friendId", target = "friend")
    @Mapping(source = "userId", target = "user")
    FriendRequest toEntity(FriendRequestDTO friendRequestDTO);

    default FriendRequest fromId(Long id) {
        if (id == null) {
            return null;
        }
        FriendRequest friendRequest = new FriendRequest();
        friendRequest.setId(id);
        return friendRequest;
    }
}
