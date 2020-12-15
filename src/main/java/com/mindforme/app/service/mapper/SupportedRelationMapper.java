package com.mindforme.app.service.mapper;

import com.mindforme.app.domain.*;
import com.mindforme.app.service.dto.SupportedRelationDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link SupportedRelation} and its DTO {@link SupportedRelationDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface SupportedRelationMapper extends EntityMapper<SupportedRelationDTO, SupportedRelation> {
    @Mapping(target = "relationData", ignore = true)
    @Mapping(target = "removeRelationData", ignore = true)
    SupportedRelation toEntity(SupportedRelationDTO supportedRelationDTO);

    default SupportedRelation fromId(Long id) {
        if (id == null) {
            return null;
        }
        SupportedRelation supportedRelation = new SupportedRelation();
        supportedRelation.setId(id);
        return supportedRelation;
    }
}
