package com.mindforme.app.service.mapper;

import com.mindforme.app.domain.*;
import com.mindforme.app.service.dto.EmergencyContactDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link EmergencyContact} and its DTO {@link EmergencyContactDTO}.
 */
@Mapper(componentModel = "spring", uses = { AddressMapper.class, FamilyMapper.class })
public interface EmergencyContactMapper extends EntityMapper<EmergencyContactDTO, EmergencyContact> {
    @Mapping(source = "address.id", target = "addressId")
    @Mapping(source = "address.address", target = "addressAddress")
    @Mapping(source = "family.id", target = "familyId")
    @Mapping(source = "family.name", target = "familyName")
    EmergencyContactDTO toDto(EmergencyContact emergencyContact);

    @Mapping(source = "addressId", target = "address")
    @Mapping(source = "familyId", target = "family")
    EmergencyContact toEntity(EmergencyContactDTO emergencyContactDTO);

    default EmergencyContact fromId(Long id) {
        if (id == null) {
            return null;
        }
        EmergencyContact emergencyContact = new EmergencyContact();
        emergencyContact.setId(id);
        return emergencyContact;
    }
}
