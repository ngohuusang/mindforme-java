import { CardType } from 'app/shared/model/enumerations/card-type.model';

export interface IPayment {
  id?: number;
  lastFour?: string;
  expireDate?: string;
  tripeCustomerId?: string;
  cardType?: CardType;
  familyName?: string;
  familyId?: number;
  userLogin?: string;
  userId?: number;
}

export const defaultValue: Readonly<IPayment> = {};
