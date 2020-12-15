package com.mindforme.app.service.mapper;

import com.mindforme.app.domain.*;
import com.mindforme.app.service.dto.InterestDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Interest} and its DTO {@link InterestDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface InterestMapper extends EntityMapper<InterestDTO, Interest> {
    @Mapping(target = "interestData", ignore = true)
    @Mapping(target = "removeInterestData", ignore = true)
    @Mapping(target = "children", ignore = true)
    @Mapping(target = "removeChild", ignore = true)
    Interest toEntity(InterestDTO interestDTO);

    default Interest fromId(Long id) {
        if (id == null) {
            return null;
        }
        Interest interest = new Interest();
        interest.setId(id);
        return interest;
    }
}
