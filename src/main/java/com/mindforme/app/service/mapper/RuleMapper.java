package com.mindforme.app.service.mapper;

import com.mindforme.app.domain.*;
import com.mindforme.app.service.dto.RuleDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Rule} and its DTO {@link RuleDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface RuleMapper extends EntityMapper<RuleDTO, Rule> {
    @Mapping(target = "ruleData", ignore = true)
    @Mapping(target = "removeRuleData", ignore = true)
    @Mapping(target = "pets", ignore = true)
    @Mapping(target = "removePet", ignore = true)
    Rule toEntity(RuleDTO ruleDTO);

    default Rule fromId(Long id) {
        if (id == null) {
            return null;
        }
        Rule rule = new Rule();
        rule.setId(id);
        return rule;
    }
}
