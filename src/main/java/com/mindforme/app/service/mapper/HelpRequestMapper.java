package com.mindforme.app.service.mapper;

import com.mindforme.app.domain.*;
import com.mindforme.app.service.dto.HelpRequestDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link HelpRequest} and its DTO {@link HelpRequestDTO}.
 */
@Mapper(componentModel = "spring", uses = { AddressMapper.class })
public interface HelpRequestMapper extends EntityMapper<HelpRequestDTO, HelpRequest> {
    @Mapping(source = "helpLocation.id", target = "helpLocationId")
    HelpRequestDTO toDto(HelpRequest helpRequest);

    @Mapping(target = "gardenRequests", ignore = true)
    @Mapping(target = "removeGardenRequest", ignore = true)
    @Mapping(target = "houseRequests", ignore = true)
    @Mapping(target = "removeHouseRequest", ignore = true)
    @Mapping(target = "petRequests", ignore = true)
    @Mapping(target = "removePetRequest", ignore = true)
    @Mapping(target = "supportedRequests", ignore = true)
    @Mapping(target = "removeSupportedRequest", ignore = true)
    @Mapping(target = "childRequests", ignore = true)
    @Mapping(target = "removeChildRequest", ignore = true)
    @Mapping(source = "helpLocationId", target = "helpLocation")
    HelpRequest toEntity(HelpRequestDTO helpRequestDTO);

    default HelpRequest fromId(Long id) {
        if (id == null) {
            return null;
        }
        HelpRequest helpRequest = new HelpRequest();
        helpRequest.setId(id);
        return helpRequest;
    }
}
