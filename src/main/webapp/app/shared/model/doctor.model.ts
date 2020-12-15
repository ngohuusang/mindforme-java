export interface IDoctor {
  id?: number;
  doctorName?: string;
  phoneNumber?: string;
  email?: string;
  medicalPractice?: string;
  addressAddress?: string;
  addressId?: number;
  familyName?: string;
  familyId?: number;
}

export const defaultValue: Readonly<IDoctor> = {};
