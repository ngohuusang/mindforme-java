package com.mindforme.app.service.mapper;

import com.mindforme.app.domain.*;
import com.mindforme.app.service.dto.HouseHelpRequestDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link HouseHelpRequest} and its DTO {@link HouseHelpRequestDTO}.
 */
@Mapper(componentModel = "spring", uses = { HelpRequestMapper.class })
public interface HouseHelpRequestMapper extends EntityMapper<HouseHelpRequestDTO, HouseHelpRequest> {
    @Mapping(source = "request.id", target = "requestId")
    HouseHelpRequestDTO toDto(HouseHelpRequest houseHelpRequest);

    @Mapping(source = "requestId", target = "request")
    HouseHelpRequest toEntity(HouseHelpRequestDTO houseHelpRequestDTO);

    default HouseHelpRequest fromId(Long id) {
        if (id == null) {
            return null;
        }
        HouseHelpRequest houseHelpRequest = new HouseHelpRequest();
        houseHelpRequest.setId(id);
        return houseHelpRequest;
    }
}
