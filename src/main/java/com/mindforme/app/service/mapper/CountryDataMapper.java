package com.mindforme.app.service.mapper;

import com.mindforme.app.domain.*;
import com.mindforme.app.service.dto.CountryDataDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link CountryData} and its DTO {@link CountryDataDTO}.
 */
@Mapper(componentModel = "spring", uses = { LanguageMapper.class })
public interface CountryDataMapper extends EntityMapper<CountryDataDTO, CountryData> {
    @Mapping(source = "lang.id", target = "langId")
    @Mapping(source = "lang.name", target = "langName")
    CountryDataDTO toDto(CountryData countryData);

    @Mapping(source = "langId", target = "lang")
    CountryData toEntity(CountryDataDTO countryDataDTO);

    default CountryData fromId(Long id) {
        if (id == null) {
            return null;
        }
        CountryData countryData = new CountryData();
        countryData.setId(id);
        return countryData;
    }
}
