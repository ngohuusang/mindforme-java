package com.mindforme.app.service.mapper;

import com.mindforme.app.domain.*;
import com.mindforme.app.service.dto.FamilyGalleryDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link FamilyGallery} and its DTO {@link FamilyGalleryDTO}.
 */
@Mapper(componentModel = "spring", uses = { FamilyMapper.class })
public interface FamilyGalleryMapper extends EntityMapper<FamilyGalleryDTO, FamilyGallery> {
    @Mapping(source = "family.id", target = "familyId")
    @Mapping(source = "family.name", target = "familyName")
    FamilyGalleryDTO toDto(FamilyGallery familyGallery);

    @Mapping(source = "familyId", target = "family")
    FamilyGallery toEntity(FamilyGalleryDTO familyGalleryDTO);

    default FamilyGallery fromId(Long id) {
        if (id == null) {
            return null;
        }
        FamilyGallery familyGallery = new FamilyGallery();
        familyGallery.setId(id);
        return familyGallery;
    }
}
