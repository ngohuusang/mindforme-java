package com.mindforme.app.service.mapper;

import com.mindforme.app.domain.*;
import com.mindforme.app.service.dto.MedicalConditionDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link MedicalCondition} and its DTO {@link MedicalConditionDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface MedicalConditionMapper extends EntityMapper<MedicalConditionDTO, MedicalCondition> {
    @Mapping(target = "medicalConditionData", ignore = true)
    @Mapping(target = "removeMedicalConditionData", ignore = true)
    @Mapping(target = "children", ignore = true)
    @Mapping(target = "removeChild", ignore = true)
    MedicalCondition toEntity(MedicalConditionDTO medicalConditionDTO);

    default MedicalCondition fromId(Long id) {
        if (id == null) {
            return null;
        }
        MedicalCondition medicalCondition = new MedicalCondition();
        medicalCondition.setId(id);
        return medicalCondition;
    }
}
