package com.mindforme.app.service.mapper;

import com.mindforme.app.domain.*;
import com.mindforme.app.service.dto.CouponTypeDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link CouponType} and its DTO {@link CouponTypeDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface CouponTypeMapper extends EntityMapper<CouponTypeDTO, CouponType> {
    @Mapping(target = "coupons", ignore = true)
    @Mapping(target = "removeCoupon", ignore = true)
    CouponType toEntity(CouponTypeDTO couponTypeDTO);

    default CouponType fromId(Long id) {
        if (id == null) {
            return null;
        }
        CouponType couponType = new CouponType();
        couponType.setId(id);
        return couponType;
    }
}
