import { ICityData } from 'app/shared/model/city-data.model';
import { Status } from 'app/shared/model/enumerations/status.model';

export interface ICity {
  id?: number;
  name?: string;
  latitude?: number;
  longitude?: number;
  status?: Status;
  cityData?: ICityData[];
  stateName?: string;
  stateId?: number;
}

export const defaultValue: Readonly<ICity> = {};
