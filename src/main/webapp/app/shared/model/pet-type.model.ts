import { IPetBreed } from 'app/shared/model/pet-breed.model';
import { IPetTypeData } from 'app/shared/model/pet-type-data.model';
import { Status } from 'app/shared/model/enumerations/status.model';

export interface IPetType {
  id?: number;
  name?: string;
  status?: Status;
  petBreeds?: IPetBreed[];
  petTypeData?: IPetTypeData[];
}

export const defaultValue: Readonly<IPetType> = {};
