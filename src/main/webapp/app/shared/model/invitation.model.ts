import { Moment } from 'moment';

export interface IInvitation {
  id?: number;
  email?: string;
  facebook?: string;
  freePeriod?: string;
  userLogin?: string;
  userId?: number;
}

export const defaultValue: Readonly<IInvitation> = {};
