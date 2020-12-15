package com.mindforme.app.service.mapper;

import com.mindforme.app.domain.*;
import com.mindforme.app.service.dto.AllergyDataDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link AllergyData} and its DTO {@link AllergyDataDTO}.
 */
@Mapper(componentModel = "spring", uses = { LanguageMapper.class, AllergyMapper.class })
public interface AllergyDataMapper extends EntityMapper<AllergyDataDTO, AllergyData> {
    @Mapping(source = "lang.id", target = "langId")
    @Mapping(source = "lang.name", target = "langName")
    @Mapping(source = "allergy.id", target = "allergyId")
    @Mapping(source = "allergy.name", target = "allergyName")
    AllergyDataDTO toDto(AllergyData allergyData);

    @Mapping(source = "langId", target = "lang")
    @Mapping(source = "allergyId", target = "allergy")
    AllergyData toEntity(AllergyDataDTO allergyDataDTO);

    default AllergyData fromId(Long id) {
        if (id == null) {
            return null;
        }
        AllergyData allergyData = new AllergyData();
        allergyData.setId(id);
        return allergyData;
    }
}
