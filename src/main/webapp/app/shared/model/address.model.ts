import { Status } from 'app/shared/model/enumerations/status.model';

export interface IAddress {
  id?: number;
  address?: string;
  name?: string;
  latitude?: number;
  longitude?: number;
  line?: string;
  unit?: string;
  number?: string;
  street?: string;
  postcode?: string;
  suburb?: string;
  status?: Status;
  stateName?: string;
  stateId?: number;
  countryName?: string;
  countryId?: number;
  cityName?: string;
  cityId?: number;
}

export const defaultValue: Readonly<IAddress> = {};
