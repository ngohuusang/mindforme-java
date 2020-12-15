export interface ICountryData {
  id?: number;
  content?: string;
  langName?: string;
  langId?: number;
}

export const defaultValue: Readonly<ICountryData> = {};
