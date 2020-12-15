package com.mindforme.app.service.mapper;

import com.mindforme.app.domain.*;
import com.mindforme.app.service.dto.PetBreedDataDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link PetBreedData} and its DTO {@link PetBreedDataDTO}.
 */
@Mapper(componentModel = "spring", uses = { LanguageMapper.class, PetBreedMapper.class })
public interface PetBreedDataMapper extends EntityMapper<PetBreedDataDTO, PetBreedData> {
    @Mapping(source = "lang.id", target = "langId")
    @Mapping(source = "lang.name", target = "langName")
    @Mapping(source = "petBreed.id", target = "petBreedId")
    @Mapping(source = "petBreed.name", target = "petBreedName")
    PetBreedDataDTO toDto(PetBreedData petBreedData);

    @Mapping(source = "langId", target = "lang")
    @Mapping(source = "petBreedId", target = "petBreed")
    PetBreedData toEntity(PetBreedDataDTO petBreedDataDTO);

    default PetBreedData fromId(Long id) {
        if (id == null) {
            return null;
        }
        PetBreedData petBreedData = new PetBreedData();
        petBreedData.setId(id);
        return petBreedData;
    }
}
