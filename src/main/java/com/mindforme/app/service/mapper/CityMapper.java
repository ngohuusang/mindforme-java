package com.mindforme.app.service.mapper;

import com.mindforme.app.domain.*;
import com.mindforme.app.service.dto.CityDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link City} and its DTO {@link CityDTO}.
 */
@Mapper(componentModel = "spring", uses = { StateMapper.class })
public interface CityMapper extends EntityMapper<CityDTO, City> {
    @Mapping(source = "state.id", target = "stateId")
    @Mapping(source = "state.name", target = "stateName")
    CityDTO toDto(City city);

    @Mapping(target = "cityData", ignore = true)
    @Mapping(target = "removeCityData", ignore = true)
    @Mapping(source = "stateId", target = "state")
    City toEntity(CityDTO cityDTO);

    default City fromId(Long id) {
        if (id == null) {
            return null;
        }
        City city = new City();
        city.setId(id);
        return city;
    }
}
