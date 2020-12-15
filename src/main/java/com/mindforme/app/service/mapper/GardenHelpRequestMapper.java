package com.mindforme.app.service.mapper;

import com.mindforme.app.domain.*;
import com.mindforme.app.service.dto.GardenHelpRequestDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link GardenHelpRequest} and its DTO {@link GardenHelpRequestDTO}.
 */
@Mapper(componentModel = "spring", uses = { HelpRequestMapper.class })
public interface GardenHelpRequestMapper extends EntityMapper<GardenHelpRequestDTO, GardenHelpRequest> {
    @Mapping(source = "request.id", target = "requestId")
    GardenHelpRequestDTO toDto(GardenHelpRequest gardenHelpRequest);

    @Mapping(source = "requestId", target = "request")
    GardenHelpRequest toEntity(GardenHelpRequestDTO gardenHelpRequestDTO);

    default GardenHelpRequest fromId(Long id) {
        if (id == null) {
            return null;
        }
        GardenHelpRequest gardenHelpRequest = new GardenHelpRequest();
        gardenHelpRequest.setId(id);
        return gardenHelpRequest;
    }
}
