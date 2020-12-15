package com.mindforme.app.service.mapper;

import com.mindforme.app.domain.*;
import com.mindforme.app.service.dto.MindingNotificationDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link MindingNotification} and its DTO {@link MindingNotificationDTO}.
 */
@Mapper(componentModel = "spring", uses = { FamilyMapper.class })
public interface MindingNotificationMapper extends EntityMapper<MindingNotificationDTO, MindingNotification> {
    @Mapping(source = "family.id", target = "familyId")
    MindingNotificationDTO toDto(MindingNotification mindingNotification);

    @Mapping(source = "familyId", target = "family")
    MindingNotification toEntity(MindingNotificationDTO mindingNotificationDTO);

    default MindingNotification fromId(Long id) {
        if (id == null) {
            return null;
        }
        MindingNotification mindingNotification = new MindingNotification();
        mindingNotification.setId(id);
        return mindingNotification;
    }
}
