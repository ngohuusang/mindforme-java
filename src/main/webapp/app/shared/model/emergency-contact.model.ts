export interface IEmergencyContact {
  id?: number;
  name?: string;
  relationToYou?: string;
  phoneNumber?: string;
  email?: string;
  addressAddress?: string;
  addressId?: number;
  familyName?: string;
  familyId?: number;
}

export const defaultValue: Readonly<IEmergencyContact> = {};
