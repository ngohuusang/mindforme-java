import { Moment } from 'moment';
import { VerificationStatus } from 'app/shared/model/enumerations/verification-status.model';

export interface IWorkingWithChildren {
  id?: number;
  firstName?: string;
  otherName?: string;
  familyName?: string;
  birthday?: string;
  checkNumber?: string;
  frontImage?: string;
  backImage?: string;
  note?: string;
  verifier?: string;
  verifiedDate?: string;
  verificationStatus?: VerificationStatus;
  userLogin?: string;
  userId?: number;
}

export const defaultValue: Readonly<IWorkingWithChildren> = {};
