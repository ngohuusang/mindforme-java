import { Moment } from 'moment';
import { IdType } from 'app/shared/model/enumerations/id-type.model';
import { VerificationStatus } from 'app/shared/model/enumerations/verification-status.model';

export interface IUserIdentification {
  id?: number;
  idType?: IdType;
  name?: string;
  expired?: string;
  idNumber?: string;
  frontImage?: string;
  backImage?: string;
  note?: string;
  verifier?: string;
  verifiedDate?: string;
  verificationStatus?: VerificationStatus;
  userLogin?: string;
  userId?: number;
}

export const defaultValue: Readonly<IUserIdentification> = {};
