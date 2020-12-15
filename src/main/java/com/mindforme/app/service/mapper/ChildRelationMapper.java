package com.mindforme.app.service.mapper;

import com.mindforme.app.domain.*;
import com.mindforme.app.service.dto.ChildRelationDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link ChildRelation} and its DTO {@link ChildRelationDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface ChildRelationMapper extends EntityMapper<ChildRelationDTO, ChildRelation> {
    @Mapping(target = "relationData", ignore = true)
    @Mapping(target = "removeRelationData", ignore = true)
    ChildRelation toEntity(ChildRelationDTO childRelationDTO);

    default ChildRelation fromId(Long id) {
        if (id == null) {
            return null;
        }
        ChildRelation childRelation = new ChildRelation();
        childRelation.setId(id);
        return childRelation;
    }
}
