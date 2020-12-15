package com.mindforme.app.service.mapper;

import com.mindforme.app.domain.*;
import com.mindforme.app.service.dto.ChildHelpRequestDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link ChildHelpRequest} and its DTO {@link ChildHelpRequestDTO}.
 */
@Mapper(componentModel = "spring", uses = { ChildMapper.class })
public interface ChildHelpRequestMapper extends EntityMapper<ChildHelpRequestDTO, ChildHelpRequest> {
    @Mapping(target = "removeChild", ignore = true)
    default ChildHelpRequest fromId(Long id) {
        if (id == null) {
            return null;
        }
        ChildHelpRequest childHelpRequest = new ChildHelpRequest();
        childHelpRequest.setId(id);
        return childHelpRequest;
    }
}
