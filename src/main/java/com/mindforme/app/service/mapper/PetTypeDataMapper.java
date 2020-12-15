package com.mindforme.app.service.mapper;

import com.mindforme.app.domain.*;
import com.mindforme.app.service.dto.PetTypeDataDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link PetTypeData} and its DTO {@link PetTypeDataDTO}.
 */
@Mapper(componentModel = "spring", uses = { LanguageMapper.class, PetTypeMapper.class })
public interface PetTypeDataMapper extends EntityMapper<PetTypeDataDTO, PetTypeData> {
    @Mapping(source = "lang.id", target = "langId")
    @Mapping(source = "lang.name", target = "langName")
    @Mapping(source = "petType.id", target = "petTypeId")
    @Mapping(source = "petType.name", target = "petTypeName")
    PetTypeDataDTO toDto(PetTypeData petTypeData);

    @Mapping(source = "langId", target = "lang")
    @Mapping(source = "petTypeId", target = "petType")
    PetTypeData toEntity(PetTypeDataDTO petTypeDataDTO);

    default PetTypeData fromId(Long id) {
        if (id == null) {
            return null;
        }
        PetTypeData petTypeData = new PetTypeData();
        petTypeData.setId(id);
        return petTypeData;
    }
}
