package com.mindforme.app.service.mapper;

import com.mindforme.app.domain.*;
import com.mindforme.app.service.dto.ChildDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Child} and its DTO {@link ChildDTO}.
 */
@Mapper(
    componentModel = "spring",
    uses = {
        ChildRelationMapper.class,
        InterestMapper.class,
        AllergyMapper.class,
        FavouriteFoodMapper.class,
        MedicalConditionMapper.class,
        FamilyMapper.class,
        HelpRequestMapper.class,
    }
)
public interface ChildMapper extends EntityMapper<ChildDTO, Child> {
    @Mapping(source = "relation.id", target = "relationId")
    @Mapping(source = "relation.name", target = "relationName")
    @Mapping(source = "family.id", target = "familyId")
    @Mapping(source = "family.name", target = "familyName")
    @Mapping(source = "request.id", target = "requestId")
    ChildDTO toDto(Child child);

    @Mapping(source = "relationId", target = "relation")
    @Mapping(target = "removeInterest", ignore = true)
    @Mapping(target = "removeAllergy", ignore = true)
    @Mapping(target = "removeFavouriteFood", ignore = true)
    @Mapping(target = "removeMedicalCondition", ignore = true)
    @Mapping(source = "familyId", target = "family")
    @Mapping(source = "requestId", target = "request")
    @Mapping(target = "helpRequests", ignore = true)
    @Mapping(target = "removeHelpRequest", ignore = true)
    Child toEntity(ChildDTO childDTO);

    default Child fromId(Long id) {
        if (id == null) {
            return null;
        }
        Child child = new Child();
        child.setId(id);
        return child;
    }
}
