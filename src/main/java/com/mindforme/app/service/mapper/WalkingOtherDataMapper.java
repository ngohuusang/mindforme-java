package com.mindforme.app.service.mapper;

import com.mindforme.app.domain.*;
import com.mindforme.app.service.dto.WalkingOtherDataDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link WalkingOtherData} and its DTO {@link WalkingOtherDataDTO}.
 */
@Mapper(componentModel = "spring", uses = { LanguageMapper.class, WalkingOtherMapper.class })
public interface WalkingOtherDataMapper extends EntityMapper<WalkingOtherDataDTO, WalkingOtherData> {
    @Mapping(source = "lang.id", target = "langId")
    @Mapping(source = "lang.name", target = "langName")
    @Mapping(source = "walkingOther.id", target = "walkingOtherId")
    @Mapping(source = "walkingOther.name", target = "walkingOtherName")
    WalkingOtherDataDTO toDto(WalkingOtherData walkingOtherData);

    @Mapping(source = "langId", target = "lang")
    @Mapping(source = "walkingOtherId", target = "walkingOther")
    WalkingOtherData toEntity(WalkingOtherDataDTO walkingOtherDataDTO);

    default WalkingOtherData fromId(Long id) {
        if (id == null) {
            return null;
        }
        WalkingOtherData walkingOtherData = new WalkingOtherData();
        walkingOtherData.setId(id);
        return walkingOtherData;
    }
}
