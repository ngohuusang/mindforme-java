package com.mindforme.app.service.mapper;

import com.mindforme.app.domain.*;
import com.mindforme.app.service.dto.PlanDataDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link PlanData} and its DTO {@link PlanDataDTO}.
 */
@Mapper(componentModel = "spring", uses = { LanguageMapper.class, PlanMapper.class })
public interface PlanDataMapper extends EntityMapper<PlanDataDTO, PlanData> {
    @Mapping(source = "lang.id", target = "langId")
    @Mapping(source = "lang.name", target = "langName")
    @Mapping(source = "plan.id", target = "planId")
    @Mapping(source = "plan.name", target = "planName")
    PlanDataDTO toDto(PlanData planData);

    @Mapping(source = "langId", target = "lang")
    @Mapping(source = "planId", target = "plan")
    PlanData toEntity(PlanDataDTO planDataDTO);

    default PlanData fromId(Long id) {
        if (id == null) {
            return null;
        }
        PlanData planData = new PlanData();
        planData.setId(id);
        return planData;
    }
}
