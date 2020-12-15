import { Moment } from 'moment';
import { IUser } from 'app/shared/model/user.model';
import { IDoctor } from 'app/shared/model/doctor.model';
import { IEmergencyContact } from 'app/shared/model/emergency-contact.model';
import { IFamilyGallery } from 'app/shared/model/family-gallery.model';
import { IPet } from 'app/shared/model/pet.model';
import { IChild } from 'app/shared/model/child.model';
import { ISupported } from 'app/shared/model/supported.model';
import { IMindingNotification } from 'app/shared/model/minding-notification.model';
import { Privacy } from 'app/shared/model/enumerations/privacy.model';
import { DistanceUnit } from 'app/shared/model/enumerations/distance-unit.model';
import { Frequency } from 'app/shared/model/enumerations/frequency.model';

export interface IFamily {
  id?: number;
  name?: string;
  karmaPoints?: number;
  overview?: string;
  rating?: string;
  imageUrl?: string;
  planExpire?: string;
  rule?: any;
  freeMonths?: string;
  otherVerify?: number;
  kc25Paid?: boolean;
  kc75Paid?: boolean;
  privacyFamily?: Privacy;
  shareToFacebook?: boolean;
  privacyPersonal?: Privacy;
  addToCalendar?: boolean;
  remindEvents?: boolean;
  notifyFacebook?: boolean;
  distanceRequest?: number;
  distanceUnit?: DistanceUnit;
  mailRequestFriend?: Frequency;
  mailRequestFriendOfFriend?: Frequency;
  mailRequest?: Frequency;
  addressAddress?: string;
  addressId?: number;
  members?: IUser[];
  doctors?: IDoctor[];
  emergencyContacts?: IEmergencyContact[];
  galleries?: IFamilyGallery[];
  pets?: IPet[];
  children?: IChild[];
  supporteds?: ISupported[];
  mindingNotifications?: IMindingNotification[];
  planName?: string;
  planId?: number;
}

export const defaultValue: Readonly<IFamily> = {
  kc25Paid: false,
  kc75Paid: false,
  shareToFacebook: false,
  addToCalendar: false,
  remindEvents: false,
  notifyFacebook: false,
};
