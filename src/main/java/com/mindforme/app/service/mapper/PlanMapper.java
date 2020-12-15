package com.mindforme.app.service.mapper;

import com.mindforme.app.domain.*;
import com.mindforme.app.service.dto.PlanDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Plan} and its DTO {@link PlanDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface PlanMapper extends EntityMapper<PlanDTO, Plan> {
    @Mapping(target = "plansData", ignore = true)
    @Mapping(target = "removePlansData", ignore = true)
    Plan toEntity(PlanDTO planDTO);

    default Plan fromId(Long id) {
        if (id == null) {
            return null;
        }
        Plan plan = new Plan();
        plan.setId(id);
        return plan;
    }
}
