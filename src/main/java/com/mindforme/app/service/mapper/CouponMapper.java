package com.mindforme.app.service.mapper;

import com.mindforme.app.domain.*;
import com.mindforme.app.service.dto.CouponDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Coupon} and its DTO {@link CouponDTO}.
 */
@Mapper(componentModel = "spring", uses = { CouponTypeMapper.class })
public interface CouponMapper extends EntityMapper<CouponDTO, Coupon> {
    @Mapping(source = "type.id", target = "typeId")
    @Mapping(source = "type.name", target = "typeName")
    CouponDTO toDto(Coupon coupon);

    @Mapping(source = "typeId", target = "type")
    Coupon toEntity(CouponDTO couponDTO);

    default Coupon fromId(Long id) {
        if (id == null) {
            return null;
        }
        Coupon coupon = new Coupon();
        coupon.setId(id);
        return coupon;
    }
}
