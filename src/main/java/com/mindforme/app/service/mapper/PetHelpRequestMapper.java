package com.mindforme.app.service.mapper;

import com.mindforme.app.domain.*;
import com.mindforme.app.service.dto.PetHelpRequestDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link PetHelpRequest} and its DTO {@link PetHelpRequestDTO}.
 */
@Mapper(componentModel = "spring", uses = { PetMapper.class, HelpRequestMapper.class })
public interface PetHelpRequestMapper extends EntityMapper<PetHelpRequestDTO, PetHelpRequest> {
    @Mapping(source = "request.id", target = "requestId")
    PetHelpRequestDTO toDto(PetHelpRequest petHelpRequest);

    @Mapping(target = "removePet", ignore = true)
    @Mapping(source = "requestId", target = "request")
    PetHelpRequest toEntity(PetHelpRequestDTO petHelpRequestDTO);

    default PetHelpRequest fromId(Long id) {
        if (id == null) {
            return null;
        }
        PetHelpRequest petHelpRequest = new PetHelpRequest();
        petHelpRequest.setId(id);
        return petHelpRequest;
    }
}
