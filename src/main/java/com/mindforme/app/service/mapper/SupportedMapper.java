package com.mindforme.app.service.mapper;

import com.mindforme.app.domain.*;
import com.mindforme.app.service.dto.SupportedDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Supported} and its DTO {@link SupportedDTO}.
 */
@Mapper(componentModel = "spring", uses = { SupportedRelationMapper.class, FamilyMapper.class })
public interface SupportedMapper extends EntityMapper<SupportedDTO, Supported> {
    @Mapping(source = "relation.id", target = "relationId")
    @Mapping(source = "relation.name", target = "relationName")
    @Mapping(source = "family.id", target = "familyId")
    @Mapping(source = "family.name", target = "familyName")
    SupportedDTO toDto(Supported supported);

    @Mapping(source = "relationId", target = "relation")
    @Mapping(source = "familyId", target = "family")
    @Mapping(target = "helpRequests", ignore = true)
    @Mapping(target = "removeHelpRequest", ignore = true)
    Supported toEntity(SupportedDTO supportedDTO);

    default Supported fromId(Long id) {
        if (id == null) {
            return null;
        }
        Supported supported = new Supported();
        supported.setId(id);
        return supported;
    }
}
