package com.mindforme.app.service.mapper;

import com.mindforme.app.domain.*;
import com.mindforme.app.service.dto.CountryDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Country} and its DTO {@link CountryDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface CountryMapper extends EntityMapper<CountryDTO, Country> {
    @Mapping(target = "publicHolidays", ignore = true)
    @Mapping(target = "removePublicHoliday", ignore = true)
    Country toEntity(CountryDTO countryDTO);

    default Country fromId(Long id) {
        if (id == null) {
            return null;
        }
        Country country = new Country();
        country.setId(id);
        return country;
    }
}
