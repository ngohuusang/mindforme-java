package com.mindforme.app.service.mapper;

import com.mindforme.app.domain.*;
import com.mindforme.app.service.dto.FavouriteFoodDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link FavouriteFood} and its DTO {@link FavouriteFoodDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface FavouriteFoodMapper extends EntityMapper<FavouriteFoodDTO, FavouriteFood> {
    @Mapping(target = "favouriteFoodData", ignore = true)
    @Mapping(target = "removeFavouriteFoodData", ignore = true)
    @Mapping(target = "children", ignore = true)
    @Mapping(target = "removeChild", ignore = true)
    FavouriteFood toEntity(FavouriteFoodDTO favouriteFoodDTO);

    default FavouriteFood fromId(Long id) {
        if (id == null) {
            return null;
        }
        FavouriteFood favouriteFood = new FavouriteFood();
        favouriteFood.setId(id);
        return favouriteFood;
    }
}
