package com.mindforme.app.service.mapper;

import com.mindforme.app.domain.*;
import com.mindforme.app.service.dto.WalkingOtherDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link WalkingOther} and its DTO {@link WalkingOtherDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface WalkingOtherMapper extends EntityMapper<WalkingOtherDTO, WalkingOther> {
    @Mapping(target = "walkingOtherData", ignore = true)
    @Mapping(target = "removeWalkingOtherData", ignore = true)
    @Mapping(target = "pets", ignore = true)
    @Mapping(target = "removePet", ignore = true)
    WalkingOther toEntity(WalkingOtherDTO walkingOtherDTO);

    default WalkingOther fromId(Long id) {
        if (id == null) {
            return null;
        }
        WalkingOther walkingOther = new WalkingOther();
        walkingOther.setId(id);
        return walkingOther;
    }
}
