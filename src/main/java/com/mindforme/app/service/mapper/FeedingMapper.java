package com.mindforme.app.service.mapper;

import com.mindforme.app.domain.*;
import com.mindforme.app.service.dto.FeedingDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Feeding} and its DTO {@link FeedingDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface FeedingMapper extends EntityMapper<FeedingDTO, Feeding> {
    @Mapping(target = "feedingData", ignore = true)
    @Mapping(target = "removeFeedingData", ignore = true)
    @Mapping(target = "pets", ignore = true)
    @Mapping(target = "removePet", ignore = true)
    Feeding toEntity(FeedingDTO feedingDTO);

    default Feeding fromId(Long id) {
        if (id == null) {
            return null;
        }
        Feeding feeding = new Feeding();
        feeding.setId(id);
        return feeding;
    }
}
