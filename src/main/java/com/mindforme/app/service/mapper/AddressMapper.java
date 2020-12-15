package com.mindforme.app.service.mapper;

import com.mindforme.app.domain.*;
import com.mindforme.app.service.dto.AddressDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Address} and its DTO {@link AddressDTO}.
 */
@Mapper(componentModel = "spring", uses = { StateMapper.class, CountryMapper.class, CityMapper.class })
public interface AddressMapper extends EntityMapper<AddressDTO, Address> {
    @Mapping(source = "state.id", target = "stateId")
    @Mapping(source = "state.name", target = "stateName")
    @Mapping(source = "country.id", target = "countryId")
    @Mapping(source = "country.name", target = "countryName")
    @Mapping(source = "city.id", target = "cityId")
    @Mapping(source = "city.name", target = "cityName")
    AddressDTO toDto(Address address);

    @Mapping(source = "stateId", target = "state")
    @Mapping(source = "countryId", target = "country")
    @Mapping(source = "cityId", target = "city")
    Address toEntity(AddressDTO addressDTO);

    default Address fromId(Long id) {
        if (id == null) {
            return null;
        }
        Address address = new Address();
        address.setId(id);
        return address;
    }
}
