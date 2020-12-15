export interface ICityData {
  id?: number;
  content?: string;
  langName?: string;
  langId?: number;
  cityName?: string;
  cityId?: number;
}

export const defaultValue: Readonly<ICityData> = {};
