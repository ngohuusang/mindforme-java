package com.mindforme.app.service.mapper;

import com.mindforme.app.domain.*;
import com.mindforme.app.service.dto.DoctorDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Doctor} and its DTO {@link DoctorDTO}.
 */
@Mapper(componentModel = "spring", uses = { AddressMapper.class, FamilyMapper.class })
public interface DoctorMapper extends EntityMapper<DoctorDTO, Doctor> {
    @Mapping(source = "address.id", target = "addressId")
    @Mapping(source = "address.address", target = "addressAddress")
    @Mapping(source = "family.id", target = "familyId")
    @Mapping(source = "family.name", target = "familyName")
    DoctorDTO toDto(Doctor doctor);

    @Mapping(source = "addressId", target = "address")
    @Mapping(source = "familyId", target = "family")
    Doctor toEntity(DoctorDTO doctorDTO);

    default Doctor fromId(Long id) {
        if (id == null) {
            return null;
        }
        Doctor doctor = new Doctor();
        doctor.setId(id);
        return doctor;
    }
}
