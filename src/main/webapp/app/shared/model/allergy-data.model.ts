export interface IAllergyData {
  id?: number;
  content?: string;
  langName?: string;
  langId?: number;
  allergyName?: string;
  allergyId?: number;
}

export const defaultValue: Readonly<IAllergyData> = {};
