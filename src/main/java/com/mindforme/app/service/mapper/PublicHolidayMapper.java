package com.mindforme.app.service.mapper;

import com.mindforme.app.domain.*;
import com.mindforme.app.service.dto.PublicHolidayDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link PublicHoliday} and its DTO {@link PublicHolidayDTO}.
 */
@Mapper(componentModel = "spring", uses = { CountryMapper.class })
public interface PublicHolidayMapper extends EntityMapper<PublicHolidayDTO, PublicHoliday> {
    @Mapping(source = "country.id", target = "countryId")
    @Mapping(source = "country.name", target = "countryName")
    PublicHolidayDTO toDto(PublicHoliday publicHoliday);

    @Mapping(source = "countryId", target = "country")
    PublicHoliday toEntity(PublicHolidayDTO publicHolidayDTO);

    default PublicHoliday fromId(Long id) {
        if (id == null) {
            return null;
        }
        PublicHoliday publicHoliday = new PublicHoliday();
        publicHoliday.setId(id);
        return publicHoliday;
    }
}
