package com.mindforme.app.service.mapper;

import com.mindforme.app.domain.*;
import com.mindforme.app.service.dto.FriendshipDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Friendship} and its DTO {@link FriendshipDTO}.
 */
@Mapper(componentModel = "spring", uses = { UserMapper.class, FriendshipGroupMapper.class })
public interface FriendshipMapper extends EntityMapper<FriendshipDTO, Friendship> {
    @Mapping(source = "friend.id", target = "friendId")
    @Mapping(source = "friend.login", target = "friendLogin")
    @Mapping(source = "user.id", target = "userId")
    @Mapping(source = "user.login", target = "userLogin")
    @Mapping(source = "group.id", target = "groupId")
    @Mapping(source = "group.name", target = "groupName")
    FriendshipDTO toDto(Friendship friendship);

    @Mapping(source = "friendId", target = "friend")
    @Mapping(source = "userId", target = "user")
    @Mapping(source = "groupId", target = "group")
    Friendship toEntity(FriendshipDTO friendshipDTO);

    default Friendship fromId(Long id) {
        if (id == null) {
            return null;
        }
        Friendship friendship = new Friendship();
        friendship.setId(id);
        return friendship;
    }
}
