package com.mindforme.app.service.mapper;

import com.mindforme.app.domain.*;
import com.mindforme.app.service.dto.CityDataDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link CityData} and its DTO {@link CityDataDTO}.
 */
@Mapper(componentModel = "spring", uses = { LanguageMapper.class, CityMapper.class })
public interface CityDataMapper extends EntityMapper<CityDataDTO, CityData> {
    @Mapping(source = "lang.id", target = "langId")
    @Mapping(source = "lang.name", target = "langName")
    @Mapping(source = "city.id", target = "cityId")
    @Mapping(source = "city.name", target = "cityName")
    CityDataDTO toDto(CityData cityData);

    @Mapping(source = "langId", target = "lang")
    @Mapping(source = "cityId", target = "city")
    CityData toEntity(CityDataDTO cityDataDTO);

    default CityData fromId(Long id) {
        if (id == null) {
            return null;
        }
        CityData cityData = new CityData();
        cityData.setId(id);
        return cityData;
    }
}
