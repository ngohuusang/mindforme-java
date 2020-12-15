package com.mindforme.app.service.mapper;

import com.mindforme.app.domain.*;
import com.mindforme.app.service.dto.FavouriteFoodDataDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link FavouriteFoodData} and its DTO {@link FavouriteFoodDataDTO}.
 */
@Mapper(componentModel = "spring", uses = { LanguageMapper.class, FavouriteFoodMapper.class })
public interface FavouriteFoodDataMapper extends EntityMapper<FavouriteFoodDataDTO, FavouriteFoodData> {
    @Mapping(source = "lang.id", target = "langId")
    @Mapping(source = "lang.name", target = "langName")
    @Mapping(source = "favouriteFood.id", target = "favouriteFoodId")
    @Mapping(source = "favouriteFood.name", target = "favouriteFoodName")
    FavouriteFoodDataDTO toDto(FavouriteFoodData favouriteFoodData);

    @Mapping(source = "langId", target = "lang")
    @Mapping(source = "favouriteFoodId", target = "favouriteFood")
    FavouriteFoodData toEntity(FavouriteFoodDataDTO favouriteFoodDataDTO);

    default FavouriteFoodData fromId(Long id) {
        if (id == null) {
            return null;
        }
        FavouriteFoodData favouriteFoodData = new FavouriteFoodData();
        favouriteFoodData.setId(id);
        return favouriteFoodData;
    }
}
