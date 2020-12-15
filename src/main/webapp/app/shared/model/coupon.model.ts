import { Moment } from 'moment';

export interface ICoupon {
  id?: number;
  name?: string;
  expireDate?: string;
  email?: string;
  message?: any;
  code?: string;
  usedBy?: number;
  typeName?: string;
  typeId?: number;
}

export const defaultValue: Readonly<ICoupon> = {};
