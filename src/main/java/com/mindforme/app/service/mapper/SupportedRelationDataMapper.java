package com.mindforme.app.service.mapper;

import com.mindforme.app.domain.*;
import com.mindforme.app.service.dto.SupportedRelationDataDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link SupportedRelationData} and its DTO {@link SupportedRelationDataDTO}.
 */
@Mapper(componentModel = "spring", uses = { LanguageMapper.class, SupportedRelationMapper.class })
public interface SupportedRelationDataMapper extends EntityMapper<SupportedRelationDataDTO, SupportedRelationData> {
    @Mapping(source = "lang.id", target = "langId")
    @Mapping(source = "lang.name", target = "langName")
    @Mapping(source = "relation.id", target = "relationId")
    @Mapping(source = "relation.name", target = "relationName")
    SupportedRelationDataDTO toDto(SupportedRelationData supportedRelationData);

    @Mapping(source = "langId", target = "lang")
    @Mapping(source = "relationId", target = "relation")
    SupportedRelationData toEntity(SupportedRelationDataDTO supportedRelationDataDTO);

    default SupportedRelationData fromId(Long id) {
        if (id == null) {
            return null;
        }
        SupportedRelationData supportedRelationData = new SupportedRelationData();
        supportedRelationData.setId(id);
        return supportedRelationData;
    }
}
