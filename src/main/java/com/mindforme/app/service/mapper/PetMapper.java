package com.mindforme.app.service.mapper;

import com.mindforme.app.domain.*;
import com.mindforme.app.service.dto.PetDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Pet} and its DTO {@link PetDTO}.
 */
@Mapper(
    componentModel = "spring",
    uses = {
        PetBreedMapper.class,
        PetTypeMapper.class,
        FeedingMapper.class,
        RuleMapper.class,
        AllergyMapper.class,
        WalkingOtherMapper.class,
        FamilyMapper.class,
    }
)
public interface PetMapper extends EntityMapper<PetDTO, Pet> {
    @Mapping(source = "breed.id", target = "breedId")
    @Mapping(source = "breed.name", target = "breedName")
    @Mapping(source = "petType.id", target = "petTypeId")
    @Mapping(source = "petType.name", target = "petTypeName")
    @Mapping(source = "family.id", target = "familyId")
    @Mapping(source = "family.name", target = "familyName")
    PetDTO toDto(Pet pet);

    @Mapping(source = "breedId", target = "breed")
    @Mapping(source = "petTypeId", target = "petType")
    @Mapping(target = "removeFeeding", ignore = true)
    @Mapping(target = "removeRule", ignore = true)
    @Mapping(target = "removeAllergy", ignore = true)
    @Mapping(target = "removeWalking", ignore = true)
    @Mapping(source = "familyId", target = "family")
    @Mapping(target = "helpRequests", ignore = true)
    @Mapping(target = "removeHelpRequest", ignore = true)
    Pet toEntity(PetDTO petDTO);

    default Pet fromId(Long id) {
        if (id == null) {
            return null;
        }
        Pet pet = new Pet();
        pet.setId(id);
        return pet;
    }
}
