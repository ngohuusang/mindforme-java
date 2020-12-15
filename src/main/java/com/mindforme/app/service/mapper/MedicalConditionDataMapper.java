package com.mindforme.app.service.mapper;

import com.mindforme.app.domain.*;
import com.mindforme.app.service.dto.MedicalConditionDataDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link MedicalConditionData} and its DTO {@link MedicalConditionDataDTO}.
 */
@Mapper(componentModel = "spring", uses = { LanguageMapper.class, MedicalConditionMapper.class })
public interface MedicalConditionDataMapper extends EntityMapper<MedicalConditionDataDTO, MedicalConditionData> {
    @Mapping(source = "lang.id", target = "langId")
    @Mapping(source = "lang.name", target = "langName")
    @Mapping(source = "medicalCondition.id", target = "medicalConditionId")
    @Mapping(source = "medicalCondition.name", target = "medicalConditionName")
    MedicalConditionDataDTO toDto(MedicalConditionData medicalConditionData);

    @Mapping(source = "langId", target = "lang")
    @Mapping(source = "medicalConditionId", target = "medicalCondition")
    MedicalConditionData toEntity(MedicalConditionDataDTO medicalConditionDataDTO);

    default MedicalConditionData fromId(Long id) {
        if (id == null) {
            return null;
        }
        MedicalConditionData medicalConditionData = new MedicalConditionData();
        medicalConditionData.setId(id);
        return medicalConditionData;
    }
}
