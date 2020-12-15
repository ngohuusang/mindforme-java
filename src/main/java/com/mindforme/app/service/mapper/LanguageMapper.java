package com.mindforme.app.service.mapper;

import com.mindforme.app.domain.*;
import com.mindforme.app.service.dto.LanguageDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Language} and its DTO {@link LanguageDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface LanguageMapper extends EntityMapper<LanguageDTO, Language> {
    default Language fromId(Long id) {
        if (id == null) {
            return null;
        }
        Language language = new Language();
        language.setId(id);
        return language;
    }
}
