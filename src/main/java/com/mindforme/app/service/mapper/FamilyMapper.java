package com.mindforme.app.service.mapper;

import com.mindforme.app.domain.*;
import com.mindforme.app.service.dto.FamilyDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Family} and its DTO {@link FamilyDTO}.
 */
@Mapper(componentModel = "spring", uses = { AddressMapper.class, PlanMapper.class })
public interface FamilyMapper extends EntityMapper<FamilyDTO, Family> {
    @Mapping(source = "address.id", target = "addressId")
    @Mapping(source = "address.address", target = "addressAddress")
    @Mapping(source = "plan.id", target = "planId")
    @Mapping(source = "plan.name", target = "planName")
    FamilyDTO toDto(Family family);

    @Mapping(source = "addressId", target = "address")
    @Mapping(target = "members", ignore = true)
    @Mapping(target = "removeMember", ignore = true)
    @Mapping(target = "doctors", ignore = true)
    @Mapping(target = "removeDoctor", ignore = true)
    @Mapping(target = "emergencyContacts", ignore = true)
    @Mapping(target = "removeEmergencyContact", ignore = true)
    @Mapping(target = "galleries", ignore = true)
    @Mapping(target = "removeGallery", ignore = true)
    @Mapping(target = "pets", ignore = true)
    @Mapping(target = "removePet", ignore = true)
    @Mapping(target = "children", ignore = true)
    @Mapping(target = "removeChild", ignore = true)
    @Mapping(target = "supporteds", ignore = true)
    @Mapping(target = "removeSupported", ignore = true)
    @Mapping(target = "mindingNotifications", ignore = true)
    @Mapping(target = "removeMindingNotification", ignore = true)
    @Mapping(source = "planId", target = "plan")
    Family toEntity(FamilyDTO familyDTO);

    default Family fromId(Long id) {
        if (id == null) {
            return null;
        }
        Family family = new Family();
        family.setId(id);
        return family;
    }
}
