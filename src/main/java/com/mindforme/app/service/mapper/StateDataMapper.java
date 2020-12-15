package com.mindforme.app.service.mapper;

import com.mindforme.app.domain.*;
import com.mindforme.app.service.dto.StateDataDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link StateData} and its DTO {@link StateDataDTO}.
 */
@Mapper(componentModel = "spring", uses = { LanguageMapper.class, StateMapper.class })
public interface StateDataMapper extends EntityMapper<StateDataDTO, StateData> {
    @Mapping(source = "lang.id", target = "langId")
    @Mapping(source = "lang.name", target = "langName")
    @Mapping(source = "state.id", target = "stateId")
    @Mapping(source = "state.name", target = "stateName")
    StateDataDTO toDto(StateData stateData);

    @Mapping(source = "langId", target = "lang")
    @Mapping(source = "stateId", target = "state")
    StateData toEntity(StateDataDTO stateDataDTO);

    default StateData fromId(Long id) {
        if (id == null) {
            return null;
        }
        StateData stateData = new StateData();
        stateData.setId(id);
        return stateData;
    }
}
