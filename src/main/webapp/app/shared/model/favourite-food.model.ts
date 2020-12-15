import { IFavouriteFoodData } from 'app/shared/model/favourite-food-data.model';
import { IChild } from 'app/shared/model/child.model';
import { Status } from 'app/shared/model/enumerations/status.model';

export interface IFavouriteFood {
  id?: number;
  name?: string;
  status?: Status;
  favouriteFoodData?: IFavouriteFoodData[];
  children?: IChild[];
}

export const defaultValue: Readonly<IFavouriteFood> = {};
