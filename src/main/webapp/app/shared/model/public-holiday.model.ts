export interface IPublicHoliday {
  id?: number;
  day?: number;
  month?: number;
  year?: number;
  name?: string;
  countryName?: string;
  countryId?: number;
}

export const defaultValue: Readonly<IPublicHoliday> = {};
