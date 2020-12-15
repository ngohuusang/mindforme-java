package com.mindforme.app.service.mapper;

import com.mindforme.app.domain.*;
import com.mindforme.app.service.dto.InvitationDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Invitation} and its DTO {@link InvitationDTO}.
 */
@Mapper(componentModel = "spring", uses = { UserMapper.class })
public interface InvitationMapper extends EntityMapper<InvitationDTO, Invitation> {
    @Mapping(source = "user.id", target = "userId")
    @Mapping(source = "user.login", target = "userLogin")
    InvitationDTO toDto(Invitation invitation);

    @Mapping(source = "userId", target = "user")
    Invitation toEntity(InvitationDTO invitationDTO);

    default Invitation fromId(Long id) {
        if (id == null) {
            return null;
        }
        Invitation invitation = new Invitation();
        invitation.setId(id);
        return invitation;
    }
}
