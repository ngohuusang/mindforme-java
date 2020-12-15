package com.mindforme.app.service.mapper;

import com.mindforme.app.domain.*;
import com.mindforme.app.service.dto.PetBreedDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link PetBreed} and its DTO {@link PetBreedDTO}.
 */
@Mapper(componentModel = "spring", uses = { PetTypeMapper.class })
public interface PetBreedMapper extends EntityMapper<PetBreedDTO, PetBreed> {
    @Mapping(source = "petType.id", target = "petTypeId")
    @Mapping(source = "petType.name", target = "petTypeName")
    PetBreedDTO toDto(PetBreed petBreed);

    @Mapping(target = "petBreedData", ignore = true)
    @Mapping(target = "removePetBreedData", ignore = true)
    @Mapping(source = "petTypeId", target = "petType")
    PetBreed toEntity(PetBreedDTO petBreedDTO);

    default PetBreed fromId(Long id) {
        if (id == null) {
            return null;
        }
        PetBreed petBreed = new PetBreed();
        petBreed.setId(id);
        return petBreed;
    }
}
