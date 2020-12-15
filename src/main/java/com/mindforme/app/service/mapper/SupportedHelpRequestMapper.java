package com.mindforme.app.service.mapper;

import com.mindforme.app.domain.*;
import com.mindforme.app.service.dto.SupportedHelpRequestDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link SupportedHelpRequest} and its DTO {@link SupportedHelpRequestDTO}.
 */
@Mapper(componentModel = "spring", uses = { SupportedMapper.class, HelpRequestMapper.class })
public interface SupportedHelpRequestMapper extends EntityMapper<SupportedHelpRequestDTO, SupportedHelpRequest> {
    @Mapping(source = "request.id", target = "requestId")
    SupportedHelpRequestDTO toDto(SupportedHelpRequest supportedHelpRequest);

    @Mapping(target = "removeSupported", ignore = true)
    @Mapping(source = "requestId", target = "request")
    SupportedHelpRequest toEntity(SupportedHelpRequestDTO supportedHelpRequestDTO);

    default SupportedHelpRequest fromId(Long id) {
        if (id == null) {
            return null;
        }
        SupportedHelpRequest supportedHelpRequest = new SupportedHelpRequest();
        supportedHelpRequest.setId(id);
        return supportedHelpRequest;
    }
}
