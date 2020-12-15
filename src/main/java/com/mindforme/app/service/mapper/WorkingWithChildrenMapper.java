package com.mindforme.app.service.mapper;

import com.mindforme.app.domain.*;
import com.mindforme.app.service.dto.WorkingWithChildrenDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link WorkingWithChildren} and its DTO {@link WorkingWithChildrenDTO}.
 */
@Mapper(componentModel = "spring", uses = { UserMapper.class })
public interface WorkingWithChildrenMapper extends EntityMapper<WorkingWithChildrenDTO, WorkingWithChildren> {
    @Mapping(source = "user.id", target = "userId")
    @Mapping(source = "user.login", target = "userLogin")
    WorkingWithChildrenDTO toDto(WorkingWithChildren workingWithChildren);

    @Mapping(source = "userId", target = "user")
    WorkingWithChildren toEntity(WorkingWithChildrenDTO workingWithChildrenDTO);

    default WorkingWithChildren fromId(Long id) {
        if (id == null) {
            return null;
        }
        WorkingWithChildren workingWithChildren = new WorkingWithChildren();
        workingWithChildren.setId(id);
        return workingWithChildren;
    }
}
