import { ICity } from 'app/shared/model/city.model';
import { IStateData } from 'app/shared/model/state-data.model';

export interface IState {
  id?: number;
  name?: string;
  status?: number;
  cities?: ICity[];
  stateData?: IStateData[];
}

export const defaultValue: Readonly<IState> = {};
