package com.mindforme.app.service.mapper;

import com.mindforme.app.domain.*;
import com.mindforme.app.service.dto.AllergyDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Allergy} and its DTO {@link AllergyDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface AllergyMapper extends EntityMapper<AllergyDTO, Allergy> {
    @Mapping(target = "allergyData", ignore = true)
    @Mapping(target = "removeAllergyData", ignore = true)
    @Mapping(target = "pets", ignore = true)
    @Mapping(target = "removePet", ignore = true)
    @Mapping(target = "children", ignore = true)
    @Mapping(target = "removeChild", ignore = true)
    Allergy toEntity(AllergyDTO allergyDTO);

    default Allergy fromId(Long id) {
        if (id == null) {
            return null;
        }
        Allergy allergy = new Allergy();
        allergy.setId(id);
        return allergy;
    }
}
