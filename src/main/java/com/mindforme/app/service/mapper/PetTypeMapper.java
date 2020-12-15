package com.mindforme.app.service.mapper;

import com.mindforme.app.domain.*;
import com.mindforme.app.service.dto.PetTypeDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link PetType} and its DTO {@link PetTypeDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface PetTypeMapper extends EntityMapper<PetTypeDTO, PetType> {
    @Mapping(target = "petBreeds", ignore = true)
    @Mapping(target = "removePetBreed", ignore = true)
    @Mapping(target = "petTypeData", ignore = true)
    @Mapping(target = "removePetTypeData", ignore = true)
    PetType toEntity(PetTypeDTO petTypeDTO);

    default PetType fromId(Long id) {
        if (id == null) {
            return null;
        }
        PetType petType = new PetType();
        petType.setId(id);
        return petType;
    }
}
