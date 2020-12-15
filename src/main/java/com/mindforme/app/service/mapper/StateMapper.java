package com.mindforme.app.service.mapper;

import com.mindforme.app.domain.*;
import com.mindforme.app.service.dto.StateDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link State} and its DTO {@link StateDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface StateMapper extends EntityMapper<StateDTO, State> {
    @Mapping(target = "cities", ignore = true)
    @Mapping(target = "removeCity", ignore = true)
    @Mapping(target = "stateData", ignore = true)
    @Mapping(target = "removeStateData", ignore = true)
    State toEntity(StateDTO stateDTO);

    default State fromId(Long id) {
        if (id == null) {
            return null;
        }
        State state = new State();
        state.setId(id);
        return state;
    }
}
