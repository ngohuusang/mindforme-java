import { IPublicHoliday } from 'app/shared/model/public-holiday.model';
import { Status } from 'app/shared/model/enumerations/status.model';

export interface ICountry {
  id?: number;
  name?: string;
  code?: string;
  status?: Status;
  publicHolidays?: IPublicHoliday[];
}

export const defaultValue: Readonly<ICountry> = {};
