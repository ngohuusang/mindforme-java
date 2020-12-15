package com.mindforme.app.service.mapper;

import com.mindforme.app.domain.*;
import com.mindforme.app.service.dto.ChildRelationDataDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link ChildRelationData} and its DTO {@link ChildRelationDataDTO}.
 */
@Mapper(componentModel = "spring", uses = { LanguageMapper.class, ChildRelationMapper.class })
public interface ChildRelationDataMapper extends EntityMapper<ChildRelationDataDTO, ChildRelationData> {
    @Mapping(source = "lang.id", target = "langId")
    @Mapping(source = "lang.name", target = "langName")
    @Mapping(source = "relation.id", target = "relationId")
    @Mapping(source = "relation.name", target = "relationName")
    ChildRelationDataDTO toDto(ChildRelationData childRelationData);

    @Mapping(source = "langId", target = "lang")
    @Mapping(source = "relationId", target = "relation")
    ChildRelationData toEntity(ChildRelationDataDTO childRelationDataDTO);

    default ChildRelationData fromId(Long id) {
        if (id == null) {
            return null;
        }
        ChildRelationData childRelationData = new ChildRelationData();
        childRelationData.setId(id);
        return childRelationData;
    }
}
