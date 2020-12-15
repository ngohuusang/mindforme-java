import { Privacy } from 'app/shared/model/enumerations/privacy.model';

export interface IFamilyGallery {
  id?: number;
  imageUrl?: string;
  sortOrder?: number;
  isDefault?: boolean;
  privacy?: Privacy;
  familyName?: string;
  familyId?: number;
}

export const defaultValue: Readonly<IFamilyGallery> = {
  isDefault: false,
};
