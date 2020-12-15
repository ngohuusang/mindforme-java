package com.mindforme.app.service.mapper;

import com.mindforme.app.domain.*;
import com.mindforme.app.service.dto.FeedingDataDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link FeedingData} and its DTO {@link FeedingDataDTO}.
 */
@Mapper(componentModel = "spring", uses = { LanguageMapper.class, FeedingMapper.class })
public interface FeedingDataMapper extends EntityMapper<FeedingDataDTO, FeedingData> {
    @Mapping(source = "lang.id", target = "langId")
    @Mapping(source = "lang.name", target = "langName")
    @Mapping(source = "feeding.id", target = "feedingId")
    @Mapping(source = "feeding.name", target = "feedingName")
    FeedingDataDTO toDto(FeedingData feedingData);

    @Mapping(source = "langId", target = "lang")
    @Mapping(source = "feedingId", target = "feeding")
    FeedingData toEntity(FeedingDataDTO feedingDataDTO);

    default FeedingData fromId(Long id) {
        if (id == null) {
            return null;
        }
        FeedingData feedingData = new FeedingData();
        feedingData.setId(id);
        return feedingData;
    }
}
