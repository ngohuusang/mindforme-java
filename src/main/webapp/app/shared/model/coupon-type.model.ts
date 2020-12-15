import { ICoupon } from 'app/shared/model/coupon.model';

export interface ICouponType {
  id?: number;
  name?: string;
  value?: number;
  coupons?: ICoupon[];
}

export const defaultValue: Readonly<ICouponType> = {};
