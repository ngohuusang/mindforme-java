export interface IFavouriteFoodData {
  id?: number;
  content?: string;
  langName?: string;
  langId?: number;
  favouriteFoodName?: string;
  favouriteFoodId?: number;
}

export const defaultValue: Readonly<IFavouriteFoodData> = {};
