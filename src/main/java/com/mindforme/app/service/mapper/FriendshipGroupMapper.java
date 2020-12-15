package com.mindforme.app.service.mapper;

import com.mindforme.app.domain.*;
import com.mindforme.app.service.dto.FriendshipGroupDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link FriendshipGroup} and its DTO {@link FriendshipGroupDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface FriendshipGroupMapper extends EntityMapper<FriendshipGroupDTO, FriendshipGroup> {
    @Mapping(target = "friendships", ignore = true)
    @Mapping(target = "removeFriendship", ignore = true)
    FriendshipGroup toEntity(FriendshipGroupDTO friendshipGroupDTO);

    default FriendshipGroup fromId(Long id) {
        if (id == null) {
            return null;
        }
        FriendshipGroup friendshipGroup = new FriendshipGroup();
        friendshipGroup.setId(id);
        return friendshipGroup;
    }
}
