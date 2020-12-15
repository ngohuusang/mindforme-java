package com.mindforme.app.service.mapper;

import com.mindforme.app.domain.*;
import com.mindforme.app.service.dto.UserIdentificationDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link UserIdentification} and its DTO {@link UserIdentificationDTO}.
 */
@Mapper(componentModel = "spring", uses = { UserMapper.class })
public interface UserIdentificationMapper extends EntityMapper<UserIdentificationDTO, UserIdentification> {
    @Mapping(source = "user.id", target = "userId")
    @Mapping(source = "user.login", target = "userLogin")
    UserIdentificationDTO toDto(UserIdentification userIdentification);

    @Mapping(source = "userId", target = "user")
    UserIdentification toEntity(UserIdentificationDTO userIdentificationDTO);

    default UserIdentification fromId(Long id) {
        if (id == null) {
            return null;
        }
        UserIdentification userIdentification = new UserIdentification();
        userIdentification.setId(id);
        return userIdentification;
    }
}
