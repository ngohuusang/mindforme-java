package com.mindforme.app.service.mapper;

import com.mindforme.app.domain.*;
import com.mindforme.app.service.dto.RuleDataDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link RuleData} and its DTO {@link RuleDataDTO}.
 */
@Mapper(componentModel = "spring", uses = { LanguageMapper.class, RuleMapper.class })
public interface RuleDataMapper extends EntityMapper<RuleDataDTO, RuleData> {
    @Mapping(source = "lang.id", target = "langId")
    @Mapping(source = "lang.name", target = "langName")
    @Mapping(source = "rule.id", target = "ruleId")
    @Mapping(source = "rule.name", target = "ruleName")
    RuleDataDTO toDto(RuleData ruleData);

    @Mapping(source = "langId", target = "lang")
    @Mapping(source = "ruleId", target = "rule")
    RuleData toEntity(RuleDataDTO ruleDataDTO);

    default RuleData fromId(Long id) {
        if (id == null) {
            return null;
        }
        RuleData ruleData = new RuleData();
        ruleData.setId(id);
        return ruleData;
    }
}
