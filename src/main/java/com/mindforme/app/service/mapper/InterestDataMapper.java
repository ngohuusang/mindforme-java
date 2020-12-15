package com.mindforme.app.service.mapper;

import com.mindforme.app.domain.*;
import com.mindforme.app.service.dto.InterestDataDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link InterestData} and its DTO {@link InterestDataDTO}.
 */
@Mapper(componentModel = "spring", uses = { LanguageMapper.class, InterestMapper.class })
public interface InterestDataMapper extends EntityMapper<InterestDataDTO, InterestData> {
    @Mapping(source = "lang.id", target = "langId")
    @Mapping(source = "lang.name", target = "langName")
    @Mapping(source = "interest.id", target = "interestId")
    @Mapping(source = "interest.name", target = "interestName")
    InterestDataDTO toDto(InterestData interestData);

    @Mapping(source = "langId", target = "lang")
    @Mapping(source = "interestId", target = "interest")
    InterestData toEntity(InterestDataDTO interestDataDTO);

    default InterestData fromId(Long id) {
        if (id == null) {
            return null;
        }
        InterestData interestData = new InterestData();
        interestData.setId(id);
        return interestData;
    }
}
